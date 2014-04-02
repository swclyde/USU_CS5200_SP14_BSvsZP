package MessagesTester;

import static org.junit.Assert.*;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.AgentInfo;
import Common.AgentInfo.PossibleAgentStatus;
import Common.ByteList;
import Common.ComponentInfo;
import Messages.JoinGame;
import Messages.Message;

public class JoinGameTester {

	@Test
	 public void JoinGame_TestConstructorsAndFactories() throws Exception
    {
        JoinGame jg = new JoinGame();
        assertEquals(0, jg.getGameId());
        assertNull(jg.getAgentInfo());

        AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
        agentInfo.setFirstName("Joe");
        agentInfo.setLastName("Jones");
        agentInfo.setANumber("A00123");
        agentInfo.setAgentStatus(PossibleAgentStatus.InGame);
        
        jg = new JoinGame((short) 10, agentInfo);
        
        assertEquals(10, jg.getGameId());
        assertSame(agentInfo, jg.getAgentInfo());
        assertSame(agentInfo, jg.getAgentInfo());

        ByteList bytes = new ByteList();
        
        jg.Encode(bytes);
        
        Message msg = Message.Create(bytes);
        
        assertNotNull(msg);
        
        
        JoinGame jg2 = (JoinGame) msg ;
        
        assertEquals(jg.getGameId(), jg2.getGameId());
    }

	@Test
	public void JoinGame_Properties()
    {
		AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
		agentInfo.setFirstName("Joe");
	    agentInfo.setLastName("Jones");
	    agentInfo.setANumber("A00123");
	    agentInfo.setAgentStatus(PossibleAgentStatus.InGame);
	    
        JoinGame jg = new JoinGame((short)10, agentInfo);
        
        assertEquals(10, jg.getGameId());
        assertEquals("A00123", jg.getAgentInfo().getANumber());
        assertEquals("Jones", jg.getAgentInfo().getLastName());
        assertEquals("Joe", jg.getAgentInfo().getFirstName());
        assertSame(agentInfo, jg.getAgentInfo());

        jg.setGameId((short) 20);
        assertEquals(20, jg.getGameId());

        jg.setAgentInfo(null);
        assertNull(jg.getAgentInfo());
        
        jg.setAgentInfo(agentInfo);
        assertSame(agentInfo, jg.getAgentInfo());
        
        
        assertEquals(Message.MESSAGE_CLASS_IDS.JoinGame.getValue(), jg.MessageTypeId().getValue());
        assertEquals(Message.MESSAGE_CLASS_IDS.JoinGame, jg.MessageTypeId());
    }
   
	@Test
	 public void JoinGame_EncodingAndDecoding() throws ApplicationException, Exception
    {
        AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
        agentInfo.setFirstName("Joe");
        agentInfo.setLastName("Jones");
        agentInfo.setANumber("A00123");
        agentInfo.setAgentStatus(PossibleAgentStatus.InGame);
        
        JoinGame jg1 = new JoinGame((short) 10, agentInfo);
        
        assertEquals(10, jg1.getGameId());
        assertSame(agentInfo, jg1.getAgentInfo());
        
        ByteList bytes = new ByteList();
       
        jg1.Encode(bytes);
    
    	
		JoinGame jg2 = JoinGame.Create(bytes);
		
		        		
        assertEquals(jg1.getGameId(), jg2.getGameId());
      
        bytes.Clear();
        jg1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
            jg2 = JoinGame.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }

        bytes.Clear();
        jg1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
            jg2 = JoinGame.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }
    }
	
	@Test
	public void JoinGame_EncodingAndDecodingTester() throws ApplicationException, Exception
	{
	     AgentInfo agentInfo = new AgentInfo((short) 1001, AgentInfo.PossibleAgentType.BrilliantStudent);
	     agentInfo.setFirstName("Joe");
	     agentInfo.setLastName("Jones");
	     agentInfo.setANumber("A00001");
	     agentInfo.setAgentStatus(AgentInfo.PossibleAgentStatus.InGame);
	        
	     assertEquals(1001, agentInfo.getId());
	     assertEquals(AgentInfo.PossibleAgentType.BrilliantStudent, agentInfo.getAgentType());
	     assertEquals("Joe", agentInfo.getFirstName());
	     assertEquals("Jones", agentInfo.getLastName());
	     assertEquals("A00001", agentInfo.getANumber());
	     assertEquals(AgentInfo.PossibleAgentStatus.InGame, agentInfo.getAgentStatus());
	     
	     
	     JoinGame jg = new JoinGame((short) 10, agentInfo);
	     ByteList bytes = new ByteList();
	        
	     jg.Encode(bytes);
	     assertEquals(10, jg.getGameId());
	     assertEquals("Joe", jg.getAgentInfo().getFirstName());
	     assertEquals("Jones", jg.getAgentInfo().getLastName());
	     assertEquals("A00001", jg.getAgentInfo().getANumber());
	     assertEquals(AgentInfo.PossibleAgentStatus.InGame, jg.getAgentInfo().getAgentStatus());
	     
	     
	     byte[] b = bytes.GetBytes(bytes.getLength());
	     for(int i =0; i<b.length; ++i)
	    	 System.out.println(b[i]);
	 }
	
}
