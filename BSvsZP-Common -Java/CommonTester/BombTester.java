package CommonTester;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.*;

public class BombTester {

	@Test
	public void Bomb_CheckConstructors()
    {
        Bomb b = new Bomb();
        assertEquals(0, b.CreatorId);
        assertNotNull(b.Excuses);
        assertNotNull(b.Twine);
        assertEquals(0, b.Excuses.size());
        assertEquals(0, b.Twine.size());
        assertNull(b.BuiltOnTick);

        Tick t1 = new Tick();
        ArrayList<Excuse> eList = new ArrayList<Excuse>();
        eList.add(CreateExcuse((short) 10));
        eList.add(CreateExcuse((short) 11));
        eList.add(CreateExcuse((short) 12));
        
        ArrayList<WhiningTwine> wtList = new ArrayList<WhiningTwine>();
        wtList.add(CreateTwine((short) 20));
        wtList.add(CreateTwine((short) 21));
        wtList.add(CreateTwine((short) 22));
        
        b = new Bomb((short) 1, eList, wtList, t1);
        assertEquals(1, b.CreatorId);
        assertSame(eList, b.Excuses);
        assertSame(wtList, b.Twine);
        assertSame(t1, b.BuiltOnTick);
    }
	
	@Test
	public void Bomb_CheckProperties()
    {
        Tick t1 = new Tick();
        
        ArrayList<Excuse> eList = new ArrayList<Excuse>
		(Arrays.asList(CreateExcuse((short) 10),
					   CreateExcuse((short) 11), 
					   CreateExcuse((short) 12)));
        
        ArrayList<WhiningTwine> wtList = new ArrayList<WhiningTwine>
		(Arrays.asList(CreateTwine((short) 20),
		  			   CreateTwine((short) 21),
					   CreateTwine((short) 22)));
        
        
        Bomb b = new Bomb((short)1, eList, wtList, t1);
        assertEquals(1, b.CreatorId);
        assertSame(eList, b.Excuses);
        assertSame(wtList, b.Twine);
        assertSame(t1, b.BuiltOnTick);

        b.CreatorId = 135;
        assertEquals(135, b.CreatorId);
        b.CreatorId = 0;
        assertEquals(0, b.CreatorId);
        b.CreatorId = Short.MAX_VALUE;
        assertEquals(Short.MAX_VALUE, b.CreatorId);

        b.Excuses = new ArrayList<Excuse>();
        assertNotNull(b.Excuses);
        assertNotSame(eList, b.Excuses);
        b.Excuses = eList;
        assertSame(eList, b.Excuses);

        b.Twine = new ArrayList<WhiningTwine>();
        assertNotNull(b.Twine);
        assertNotSame(wtList, b.Twine);
        b.Twine = wtList;
        assertSame(wtList, b.Twine);
    }

	@Test
	 public void Bomb_CheckEncodeAndDecode() throws Exception
    {
        Tick t1 = new Tick();
        ArrayList<Excuse> eList = new ArrayList<Excuse>
        		(Arrays.asList(CreateExcuse((short) 10),
        					   CreateExcuse((short) 11), 
        					   CreateExcuse((short) 12)));
        
        ArrayList<WhiningTwine> wtList = new ArrayList<WhiningTwine>
        		(Arrays.asList(CreateTwine((short) 20),
        		  			   CreateTwine((short) 21),
        					   CreateTwine((short) 22)));
        
        Bomb b1 = new Bomb((short) 1, eList, wtList, t1);
        
        assertEquals(1, b1.CreatorId);
        assertSame(eList, b1.Excuses);
        assertSame(wtList, b1.Twine);
        assertSame(t1, b1.BuiltOnTick);

        ByteList bytes = new ByteList();
        b1.Encode(bytes);
        Bomb b2 = Bomb.Create(bytes);
        assertEquals(b1.CreatorId, b2.CreatorId);
        assertEquals(b1.Excuses.size(), b2.Excuses.size());
        assertEquals(b1.Twine.size(), b2.Twine.size());
        assertEquals(b1.BuiltOnTick.getLogicalClock(), b2.BuiltOnTick.getLogicalClock());
        assertEquals(b1.BuiltOnTick.getHashCode(), b2.BuiltOnTick.getHashCode());

        bytes.Clear();
        b1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	b2 = Bomb.Create(bytes);
        	fail("Expected an exception to be thrown");
        } catch (Exception e){}

        bytes.Clear();
        b1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        
        try
        {
        	b2 = Bomb.Create(bytes);
        	fail("Expected an exception to be thrown");
        } catch (Exception e) {}
        
    }
	
	private Excuse CreateExcuse(short id)
    {
        return new Excuse(id, new ArrayList<Tick>(Arrays.asList(new Tick(), new Tick())), new Tick());
    }

    private WhiningTwine CreateTwine(short id)
    {
    	return new WhiningTwine(id, new ArrayList<Tick>(Arrays.asList(new Tick(), new Tick())), new Tick());
    }



}
