package MessagesTester;

import java.io.NotActiveException;
import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;
import Common.*;
import Messages.*;

public class TestMessages {

	public static void main(String[] args) throws NotActiveException, UnknownHostException, Exception 
	{
		DistributableObject disO = new DistributableObject();
     
        AckNak ack = new AckNak(Reply.PossibleStatus.Success, 20, disO, "AckNak Message", "Note to AckNak message");
        ByteList ackBytes = new ByteList();
        ack.Encode(ackBytes);
        
        printAll(ackBytes);
        // 0-5501090-550700-5501813-2404000113-240400011104678011101160101032011601110320650990107078097010703201090101011501150970103010100002010306509901070780970107032077010101150115097010301010

        AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
        agentInfo.setFirstName("Joe");
        agentInfo.setLastName("Jones");
        agentInfo.setANumber("A00001");
        agentInfo.setAgentStatus(AgentInfo.PossibleAgentStatus.InGame);
        
        EndPoint ep = new EndPoint("129.123.7.53:51003");
        ComponentInfo cInfo = new ComponentInfo((short) 10, ep);
        ByteList ciBytes = new ByteList();
        cInfo.Encode(ciBytes);
        printAll(ciBytes);
        //3-1401501013-220800000000
	}
	
	public static void printAll(ByteList bytes) throws ApplicationException
	{
		  byte[] b = bytes.GetBytes(bytes.getLength());
	        for(int i =0; i<b.length; ++i)
	            System.out.print(b[i]);
	        System.out.println();
	}
}
