package MessagesTester;

import static org.junit.Assert.*;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.junit.Test;

import Common.*;
import Messages.*;

public class ReadyReplyTester {

	@Test
	 public void ReadyReply_TestEverything() throws NotActiveException, UnknownHostException, Exception
	         {
	             ReadyReply r1 = new ReadyReply(Reply.PossibleStatus.Failure);
	             assertEquals(Reply.PossibleStatus.Failure, r1.Status);
	 
	             r1 = new ReadyReply(Reply.PossibleStatus.Success, "test note");
	             assertEquals(Reply.PossibleStatus.Success, r1.Status);
	             assertEquals("test note", r1.Note);
	 
	             ByteList byteList = new ByteList();
	             r1.Encode(byteList);
	 
	             Message msg = Message.Create(byteList);
	             assertNotNull(msg);
	             ReadyReply r2 = (ReadyReply)msg;
	             assertEquals(r1.Status, r2.Status);
	             assertEquals(r1.Note, r2.Note);
	         }
	  
	
	
}
