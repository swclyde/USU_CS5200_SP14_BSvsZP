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
         System.out.println("=== info.getSpeed() === " + info.getSpeed());
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
         System.out.println("=== info.getStrength() === " + info.getStrength());
         assertEquals((Double)0.0, info.getStrength());
         assertEquals((Double)0.0, info.getSpeed());
         assertNull(info.getLocation());
         assertSame(ep, info.getCommunicationEndPoint());
         assertEquals(AgentInfo.PossibleAgentType.WhiningSpinner, info.getAgentType());
         
         System.out.println("info.getAgentType().getValue() ======" + info.getAgentType().getValue());
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
    
       // info.handler += ChangedEventHandler;

        // Id Property
       /* recentStateChange = null;
        info.setId((short) 1002);
        assertEquals(1002, info.getId());
        assertNull(recentStateChange);
        
        System.out.println("recentStateChange.Type = " + recentStateChange.Type);
        System.out.println("StateChange.ChangeType.UPDATE =" + StateChange.ChangeType.UPDATE);
        
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        recentStateChange = null;
        info.setId((short)0);
        assertEquals(0, info.getId());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        info.setId(Short.MAX_VALUE);
        assertEquals(Short.MAX_VALUE, info.getId());
        info.setId((short)10);
        assertEquals(10, info.getId());

        // AgentType
        recentStateChange = null;
        info.setAgentType(AgentInfo.PossibleAgentType.BrilliantStudent);
        assertEquals(AgentInfo.PossibleAgentType.BrilliantStudent, info.getAgentType());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        // ANumber
        recentStateChange = null;
        info.setANumber("A000234");
        assertEquals("A000234", info.getANumber());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        info.setANumber(null);
        assertNull(info.getANumber());
        info.setANumber("A012345");
        assertEquals("A012345", info.getANumber());

        // FirstName
        recentStateChange = null;
        info.setFirstName("Henry");
        assertEquals("Henry", info.getFirstName());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        info.setFirstName(null);
        assertNull(info.getFirstName());
        info.setFirstName("John");
        assertEquals("John", info.getFirstName());

        // LastName
        recentStateChange = null;
        info.setLastName("Franks");
        assertEquals("Franks", info.getLastName());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        info.setLastName(null);
        assertNull(info.getLastName());
        info.setLastName("Jones");
        assertEquals("Jones", info.getLastName());

        // Strength
        recentStateChange = null;
        info.setStrength(123.45);
        assertEquals((Double)123.45, info.getStrength());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        // Speed
        recentStateChange = null;
        info.setSpeed((Double)23.456);
        assertEquals((Double)23.456, info.getSpeed());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        // Speed
        recentStateChange = null;
        info.setPoints(53.6);
        assertEquals((Double)53.6, info.getPoints());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        // Location
        recentStateChange = null;
        FieldLocation f = new FieldLocation((short)10, (short)20);
        info.setLocation(f);
        assertSame(f, info.getLocation());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);

        // CommunicationEndPoint
        recentStateChange = null;
        EndPoint ep1 = new EndPoint(3242, 1000);
        info.setCommmunicationEndPoint(ep1);
        assertSame(ep1, info.getCommunicationEndPoint());
        assertNotNull(recentStateChange);
        assertEquals(recentStateChange.Type, StateChange.ChangeType.UPDATE);
        assertSame(info, recentStateChange.Subject);
*/
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
