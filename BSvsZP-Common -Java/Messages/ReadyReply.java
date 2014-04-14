package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;

public class ReadyReply extends Reply
{
	private static short ClassId; // { get { return  } }
	private static int MinimumEncodingLength;
  
	
        @Override
	public short getClassId() {
		ClassId = (short)MESSAGE_CLASS_IDS.ReadyReply.getValue();
		return   ClassId;
	}

	protected ReadyReply() { }
	
	public ReadyReply(PossibleStatus status, String...note)
	{
		super(Reply.PossibleTypes.ReadyReply, status, (note.length ==1 ? note[0] : null));
    }
	 
	
	public static ReadyReply Create(ByteList messageBytes) throws Exception
    {
        ReadyReply result = null;

        if (messageBytes==null || messageBytes.getRemainingToRead()< getMinimumEncodingLength())
            throw new ApplicationException("Invalid message byte array", null);
        if (messageBytes.PeekInt16() != ClassId)
            throw new ApplicationException("Invalid message class id", null);
        else
        {
            result = new ReadyReply();
            result.Decode(messageBytes);
        }

        return result;
    }
	
	 @Override public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception
     {
         bytes.Add((short)MESSAGE_CLASS_IDS.ReadyReply.getValue());                           // Write out this class id first
         bytes.update();
         short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
         bytes.update();                                           // can write the length here later
         bytes.Add((short) 0);                           // Write out a place holder for the length
         bytes.update();
         super.Encode(bytes);                             // Encode stuff from base class
         bytes.update();
         short length = (short)(bytes.getCurrentWritePosition() - lengthPos - 2);
         bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
         bytes.update();
     }

     @Override public void Decode(ByteList bytes) throws Exception
     {
         short objType = bytes.GetInt16();  bytes.update();
         short objLength = bytes.GetInt16();  bytes.update();

         bytes.SetNewReadLimit(objLength);
         bytes.update();
         super.Decode(bytes);
         bytes.update();
         bytes.RestorePreviosReadLimit(); bytes.update();
     } 
     
	public static int getMinimumEncodingLength() {
		 MinimumEncodingLength = 4;
		 return MinimumEncodingLength;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public MESSAGE_CLASS_IDS MessageTypeId() {
		return Message.MESSAGE_CLASS_IDS.fromShort(ClassId);
	}

}
