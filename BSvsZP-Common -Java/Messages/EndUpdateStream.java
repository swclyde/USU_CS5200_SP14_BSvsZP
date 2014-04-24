package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;

public class EndUpdateStream extends Reply{

	public short GameId; 
    public static int MinimumEncodingLength; 
    
    public short getClassId()
    {
    	return (short)MESSAGE_CLASS_IDS.EndUpdateStream.getValue();
    }
    public EndUpdateStream()
    {
    	super(PossibleTypes.EndUpdateStream, PossibleStatus.Success, "");
    }
   
    public static EndUpdateStream Create(ByteList bytes) throws Exception
    {
        EndUpdateStream result = null;

        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength())
            throw new ApplicationException("Invalid message byte array", null);
        else if (bytes.PeekInt16() != (short)MESSAGE_CLASS_IDS.EndUpdateStream.getValue())
            throw new ApplicationException("Invalid message class id", null);
        else
        {
            result = new EndUpdateStream();
            result.Decode(bytes);
        }

        return result;
    }	
	
    public void Encode(ByteList bytes) throws NotActiveException, Exception
    {
        bytes.Add((short)MESSAGE_CLASS_IDS.EndUpdateStream.getValue());                              // Write out this class id first

        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
                                                                // can write the length here later
        bytes.Add((short)0);                             // Write out a place holder for the length


        super.Encode(bytes);                              // Encode the part of the object defined
                                                                // by the base class
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
    }

    public void Decode(ByteList bytes) throws Exception
    {

        short objType = bytes.GetInt16(); bytes.update();
        short objLength = bytes.GetInt16(); bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();
        
        super.Decode(bytes);

        bytes.RestorePreviosReadLimit(); bytes.update();
    }
    
	public short getGameId() {
		return GameId;
	}

	public void setGameId(short gameId) {
		GameId = gameId;
	}

	public static int getMinimumEncodingLength() {
		MinimumEncodingLength =  4;                // Object header
		return MinimumEncodingLength;
	}

	public Message.MESSAGE_CLASS_IDS MessageTypeId() 
	{
		return Message.MESSAGE_CLASS_IDS.EndUpdateStream; 
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
