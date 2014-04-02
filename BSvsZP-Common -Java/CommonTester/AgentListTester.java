package CommonTester;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.AgentInfo;
import Common.AgentInfo.PossibleAgentStatus;
import Common.AgentList;
import Common.ByteList;

public class AgentListTester {

	@Test
	public void AgentList_CheckConstructors()
    {
        AgentList list = new AgentList();
        assertEquals(0, list.Count());
    }

	@Test
	public void AgentList_CheckProperties()
    {
        AgentInfo info1 = new AgentInfo((short) 10, AgentInfo.PossibleAgentType.ExcuseGenerator);
        AgentInfo info2 = new AgentInfo((short) 11, AgentInfo.PossibleAgentType.WhiningSpinner);
        AgentInfo info3 = new AgentInfo((short) 12, AgentInfo.PossibleAgentType.BrilliantStudent);

        AgentList list = new AgentList();
        list.Add(info1);
        list.Add(info2);
        list.Add(info3);
        assertSame(info1, list.getAgentInfo(0));
        assertSame(info2, list.getAgentInfo(1));
        assertSame(info3, list.getAgentInfo(2));
    }
	
	@Test
	public void AgentList_CheckEncodeAndDecode() throws UnknownHostException, Exception
    {
        AgentInfo info1 = new AgentInfo((short) 10, AgentInfo.PossibleAgentType.ExcuseGenerator);
        info1.setAgentStatus(PossibleAgentStatus.InGame);
        AgentInfo info2 = new AgentInfo((short) 11, AgentInfo.PossibleAgentType.WhiningSpinner);
        info2.setAgentStatus(PossibleAgentStatus.InGame);
        AgentInfo info3 = new AgentInfo((short) 12, AgentInfo.PossibleAgentType.BrilliantStudent);
        info3.setAgentStatus(PossibleAgentStatus.InGame);

        AgentList list1 = new AgentList();
        list1.Add(info1);
        list1.Add(info2);
        list1.Add(info3);

        ByteList bytes = new ByteList();
        bytes.update();
        list1.Encode(bytes);
        bytes.update();
        AgentList list2 = AgentList.Create(bytes);
        assertEquals(3, list2.Count());
        assertEquals(10, list2.getAgentInfo(0).getId());
        assertEquals(11, list2.getAgentInfo(1).getId());
        assertEquals(12, list2.getAgentInfo(2).getId());

        bytes.Clear();
        list1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
            list2 = AgentList.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }

        bytes.Clear();
        list1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
            list2 = AgentList.Create(bytes);
            fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e)
        {
        }

    }
}
