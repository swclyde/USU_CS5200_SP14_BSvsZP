package CommonTester;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.*;
public class PlayingFieldLayoutTester {

	@Test
	public void PlayingFieldLayout_CheckConstructors()
    {
        PlayingFieldLayout pfl = new PlayingFieldLayout();
        assertEquals(0, pfl.getWidth());
        assertEquals(0, pfl.getHeight());
        assertNotNull(pfl.SidewalkSquares);

        pfl = new PlayingFieldLayout((short)20, (short)25);
        assertEquals(20, pfl.getWidth());
        assertEquals(25, pfl.getHeight());
        assertNotNull(pfl.SidewalkSquares);            
    }
	
	@Test
	 public void PlayingFieldLayout_CheckProperties()
    {
        PlayingFieldLayout pfl = new PlayingFieldLayout((short)20, (short)30);
        assertEquals(20, pfl.getWidth());
        assertEquals(30, pfl.getHeight());
        assertNotNull(pfl.SidewalkSquares);

        pfl.setWidth((short) 0);
        assertEquals(0, pfl.getWidth());
        pfl.setWidth((short) 35);
        assertEquals(35, pfl.getWidth());

        pfl.setHeight((short) 0);
        assertEquals(0, pfl.getHeight());
        pfl.setHeight((short) 45);
        assertEquals(45, pfl.getHeight());

        ArrayList<FieldLocation> flList = new ArrayList<FieldLocation>
        			(Arrays.asList(new FieldLocation((short)1, (short)1), 
        						   new FieldLocation((short)2, (short)1)));
        pfl.SidewalkSquares = flList;
        assertSame(flList, pfl.SidewalkSquares);
        pfl.SidewalkSquares = null;
        assertNull(pfl.SidewalkSquares);

    }

	@Test
	 public void PlayingFieldLayout_CheckEncodeAndDecode() throws Exception
    {
        PlayingFieldLayout pfl1 = new PlayingFieldLayout((short) 20, (short)30);
        assertEquals(20, pfl1.getWidth());
        assertEquals(30, pfl1.getHeight());
        assertNotNull(pfl1.SidewalkSquares);

        List<FieldLocation> flList = new ArrayList<FieldLocation>(
        				Arrays.asList(new FieldLocation((short)1, (short)1),
        							  new FieldLocation((short)2, (short)1) ));
        pfl1.SidewalkSquares = (ArrayList<FieldLocation>) flList;
        assertSame(flList, pfl1.SidewalkSquares);

        ByteList bytes = new ByteList();
        pfl1.Encode(bytes);
        PlayingFieldLayout pfl2 = PlayingFieldLayout.Create(bytes);
        assertEquals(pfl1.getWidth(), pfl2.getWidth());
        assertEquals(pfl1.getHeight(), pfl2.getHeight());
        assertNotNull(pfl2.SidewalkSquares);
        assertEquals(pfl1.SidewalkSquares.size(), pfl1.SidewalkSquares.size());

        bytes.Clear();
        pfl1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	pfl2 = PlayingFieldLayout.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}
        
        bytes.Clear();
        pfl1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
        	pfl2 = PlayingFieldLayout.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}
        	
    }
}
