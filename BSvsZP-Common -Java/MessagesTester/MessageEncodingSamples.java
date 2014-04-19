package MessagesTester;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;
import Common.AgentInfo;
import Common.ByteList;
import Common.FieldLocation;
import Common.MessageNumber;
import Messages.AckNak;
import Messages.JoinGame;
import Messages.Reply;

public class MessageEncodingSamples {

	@Test
	public void CreateEncodingSamples() throws Exception {
		Writer writer = null;
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MessageSamples.txt"), "UTF-8"));
		
		MessageNumber msgNumber = MessageNumber.Create((short)100, (short)120);
        MessageNumber conversationNumber = MessageNumber.Create((short)200, (short)240);
		
        AgentInfo agentInfo = new AgentInfo((short)10, AgentInfo.PossibleAgentType.BrilliantStudent, new Common.EndPoint("129.123.5.10:1234"));
        agentInfo.setAgentStatus(AgentInfo.PossibleAgentStatus.InGame);
        agentInfo.setANumber("A00001");
        
        agentInfo.setFirstName("Joe");
        agentInfo.setLastName("Jones");
        agentInfo.setLocation(new FieldLocation((short) 10, (short) 20));
        
        agentInfo.setPoints((Double) 100.0);
        agentInfo.setStrength((Double) 200.0);
        agentInfo.setSpeed(1.2);
     


        AckNak ackNak = new AckNak(Reply.PossibleStatus.Success, agentInfo, "Test Message");
        ackNak.setMessageNr(msgNumber);
        ackNak.setConversationId(conversationNumber);
        ackNak.setIntResult(99);
        ackNak.setNote("Test Note");
        
        writer.write("AckNak");
        writer.write("\n" + "\tMessageNr = " + ackNak.getMessageNr().toString());
        writer.write("\n" + "\tConversationId = " +  ackNak.getConversationId().toString());
        writer.write("\n" + "\tReplyType = " +  ackNak.getReplyType());
        writer.write("\n" + "\tAckNak Status = " +  ackNak.getStatus());
        writer.write("\n" + "\tAgent Info:");
        writer.write("\n" + "\t\tId = " + agentInfo.getId());
        writer.write("\n" + "\t\tAgentStatus = " + agentInfo.getAgentStatus());
        writer.write("\n" + "\t\tANumber = " +  agentInfo.getANumber());
        writer.write("\n" + "\t\tFirstName = " + agentInfo.getFirstName());
        writer.write("\n" + "\t\tLastName = " + agentInfo.getLastName());
        writer.write("\n" + "\t\tLocation (X = " + agentInfo.getLocation().getX() + ", Y = " +  agentInfo.getLocation().getY() + ")");
        
        writer.write("\n" + "\t\tPoints = " + agentInfo.getPoints());
        writer.write("\n" + "\t\tStrength = " + agentInfo.getStrength());
        writer.write("\n" + "\t\tSpeed = " + agentInfo.getSpeed());
        
        ByteList byteList = new ByteList();
        ackNak.Encode(byteList);

        writer.write("");
        writer.write("\n" + "Encoding:");
        printAll(byteList, writer);
        writer.write("\n" );
        writer.write("------------------------------------");
        writer.write("\n");

        JoinGame joinGame = new JoinGame((short)20, agentInfo);
        joinGame.setMessageNr(msgNumber);
        joinGame.setConversationId(conversationNumber);
                                    
        writer.write("JoinGame");
        writer.write("\n" + "\tMessageNr = " +  joinGame.getMessageNr().toString());
        writer.write("\n" + "\tConversationId = " + joinGame.getConversationId().toString());
        writer.write("\n" + "\tGameId = " + joinGame.getGameId());
        writer.write("\n" + "\tAgent Info:");
        writer.write("\n" + "\t\tId = " + agentInfo.getId());
        writer.write("\n" + "\t\tAgentStatus = " + agentInfo.getAgentStatus());
        writer.write("\n" + "\t\tANumber = " + agentInfo.getANumber());
        writer.write("\n" + "\t\tFirstName = " + agentInfo.getFirstName());
        writer.write("\n" + "\t\tLastName = " + agentInfo.getLastName());
        writer.write("\n" + "\t\tLocation = " + agentInfo.getLocation().ToString());
        writer.write("\n" + "\t\tPoints = " + agentInfo.getPoints());
        writer.write("\n" + "\t\tStrength = " + agentInfo.getStrength());
        writer.write("\n" + "\t\tSpeed = " + agentInfo.getSpeed());
	
        
        ByteList byteList2 = new ByteList();
        joinGame.Encode(byteList2);

        writer.write("");
        writer.write("Encoding:");
        printAll(byteList2, writer);
        writer.write("");
        writer.write("------------------------------------");
        writer.write("");
		writer.close();
		
	}

	public static void printAll(ByteList bytes, Writer writer) throws ApplicationException, IOException
	{
		  byte[] b = bytes.GetBytes(bytes.getLength());
	        for(int i =0; i<b.length; ++i)
	        	writer.write( i + ":" + b[i] + " ");
	       
	}
}
