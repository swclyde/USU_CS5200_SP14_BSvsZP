package Messages;

import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;
import Common.Tick;

public class GetResource extends Request {

    public short GameId;
    public PossibleResourceType GetResourceType;
    public Tick EnablingTick;
    private static int MinimumEncodingLength;

    public enum PossibleResourceType {

        GameConfiguration(1),
        PlayingFieldLayout(2),
        BrillianStudentList(3),
        ExcuseGeneratorList(4),
        WhiningSpinnerList(5),
        ZombieProfessorList(6),
        Excuse(7),
        WhiningTwine(8),
        Tick(9);
        private int value;

        PossibleResourceType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static PossibleResourceType convert(byte value) {
            return PossibleResourceType.values()[value - 1];
        }
    }

    public GetResource() {
        super(PossibleTypes.GetResource);
    }

    public GetResource(short gameId, PossibleResourceType type, Tick tick) {
        super(PossibleTypes.GetResource);
        GameId = gameId;
        GetResourceType = type;
        EnablingTick = tick;
    }

    public GetResource(short gameId, PossibleResourceType type) {
    	this(gameId, type, null);
    }
    
    public static GetResource Create(ByteList messageBytes) throws ApplicationException, Exception {
        GetResource result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < GetResource.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (messageBytes.PeekInt16() !=(short) MESSAGE_CLASS_IDS.GetResource.getValue() ) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new GetResource();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.GetResource.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length

        bytes.update();
        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        bytes.AddObjects(GameId, (byte) GetResourceType.getValue(), EnablingTick);
        bytes.update();
        int lengthinBytes = (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.update();
        short length = (short) lengthinBytes;
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
        bytes.update();
    }

    @Override
    public void Decode(ByteList bytes) throws ApplicationException, Exception {

        short objType = bytes.GetInt16();
        bytes.update();
        short objLength = bytes.GetInt16();
        bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();
        super.Decode(bytes);

        GameId = bytes.GetInt16();
        bytes.update();
        GetResourceType = PossibleResourceType.convert(bytes.GetByte());
        bytes.update();
        EnablingTick = (Tick) bytes.GetDistributableObject();
        bytes.update();

        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                				+ 2 // GameId
                				+ 1 // GetType
                				+ 1;
        return MinimumEncodingLength;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.GetResource.getValue();
    }

    public short getGameId() {
        return GameId;
    }

    public void setGameId(short gameId) {
        GameId = gameId;
    }

    public PossibleResourceType getGetType() {
        return GetResourceType;
    }

    public void setGetType(PossibleResourceType getType) {
        GetResourceType = getType;
    }

    public Tick getEnablingTick() {
        return EnablingTick;
    }

    public void setEnablingTick(Tick enablingTick) {
        EnablingTick = enablingTick;
    }

    protected GetResource(PossibleTypes type) {
        super(type);

    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.GetResource;
    }

}
