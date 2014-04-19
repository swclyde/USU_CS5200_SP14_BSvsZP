package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;

public class ChangeStrength extends Request {

    public short DeltaValue;
    private static int MinimumEncodingLength;

    public ChangeStrength() {
        super(PossibleTypes.ChangeStrength);
    }

    public ChangeStrength(short deltaValue) {
        super(PossibleTypes.ChangeStrength);
        DeltaValue = deltaValue;
    }

    public static ChangeStrength Create(ByteList messageBytes) throws ApplicationException, Exception {
        ChangeStrength result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < ChangeStrength.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.ChangeStrength.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new ChangeStrength();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, NotActiveException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.ChangeStrength.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                                   // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        bytes.Add(DeltaValue);
        bytes.update();
        Integer currentLengthinBytes = (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.update();
        short length = currentLengthinBytes.shortValue();
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

        DeltaValue = bytes.GetInt16();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.ChangeStrength.getValue();
    }

    public short getDeltaValue() {
        return DeltaValue;
    }

    public void setDeltaValue(short deltaValue) {
        DeltaValue = deltaValue;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 		// Object header
                				+ 2;    // Delta Value
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.ChangeStrength;
    }

}
