package CommonTester;

import static org.junit.Assert.*;
import org.junit.Test;
import Common.ByteList;

public class ByteListTester {

	@Test
	 public void ByteList_TestConstuctors() throws Exception
    {
        // Check out the default constructor
        ByteList myBytes = new ByteList();
        assertNotNull(myBytes);
        assertEquals(0, myBytes.getLength());

        // Check out the general constructor that take any number of objects
        // Case 1: A single boolean object
        myBytes = new ByteList(true);
        assertNotNull(myBytes);
        assertEquals(1, myBytes.getLength());//.get_sections().size());
        assertEquals((byte)1, myBytes.getByteValue(0));

        // Case 2: 3 different objects
        myBytes = new ByteList(true, 123,"Hello"); //
        assertNotNull(myBytes);
        assertEquals(1 + 4 + (2 + (2*5)), myBytes.getLength());

        // Case 3: 3 strings of lengths 5, 5, and 52
        myBytes = new ByteList("Hello", "There", "You amazing software developer and brilliant student");
        assertNotNull(myBytes);
        assertEquals((2 + 2*5 ) + (2 + 2*5 ) + (2 + 2*52 ), myBytes.getLength());
             
        // Case 4: with a bunch of other parameters types
        ByteList moreBytes = new ByteList(  myBytes,
                                            (short)10,
                                            (long)20,
                                            (float)30.0, // changed from Single to float 
                                            (double)40.0); // changed from Double to double
        assertNotNull(moreBytes);
        assertEquals(myBytes.getLength() + 2 + 8 + 4 + 8 , moreBytes.getLength());
        
        
        // Case 5: with a byte[]
        ByteList addmoreBytes = new ByteList(new byte[] { 1, 2, 3 });      
        assertNotNull(addmoreBytes);
        assertEquals(addmoreBytes.getLength(), addmoreBytes.getLength());
    }

	@Test
	public void ByteList_WriteAndAddMethods() throws Exception
    {
        ByteList myBytes = new ByteList();

        // Case 1: Write out a boolean of True
        myBytes.Clear();
        myBytes.Add(true);
        assertNotNull(myBytes);
        assertEquals(1, myBytes.getLength());
        assertEquals((byte) 1, myBytes.getByteValue(0));

        // Case 2: Write out a boolean of False
        myBytes.Clear();
        myBytes.Add(false);
        assertNotNull(myBytes);
        assertEquals(1, myBytes.getLength());
        assertEquals((byte) 0, myBytes.getByteValue(0)); // changed from myBytes[0] to myBytes.GetByteList(0)
        
        // Case 3: Write out a Byte
        myBytes.Clear();
        myBytes.Add((byte)4);
        assertNotNull(myBytes);
        assertEquals(1, myBytes.getLength());
        assertEquals((byte) 4, myBytes.getByteValue(0));

        // Case 4: Write out a Char
        myBytes.Clear();
        myBytes.Add('A');
        assertNotNull(myBytes);
        assertEquals(2, myBytes.getLength());
        assertEquals(65, myBytes.getByteValue(0));
        assertEquals(0, myBytes.getByteValue(1));

        // Case 5: Write out a Int16
        myBytes.Clear();
        myBytes.Add((short) 7);
        assertNotNull(myBytes);
        assertEquals(2, myBytes.getLength());
        assertEquals(0, myBytes.getByteValue(0));
        assertEquals(7, myBytes.getByteValue(1));

        // Case 6: Write out a Int16
        myBytes.Clear();
        myBytes.Add(Short.MAX_VALUE);
        assertNotNull(myBytes);
        assertEquals(2, myBytes.getLength());
        assertEquals((byte) 127, myBytes.getByteValue(0));
        assertEquals((byte)255, myBytes.getByteValue(1));

        // Case 7: Write out a Int32
        myBytes.Clear();
        myBytes.Add((int) 7);
        assertNotNull(myBytes);
        assertEquals(4, myBytes.getLength());
        for (int i = 0; i < 3; i++) 
        	assertEquals((byte)0, myBytes.getByteValue(0));
        assertEquals((byte)7, myBytes.getByteValue(3));

        // Case 8: Write out a Int32
        myBytes.Clear();
        myBytes.Add(Integer.MAX_VALUE);
        assertNotNull(myBytes);
        assertEquals(4, myBytes.getLength());
        assertEquals((byte)127, myBytes.getByteValue(0));
        for (int i = 1; i < 4; i++) 
        	assertEquals((byte)255, myBytes.getByteValue(i));

        // Case 9: Write out a Int64
        myBytes.Clear();
        myBytes.Add((long) 7);
        assertNotNull(myBytes);
        assertEquals(8, myBytes.getLength());
        for (int i=0; i<7; i++) 
        	assertEquals((byte)0, myBytes.getByteValue(i));
        assertEquals((byte)7, myBytes.getByteValue(7));

        // Case 10: Write out a Int64
        myBytes.Clear();
        myBytes.Add(Long.MAX_VALUE);
        assertNotNull(myBytes);
        assertEquals(8, myBytes.getLength());
        assertEquals(127,myBytes.getByteValue(0));
        for (int i = 1; i < 8; i++) 
        	assertEquals((byte)255, myBytes.getByteValue(i));

        // Case 11: Write out a Single Precision Real
        myBytes.Clear();
        myBytes.Add((float) 7.7 );
        assertNotNull(myBytes);
        assertEquals(4, myBytes.getLength());

        // Case 12: Write out a Double Precision Real
        myBytes.Clear();
        myBytes.Add((float)7.7);
        assertNotNull(myBytes);
        assertEquals(4, myBytes.getLength());

        // Case 13: Write out a Byte Array
        myBytes.Clear();
        myBytes.Add(new byte[] { 1, 2, 3, 4, 5, 6 });
        assertEquals(6, myBytes.getLength());
        for (int i = 0; i < 6; i++)
        	assertEquals((byte)(i+1), myBytes.getByteValue(i));

        // Case 14: Write out a string
        myBytes.Clear();
        myBytes.Add((String) null);
        assertEquals(2, myBytes.getLength());
        assertEquals((byte)0, myBytes.getByteValue(0));
        assertEquals((byte)0, myBytes.getByteValue(1));

        // Case 15: Write out a string
        myBytes.Clear();
        String str = "";
        myBytes.Add(str);
        assertEquals(2 , myBytes.getLength());
        assertEquals((byte)0, myBytes.getByteValue(0));
        assertEquals((byte) 0, myBytes.getByteValue(1));

        // Case 16: Write out a string
        myBytes = new ByteList("abc");
        System.out.println(myBytes.getLength());
        assertEquals(2 + 2*3, myBytes.getLength());
        assertEquals((byte)0, myBytes.getByteValue(0));
        assertEquals((byte) (2 + 2*3), myBytes.getByteValue(1));

        // Note AddObjects and AddObject methods were tested with constructors
   }
    
}
