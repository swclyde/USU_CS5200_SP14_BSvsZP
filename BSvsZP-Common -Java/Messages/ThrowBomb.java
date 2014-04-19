package Messages;


import org.omg.CORBA.portable.ApplicationException;

import Common.*;

public class ThrowBomb extends Request 
{
    public short ThrowingBrilliantStudentId;
    public Bomb Bomb;
    public FieldLocation TowardsSquare;
    public Tick EnablingTick;
    private static int MinimumEncodingLength;

    protected ThrowBomb(PossibleTypes type) {
        super(type);

    }

    public ThrowBomb() {
        super(PossibleTypes.ThrowBomb);
    }

    public ThrowBomb(short bsId, Bomb bomb, FieldLocation towardsSquare, Tick tick) {
        super(PossibleTypes.Move);
        ThrowingBrilliantStudentId = bsId;
        Bomb = bomb;
        TowardsSquare = towardsSquare;
        EnablingTick = tick;
    }


    public static ThrowBomb Create(ByteList bytes) throws ApplicationException, Exception {
        ThrowBomb result = null;

        if (bytes == null || bytes.getRemainingToRead() < ThrowBomb.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (bytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.ThrowBomb.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new ThrowBomb();
            result.Decode(bytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.ThrowBomb.getValue());
        bytes.update();                            // Write out this class id first
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);
        bytes.update();                            // Write out a place holder for the length
        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        bytes.AddObjects(ThrowingBrilliantStudentId, Bomb, TowardsSquare, EnablingTick);
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
        ThrowingBrilliantStudentId = bytes.GetInt16();
        bytes.update();
        Bomb = (Bomb) bytes.GetDistributableObject();
        bytes.update();
        TowardsSquare = (FieldLocation) bytes.GetDistributableObject();
        bytes.update();
        EnablingTick = (Tick) bytes.GetDistributableObject();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public short getThrowingBrilliantStudentId() {
        return ThrowingBrilliantStudentId;
    }

    public void setThrowingBrilliantStudentId(short throwingBrilliantStudentId) {
        ThrowingBrilliantStudentId = throwingBrilliantStudentId;
    }

    public Bomb getBomb() {
        return Bomb;
    }

    public void setBomb(Bomb bomb) {
        Bomb = bomb;
    }

    public FieldLocation getTowardsSquare() {
        return TowardsSquare;
    }

    public void setTowardsSquare(FieldLocation towardsSquare) {
        TowardsSquare = towardsSquare;
    }

    public Tick getEnablingTick() {
        return EnablingTick;
    }

    public void setEnablingTick(Tick enablingTick) {
        EnablingTick = enablingTick;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.ThrowBomb.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
        						+ 2 // GameId
        						+ 1 // Bomb
        						+ 1 // TowardsSquare
        						+ 1;             // EnablingTick
        if (Common.ByteList.DEBUG)
        {
        	System.out.println("ThrowBomb.MinimumEncodingLength" + MinimumEncodingLength);
        }
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.ThrowBomb;
    }
}
