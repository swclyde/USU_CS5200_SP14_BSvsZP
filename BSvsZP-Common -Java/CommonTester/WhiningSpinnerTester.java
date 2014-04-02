package CommonTester;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.Tick;
import Common.WhiningTwine;

public class WhiningSpinnerTester {

	@Test
	public void WhiningSpinner_CheckConstructors()
    {
        WhiningTwine e = new WhiningTwine();
        assertEquals(0, e.getCreatorId());
        assertNotNull(e.getTicks());
        assertEquals(0, e.getTicks().size());
        assertNull(e.getRequestTick());

        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        ArrayList<Tick> ticks = new ArrayList<Tick>( Arrays.asList(t1, t2, t3));
        e = new WhiningTwine((short) 10, ticks, t4);
        assertEquals(10, e.getCreatorId());
        assertNotNull(e.getTicks());
        assertEquals(3, e.getTicks().size());
        assertSame(t1, e.getTicks().get(0));
        assertSame(t2, e.getTicks().get(1));
        assertSame(t3, e.getTicks().get(2));
        assertSame(t4, e.getRequestTick());
    }

	@Test
	public void WhiningSpinner_CheckProperties()
    {
        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        ArrayList<Tick> ticks = new ArrayList<Tick>(Arrays.asList(t1, t2, t3));
        WhiningTwine e = new WhiningTwine((short) 10, ticks, t4);
        assertEquals(10, e.getCreatorId());
        assertNotNull(e.getTicks());
        assertEquals(3, e.getTicks().size());
        assertSame(t1, e.getTicks().get(0));
        assertSame(t2, e.getTicks().get(1));
        assertSame(t3, e.getTicks().get(2));
        assertSame(t4, e.getRequestTick());

        e.setCreatorId((short) 135);
        assertEquals(135, e.getCreatorId());
        e.setCreatorId((short) 0);
        assertEquals(0, e.getCreatorId());
        e.setCreatorId( Short.MAX_VALUE);
        assertEquals(Short.MAX_VALUE, e.getCreatorId());

        e.setTicks(null);
        assertNull(e.getTicks());
        e.setTicks(ticks);
        assertSame(ticks, e.getTicks());

        e.setRequestTick(null);
        assertNull(e.getRequestTick());
        e.setRequestTick(t4);
        assertSame(t4, e.getRequestTick());
    }
	
	
	@Test
	public void WhiningSpinner_CheckEncodeAndDecode() throws UnknownHostException, Exception
    {
        Tick t1 = new Tick();
        Tick t2 = new Tick();
        Tick t3 = new Tick();
        Tick t4 = new Tick();
        ArrayList<Tick> ticks = new ArrayList<Tick>(Arrays.asList(t1, t2, t3 ));
        WhiningTwine e1 = new WhiningTwine((short)10, ticks, t4);
        assertEquals(10, e1.getCreatorId());
        assertNotNull(e1.getTicks());
        assertEquals(3, e1.getTicks().size());
        assertSame(t1, e1.getTicks().get(0));
        assertSame(t2, e1.getTicks().get(1));
        assertSame(t3, e1.getTicks().get(2));
        assertSame(t4, e1.getRequestTick());

        ByteList bytes = new ByteList();
        e1.Encode(bytes);
        WhiningTwine e2 = WhiningTwine.Create(bytes);
        assertEquals(e1.getCreatorId(), e2.getCreatorId());
        assertEquals(e1.getTicks().size(), e2.getTicks().size());
        assertEquals(e1.getTicks().get(0).getLogicalClock(), e2.getTicks().get(0).getLogicalClock());
        assertEquals(e1.getTicks().get(0).getHashCode(), e2.getTicks().get(0).getHashCode());
        assertEquals(e1.getTicks().get(1).getLogicalClock(), e2.getTicks().get(1).getLogicalClock());
        assertEquals(e1.getTicks().get(1).getHashCode(), e2.getTicks().get(1).getHashCode());
        assertEquals(e1.getTicks().get(2).getLogicalClock(), e2.getTicks().get(2).getLogicalClock());
        assertEquals(e1.getTicks().get(2).getHashCode(), e2.getTicks().get(2).getHashCode());
        assertEquals(e1.getRequestTick().getLogicalClock(), e2.getRequestTick().getLogicalClock());
        assertEquals(e1.getRequestTick().getHashCode(), e2.getRequestTick().getHashCode());

        bytes.Clear();
        e1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	e2 = WhiningTwine.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

        bytes.Clear();
        e1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
        	e2 = WhiningTwine.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

    }
	
}
