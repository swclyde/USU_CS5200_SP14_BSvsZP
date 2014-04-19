package Messages;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;

public class GetStatus extends Request {

    private static int MinimumEncodingLength;

    public GetStatus() {
        super(PossibleTypes.GetStatus);
    }

    public static GetStatus Create(ByteList messageBytes) throws ApplicationException, Exception {
        GetStatus result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < GetStatus.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.GetStatus.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new GetStatus();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.GetStatus.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                              // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();

        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.update();
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object
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

        super.Decode(bytes);

        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.GetStatus.getValue();

    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4;				// Object header
        return MinimumEncodingLength;
    }

    protected GetStatus(PossibleTypes type) {
        super(type);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.GetStatus;
    }
}
