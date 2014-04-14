package Messages;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;

public class EndGame extends Request {

    private static short ClassId;
    public short GameId;
    private static int MinimumEncodingLength;

    public EndGame() {
        super(PossibleTypes.EndGame);
    }

    public EndGame(short gameId) {
        super(PossibleTypes.EndGame);
        GameId = gameId;
    }

    protected EndGame(PossibleTypes type) {
        super(type);
        // TODO Auto-generated constructor stub
    }

    //new
    public static EndGame Create(ByteList bytes) throws ApplicationException, Exception {
        EndGame result = null;

        if (bytes == null || bytes.getRemainingToRead() < EndGame.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (bytes.PeekInt16() != ClassId) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new EndGame();
            result.Decode(bytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.EndGame.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                         // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class
        bytes.Add(GameId);
        bytes.update();
        Integer lenghtinBytes = (bytes.getCurrentWritePosition() - lengthPos - 2);
        short length = lenghtinBytes.shortValue();
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
        GameId = bytes.GetInt16();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public short getGameId() {
        return GameId;
    }

    public void setGameId(short gameId) {
        GameId = gameId;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 2;              // GameId
        return MinimumEncodingLength;
    }

    @Override
    public short getClassId() {
        ClassId = (short) MESSAGE_CLASS_IDS.EndGame.getValue();
        return ClassId;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.EndGame;
    }

}
