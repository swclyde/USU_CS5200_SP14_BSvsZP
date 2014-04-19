package Messages;

import java.io.NotActiveException;

import org.omg.CORBA.portable.ApplicationException;

import Common.AgentList;
import Common.ByteList;

public class AgentListReply extends Reply {

    public AgentList Agents;
    private static int MinimumEncodingLength;

    protected AgentListReply() {
    }

    public AgentListReply(PossibleStatus status, AgentList agents, String... note) {
        super(Reply.PossibleTypes.AgentListReply, status, (note.length == 1) ? note[0] : null);
        Agents = agents;
    }

    public static AgentListReply Create(ByteList messageBytes) throws Exception, ApplicationException {
        AgentListReply result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }
        if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.AgentListReply.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new AgentListReply();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.AgentListReply.getValue());                           // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        bytes.update();                                  // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                             // Encode stuff from base class

        bytes.Add(Agents);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object
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

        Agents = (AgentList) bytes.GetDistributableObject();
        bytes.update();

        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    @Override
    public Message.MESSAGE_CLASS_IDS MessageTypeId() {
        return  MESSAGE_CLASS_IDS.AgentListReply;
    }

    @Override
    public short getClassId() {
       return (short) MESSAGE_CLASS_IDS.AgentListReply.getValue();
    }

    public AgentList getAgents() {
        return Agents;
    }

    public void setAgents(AgentList agents) {
        Agents = agents;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + AgentList.getMinimumEncodingLength();
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

}
