package CommonTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.net.InetSocketAddress;




//import junit.framework.Assert;
import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.AgentInfo;
import Common.AgentInfo.PossibleAgentStatus;
import Common.ByteList;
import Common.EndPoint;
import Common.FieldLocation;
import Common.StateChange;

public class AgentInfoTester 
{
	private StateChange recentStateChange = null;

	@Test
	public void AgentInfo_CheckConstructors() throws Exception {
		 AgentInfo info = new AgentInfo();
		 assertEquals(0, info.getId());
		 assertNull(info.getANumber());
		 assertNull(info.getFirstName());
		 assertNull(info.getLastName());
         assertEquals((Double)0.0, info.getStrength());
         assertEquals((Double)0.0, info.getSpeed());
         assertEquals((Double)0.0, info.getPoints());
         assertNull(info.getLocation());
         assertNull(info.getCommunicationEndPoint());

         info = new AgentInfo((short) 10, AgentInfo.PossibleAgentType.ExcuseGenerator);
         info.setStrength(20.0);
         assertEquals(10, info.getId());
         assertNull(info.getANumber());
         assertNull(info.getFirstName());
         assertNull(info.getLastName());
         assertEquals((Double)20.0, info.getStrength());
         
         assertEquals((Double)0.0, info.getSpeed());
         assertNull(info.getLocation());
         assertNull(info.getCommunicationEndPoint());
         assertEquals(AgentInfo.PossibleAgentType.ExcuseGenerator, info.getAgentType());
         
         InetSocketAddress inetSocketAddress = new InetSocketAddress("129.123.7.24", 1345);
         EndPoint ep = new EndPoint(inetSocketAddress);
         info = new AgentInfo((short) 20, AgentInfo.PossibleAgentType.WhiningSpinner, ep);
         assertEquals(20, info.getId());
         assertNull(info.getANumber());
         assertNull(info.getFirstName());
         assertNull(info.getLastName());
         assertEquals((Double)0.0, info.getStrength());
         assertEquals((Double)0.0, info.getSpeed());
         assertNull(info.getLocation());
         assertSame(ep, info.getCommunicationEndPoint());
         assertEquals(AgentInfo.PossibleAgentType.WhiningSpinner, info.getAgentType());
         
        
	}

	@Test
	public void AgentInfo_CheckProperties() throws Exception
    {
        EndPoint ep = new EndPoint("129.123.7.24:1345");
        AgentInfo info = new AgentInfo((short)20, AgentInfo.PossibleAgentType.WhiningSpinner, ep);
        info.setANumber("A00001");
        info.setFirstName("Joe");
        info.setLastName("Jones");
        info.setLocation(new FieldLocation((short)10, (short)20, false));
        info.setStrength(1200.5);
        info.setSpeed(1500.0);
        info.setPoints(3332.42);

        assertEquals(20, info.getId());
        assertEquals(AgentInfo.PossibleAgentType.WhiningSpinner, info.getAgentType());
        assertEquals("A00001", info.getANumber());
        assertEquals("Joe", info.getFirstName());
        assertEquals("Jones", info.getLastName());
        assertEquals((Double)1200.5, info.getStrength());
        assertEquals((Double)1500.0, info.getSpeed());
        assertEquals((Double)3332.42, info.getPoints());
        assertEquals(10, info.getLocation().getX());
        assertEquals(20, info.getLocation().getY());
        assertSame(ep, info.getCommunicationEndPoint());
    }
	
	@Test
	 public void AgentInfo_CheckEncodeAndDecode() throws ApplicationException, Exception
    {
        EndPoint ep = new EndPoint("129.123.7.24:1345");
        AgentInfo info1 = new AgentInfo((short)20, AgentInfo.PossibleAgentType.WhiningSpinner, ep);
        info1.setANumber("A00001");
        info1.setFirstName("Joe");
        info1.setLastName("Jones");
        info1.setLocation(new FieldLocation((short)10, (short)20, false));
        info1.setStrength((Double) 1200.5);
        info1.setSpeed((Double)1500.0);
        info1.setAgentStatus(PossibleAgentStatus.InGame);

        ByteList bytes = new ByteList();
        info1.Encode(bytes);
        AgentInfo info2 = AgentInfo.Create(bytes);
        assertEquals(info1.getId(), info2.getId());
        assertEquals(info1.getAgentType(), info2.getAgentType());
        assertEquals(info1.getANumber(), info2.getANumber());
        assertEquals(info1.getFirstName(), info2.getFirstName());
        assertEquals(info1.getLastName(), info2.getLastName());
        assertEquals(info1.getStrength(), info2.getStrength());
        assertEquals(info1.getSpeed(), info2.getSpeed());
        assertEquals(info1.getPoints(), info2.getPoints());
        assertEquals(info1.getLocation().getX(), info2.getLocation().getX());
        assertEquals(info1.getLocation().getY(), info2.getLocation().getY());
        assertEquals(info1.getCommunicationEndPoint().getAddress(), info2.getCommunicationEndPoint().getAddress());
        assertEquals(info1.getCommunicationEndPoint().getPort(), info2.getCommunicationEndPoint().getPort());

        bytes.Clear();
        info1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
            info2 = AgentInfo.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }

        bytes.Clear();
        info1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
            info2 = AgentInfo.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }
    }

    private void ChangedEventHandler(StateChange stateChange)
    {
        recentStateChange = stateChange;
    }
}
