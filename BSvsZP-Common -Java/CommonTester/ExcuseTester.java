package CommonTester;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.Excuse;
import Common.Tick;

public class ExcuseTester {

	@Test
	 public void Excuse_CheckConstructors()
    {
        Excuse e = new Excuse();
        assertEquals(0, e.CreatorId);
        assertNotNull(e.Ticks);
        assertEquals(0, e.Ticks.size());
        assertNull(e.RequestTick);

        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        
        List<Tick> ticks = new ArrayList<Tick>(Arrays.asList(t1, t2, t3));
        e = new Excuse((short)10, (ArrayList<Tick>) ticks, t4);
        assertEquals(10, e.CreatorId);
        assertNotNull(e.Ticks);
        assertEquals(3, e.Ticks.size());
        assertSame(t1, e.Ticks.get(0));
        assertSame(t2, e.Ticks.get(1));
        assertSame(t3, e.Ticks.get(2));
        assertSame(t4, e.RequestTick);
    }

	@Test
	public void Excuse_CheckProperties()
    {
        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        List<Tick> ticks = new ArrayList<Tick>(Arrays.asList( t1, t2, t3 ));
        Excuse e = new Excuse((short)10, (ArrayList<Tick>) ticks, t4);
        assertEquals(10, e.CreatorId);
        assertNotNull(e.Ticks);
        assertEquals(3, e.Ticks.size());
        assertSame(t1, e.Ticks.get(0));
        assertSame(t2, e.Ticks.get(1));
        assertSame(t3, e.Ticks.get(2));
        assertSame(t4, e.RequestTick);

        e.CreatorId = 135;
        assertEquals(135, e.CreatorId);
        e.CreatorId = 0;
        assertEquals(0, e.CreatorId);
        e.CreatorId = Short.MAX_VALUE;
        assertEquals(Short.MAX_VALUE, e.CreatorId);

        e.Ticks = null;
        assertNull(e.Ticks);
        e.Ticks = ticks;
        assertSame(ticks, e.Ticks);

        e.RequestTick = null;
        assertNull(e.RequestTick);
        e.RequestTick = t4;
        assertSame(t4, e.RequestTick);
    }
	
	@Test
	public void Excuse_CheckEncodeAndDecode() throws Exception
    {
        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        List<Tick> ticks = new ArrayList<Tick>(Arrays.asList(t1, t2, t3));
        Excuse e1 = new Excuse((short) 10, ( ArrayList<Tick>) ticks, t4);
        assertEquals(10, e1.CreatorId);
        assertNotNull(e1.Ticks);
        assertEquals(3, e1.Ticks.size());
        assertSame(t1, e1.Ticks.get(0));
        assertSame(t2, e1.Ticks.get(1));
        assertSame(t3, e1.Ticks.get(2));
        assertSame(t4, e1.RequestTick);

        ByteList bytes = new ByteList();
        e1.Encode(bytes);
        Excuse e2 = Excuse.Create(bytes);
        assertEquals(e1.CreatorId, e2.CreatorId);
        assertEquals(e1.Ticks.size(), e2.Ticks.size());
        assertEquals(e1.Ticks.get(0).getLogicalClock(), e2.Ticks.get(0).getLogicalClock());
        assertEquals(e1.Ticks.get(0).getHashCode(), e2.Ticks.get(0).getHashCode());
        assertEquals(e1.Ticks.get(1).getLogicalClock(), e2.Ticks.get(1).getLogicalClock());
        assertEquals(e1.Ticks.get(1).getHashCode(), e2.Ticks.get(1).getHashCode());
        assertEquals(e1.Ticks.get(2).getLogicalClock(), e2.Ticks.get(2).getLogicalClock());
        assertEquals(e1.Ticks.get(2).getHashCode(), e2.Ticks.get(2).getHashCode());
        assertEquals(e1.RequestTick.getLogicalClock(), e2.RequestTick.getLogicalClock());
        assertEquals(e1.RequestTick.getHashCode(), e2.RequestTick.getHashCode());

        bytes.Clear();
        e1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	e2 = Excuse.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

        bytes.Clear();
        e1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        
        try
        {
        	e2 = Excuse.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

    }
}
