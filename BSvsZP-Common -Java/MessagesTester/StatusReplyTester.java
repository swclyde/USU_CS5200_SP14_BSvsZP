package MessagesTester;

import static org.junit.Assert.*;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.junit.Test;

import Common.AgentInfo;
import Common.AgentInfo.PossibleAgentStatus;
import Common.ByteList;
import Messages.Message;
import Messages.StatusReply;
import Messages.Reply;

public class StatusReplyTester {

	@Test
	 public void StatusReply_TestEverything() throws NotActiveException, UnknownHostException, Exception
	 {
		 AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
		 agentInfo.setANumber("A0001");
		 agentInfo.setFirstName("Joe");
		 agentInfo.setLastName("Jone");
		 agentInfo.setAgentStatus(PossibleAgentStatus.InGame);
		 StatusReply r1 = new StatusReply(Reply.PossibleStatus.Success, agentInfo);
		 assertEquals(Reply.PossibleStatus.Success, r1.Status);
		 assertSame(agentInfo, r1.Info);
		 
		 r1 = new StatusReply(Reply.PossibleStatus.Success, agentInfo, "test note");
		 
		 assertEquals(Reply.PossibleStatus.Success, r1.Status);
		 assertSame(agentInfo, r1.Info);
		 assertEquals("test note", r1.Note);
		 
		 ByteList byteList = new ByteList();
		 r1.Encode(byteList);
		 Message msg = Message.Create(byteList);
		 
		 assertNotNull(msg);
		 StatusReply r2 = (StatusReply) msg ;
		 assertEquals(r1.Status, r2.Status);
		 assertEquals(r1.Info.getId(), r2.Info.getId());
		 assertEquals(r1.Info.getLastName(), r2.Info.getLastName());
		 assertEquals(r1.Note, r2.Note);
	       }

}
