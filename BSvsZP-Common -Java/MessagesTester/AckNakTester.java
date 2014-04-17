package MessagesTester;

import static org.junit.Assert.*;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.Tick;
import Messages.AckNak;
import Messages.Message;
import Messages.Reply;

public class AckNakTester {

	@Test
	public void test() {
		Tick t1 = new Tick();
        AckNak m = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Success, m.Status);
        assertEquals(10, m.IntResult);
        assertSame(t1, m.ObjResult);
        assertEquals("Test Message", m.Message);
        assertEquals("Test Note", m.Note);
        
        
        m = new AckNak(Reply.PossibleStatus.Failure, 20);
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Failure, m.Status);
        assertEquals(20, m.IntResult);
        assertNull(m.ObjResult);
        assertEquals("", m.Message);
        assertEquals("", m.Note);

        m = new AckNak(Reply.PossibleStatus.Failure, 20, "Test Message");
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Failure, m.Status);
        assertEquals(20, m.IntResult);
        assertNull(m.ObjResult);
        assertEquals("Test Message", m.Message);
        assertEquals("", m.Note);

        m = new AckNak(Reply.PossibleStatus.Failure, t1);
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Failure, m.Status);
        assertEquals(0, m.IntResult);
        assertSame(t1, m.ObjResult);
        assertEquals("", m.Message);
        assertEquals("", m.Note);

        m = new AckNak(Reply.PossibleStatus.Failure, t1, "Test Message");
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Failure, m.Status);
        assertEquals(0, m.IntResult);
        assertSame(t1, m.ObjResult);
        assertEquals("Test Message", m.Message);
        assertEquals("", m.Note);

	}
	@Test
	public void AckNak_CheckProperties()
    {
        Tick t1 = new Tick();
        AckNak m = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
        assertEquals(Reply.PossibleTypes.AckNak, m.ReplyType);
        assertEquals(Reply.PossibleStatus.Success, m.Status);
        assertEquals(10, m.IntResult);
        assertSame(t1, m.ObjResult);
        assertEquals("Test Message", m.Message);
        assertEquals("Test Note", m.Note);
        m.IntResult = 200;
        assertEquals(200, m.IntResult);

        m.ObjResult = null;
        assertNull(m.ObjResult);
        m.ObjResult = t1;
        assertSame(t1, m.ObjResult);

        m.Message = "Testing";
        assertEquals("Testing", m.Message);

        m.Note = "Test Note";
        assertEquals("Test Note", m.Note);
        
        assertEquals(Message.MESSAGE_CLASS_IDS.AckNak, m.MessageTypeId());
     }
	
	
	@Test
	public void AckNak_CheckEncodeDecode() throws NotActiveException, UnknownHostException, Exception
	{
	            Tick t1 = new Tick();
	            AckNak m1 = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
	            assertEquals(Reply.PossibleTypes.AckNak, m1.ReplyType);
	            assertEquals(Reply.PossibleStatus.Success, m1.Status);
	            assertEquals(10, m1.IntResult);
	            assertSame(t1, m1.ObjResult);
	            assertEquals("Test Message", m1.Message);
	            assertEquals("Test Note", m1.Note);

	            ByteList bytes = new ByteList();
	            m1.Encode(bytes);
	            AckNak m2 = AckNak.Create(bytes);
	            assertEquals(m1.Status, m2.Status);
	            assertEquals(m1.IntResult, m2.IntResult);
	            assertEquals(((Tick)m1.ObjResult).getLogicalClock(), ((Tick)m2.ObjResult).getLogicalClock());
	            assertEquals(m1.getMessage(), m2.getMessage());
	            assertEquals(m1.Note, m2.Note);

	            bytes.Clear();
	            m1.Encode(bytes);
	            bytes.GetByte();            // Read one byte, which will throw the length off
	            try
	            {
	                m2 = AckNak.Create(bytes);
	                fail("Expected an exception to be thrown");
	            }
	            catch (ApplicationException e ) {      }

	            bytes.Clear();
	            m1.Encode(bytes);
	            bytes.Add((byte)100);       // Add a byte
	            bytes.GetByte();            // Read one byte, which will make the ID wrong
	            try
	            {
	                m2 = AckNak.Create(bytes);
	                fail("Expected an exception to be thrown");
	            }
	            catch (ApplicationException e) {     }

	        }
	
}
