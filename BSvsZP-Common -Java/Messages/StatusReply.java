package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.AgentInfo;
import Common.ByteList;

public class StatusReply extends Reply
{
    public AgentInfo Info;
    public static int MinimumEncodingLength;

    public AgentInfo getInfo() {
        return Info;
    }

    public void setInfo(AgentInfo info) {
        Info = info;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.StatusReply.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
        						+ 1;             // Agent Info;
        return MinimumEncodingLength;
    }

    protected StatusReply() {
    }

    public StatusReply(PossibleStatus status, AgentInfo info, String... note) {
        super(Reply.PossibleTypes.StatusReply, status, (note.length == 1 ? note[0] : null));
        Info = info;
    }

    public static StatusReply Create(ByteList messageBytes) throws ApplicationException, Exception {
        StatusReply result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }
        if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.StatusReply.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new StatusReply();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.StatusReply.getValue());                           // Write out this class id first

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length

        super.Encode(bytes);                             // Encode stuff from base class

        bytes.Add(Info);

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        

    }

    @Override
    public void Decode(ByteList bytes) throws Exception {
        short objType = bytes.GetInt16();
        short objLength = bytes.GetInt16();

        bytes.SetNewReadLimit(objLength);

        super.Decode(bytes);

        Info = (AgentInfo) bytes.GetDistributableObject();

        bytes.RestorePreviosReadLimit();
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.StatusReply;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}
