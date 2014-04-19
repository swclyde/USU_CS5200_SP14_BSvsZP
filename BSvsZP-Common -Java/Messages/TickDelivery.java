package Messages;

import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;
import Common.Tick;

public class TickDelivery extends Request {

    public Tick CurrentTick;
    private static int MinimumEncodingLength;
    
    public  Message.MESSAGE_CLASS_IDS MessageTypeId()
    {
    	return Messages.Message.MESSAGE_CLASS_IDS.TickDelivery;
    }
    protected TickDelivery(PossibleTypes type) {
        super(type);
    }

    public TickDelivery() {
        super(PossibleTypes.TickDelivery);
    }

    public TickDelivery(Tick tick) {
        super(PossibleTypes.TickDelivery);
        CurrentTick = tick;
    }

    public static TickDelivery Create(ByteList bytes) throws ApplicationException, Exception {
        TickDelivery result = null;

        if (bytes == null || bytes.getRemainingToRead() < TickDelivery.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (bytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.TickDelivery.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new TickDelivery();
            result.Decode(bytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.TickDelivery.getValue());
        bytes.update();                           // Write out this class id first
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);
        bytes.update();                           // Write out a place holder for the length
        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        bytes.AddObject(CurrentTick);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
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
        CurrentTick = (Tick) bytes.GetDistributableObject();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public Tick getCurrentTick() {
        return CurrentTick;
    }

    public void setCurrentTick(Tick currentTick) {
        CurrentTick = currentTick;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.TickDelivery.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
        						+ 1;
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
