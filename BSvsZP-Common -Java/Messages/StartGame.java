package Messages;

import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;

public class StartGame extends Request {

    private static short ClassId;
    public short GameId;
    private static int MinimumEncodingLength;

    protected StartGame(PossibleTypes type) {
        super(type);
    }

    public StartGame() {
        super(PossibleTypes.StartGame);
    }

    public StartGame(short gameId) {
        super(PossibleTypes.StartGame);
        GameId = gameId;
    }

    //new
    public static StartGame Create(ByteList bytes) throws ApplicationException, Exception {
        StartGame result = null;

        if (bytes == null || bytes.getRemainingToRead() < StartGame.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (bytes.PeekInt16() != ClassId) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new StartGame();
            result.Decode(bytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.StartGame.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                                   // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();

        super.Encode(bytes);                              // Encode the part of the object defined
        // by the base class

        bytes.Add(GameId);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
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
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public short getGameId() {
        return GameId;
    }

    public void setGameId(short gameId) {
        GameId = gameId;
    }

    @Override
    public short getClassId() {
        ClassId = (short) MESSAGE_CLASS_IDS.StartGame.getValue();
        return ClassId;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 2;              // GameId
        System.out.println("StartGame.MinimumEncodingLength" + MinimumEncodingLength);
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.StartGame;
    }
}
