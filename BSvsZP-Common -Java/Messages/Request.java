package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;

public abstract class Request extends Message {

    private static short ClassId;

    public enum PossibleTypes {

        JoinGame(2),
        AddComponent(3),
        RemoveComponent(4),
        StartGame(5),
        EndGame(6),
        GetResource(7),
        TickDelivery(8),
        ValidateTick(9),
        Move(10),
        ThrowBomb(11),
        Eat(12),
        ChangeStrength(13),
        Collaborate(14),
        GetStatus(15),
        ExitGame(16);

        private int value;

        PossibleTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static PossibleTypes convert(byte value) {

            return PossibleTypes.values()[value];
        }

        public static PossibleTypes fromByte(int b) {

            PossibleTypes temp = null;
            for (PossibleTypes t : PossibleTypes.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;  //or throw exception
        }
    }

    public PossibleTypes RequestType;
    private static int MinimumEncodingLength;

    protected Request(PossibleTypes type) {
        RequestType = type;
    }

    //new
    public static Request Create(ByteList bytes) throws ApplicationException, Exception {
        Request result = null;

        if (bytes == null || bytes.getRemainingToRead() < Request.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }

        short msgType = bytes.PeekInt16();

        if (msgType == (short) MESSAGE_CLASS_IDS.JoinGame.getValue()) {
            result = JoinGame.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.AddComponent.getValue()) {
            result = AddComponent.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.RemoveComponent.getValue()) {
            result = RemoveComponent.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.StartGame.getValue()) {
            result = StartGame.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.EndGame.getValue()) {
            result = EndGame.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.GetResource.getValue()) {
            result = GetResource.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.TickDelivery.getValue()) {
            result = TickDelivery.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.ValidateTick.getValue()) {
            result = ValidateTick.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.Move.getValue()) {
            result = Move.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.ThrowBomb.getValue()) {
            result = ThrowBomb.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.Eat.getValue()) {
            result = Eat.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.ChangeStrength.getValue()) {
            result = ChangeStrength.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.Collaborate.getValue()) {
            result = Collaborate.Create(bytes);
        } else if (msgType == (short) MESSAGE_CLASS_IDS.GetStatus.getValue()) {
            result = GetStatus.Create(bytes);
        } else {
            throw new ApplicationException("Invalid Message Class Id", null);
        }
        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.Request.getValue());                           // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        bytes.getRemainingToRead();
        super.Encode(bytes);                             // Encode stuff from base class
        bytes.update();
        bytes.Add((byte) RequestType.getValue());         // Write out a place holder for the length
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object
        bytes.update();
    }

    @Override
    public void Decode(ByteList bytes) throws Exception {
        short objType = bytes.GetInt16();
        bytes.update();
        short objLength = bytes.GetInt16();
        bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();
        bytes.getRemainingToRead();
        bytes.update();
        super.Decode(bytes);

        int temp = (int) bytes.GetByte();
        bytes.update();
        RequestType = PossibleTypes.fromByte(temp);
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    @Override
    public short getClassId() {
        ClassId = (short) MESSAGE_CLASS_IDS.Request.getValue();
        System.out.println("Request.ClassId: " + ClassId);
        return ClassId;
    }

    public PossibleTypes getRequestType() {
        return RequestType;
    }

    public void setRequestType(PossibleTypes requestType) {
        RequestType = requestType;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 1;             // RequestType
        System.out.println("Request.MinimumEncodingLength" + MinimumEncodingLength);
        return MinimumEncodingLength;
    }
}
