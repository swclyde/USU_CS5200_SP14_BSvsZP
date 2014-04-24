package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;

public class StartUpdateStream extends Request
{
	public Message.MESSAGE_CLASS_IDS MessageTypeId() 
	{ 
		return Message.MESSAGE_CLASS_IDS.StartUpdateStream; 
	}
	
	public short GameId; 
    public static int MinimumEncodingLength;
    public StartUpdateStream(){
    	super(PossibleTypes.StartUpdateStream);
    }
    public static StartUpdateStream Create(ByteList bytes) throws Exception
    {
        StartUpdateStream result = null;

        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength())
            throw new ApplicationException("Invalid message byte array", null);
        else if (bytes.PeekInt16() != (short)MESSAGE_CLASS_IDS.StartUpdateStream.getValue())
            throw new ApplicationException("Invalid message class id", null);
        else
        {
            result = new StartUpdateStream();
            result.Decode(bytes);
        }

        return result;
    }
    public short getGameId() {
		return GameId;
	}

	public void setGameId(short gameId) {
		GameId = gameId;
	}

	public static int getMinimumEncodingLength() {
            return 4;                // Object header
	}
	
	public void Encode(ByteList bytes) throws NotActiveException, Exception
    {
        bytes.Add((short)MESSAGE_CLASS_IDS.StartUpdateStream.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                         // can write the length here later
        bytes.Add((short)0);                             // Write out a place holder for the length
        bytes.update();

        super.Encode(bytes);                              // Encode the part of the object defined
        bytes.update();                                         // by the base class

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.update();
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object
        bytes.update();
    }

     public void Decode(ByteList bytes) throws Exception
    {

        short objType = bytes.GetInt16(); bytes.update();
        short objLength = bytes.GetInt16();  bytes.update();

        bytes.SetNewReadLimit(objLength);  bytes.update();

        super.Decode(bytes);  bytes.update();

        bytes.RestorePreviosReadLimit();  bytes.update();
    }
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
