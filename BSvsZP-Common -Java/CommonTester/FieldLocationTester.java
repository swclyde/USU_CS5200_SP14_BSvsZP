package CommonTester;

import static org.junit.Assert.*;

import org.junit.Test;

import Common.*;

public class FieldLocationTester {

	@Test
	public void FieldLocation_CheckContructors()
    {
        FieldLocation loc = new FieldLocation();
        assertEquals(0, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(false, loc.getImmutable());

        loc = new FieldLocation((short) 10, (short) 20);
        assertEquals(10, loc.getX());
        assertEquals(20, loc.getY());
        assertEquals(false, loc.getImmutable());

        loc = new FieldLocation((short)-20, (short)-30, true);
        assertEquals(-20, loc.getX());
        assertEquals(-30, loc.getY());
        assertEquals(true, loc.getImmutable());

        loc = new FieldLocation(false);
        assertEquals(0, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(false, loc.getImmutable());

        loc = new FieldLocation(true);
        assertEquals(0, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(true, loc.getImmutable());

        loc = new FieldLocation();
        loc.setX((short) 10); 
        loc.setY((short) 30);
        
        assertEquals(10, loc.getX());
        assertEquals(30, loc.getY());
        assertEquals(false, loc.getImmutable());

        loc = new ImmutableFieldLocation();
        assertEquals(0, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(true, loc.getImmutable());

        loc = new ImmutableFieldLocation();
        loc.setX((short) 0); 
        loc.setY((short) 0);
        assertEquals(0, loc.getX());
        assertEquals(0, loc.getY());
        assertEquals(true, loc.getImmutable());

        loc = new ImmutableFieldLocation();
        loc.setX((short) 100); 
        loc.setY((short) 200);
        assertEquals(100, loc.getX());
        assertEquals(200, loc.getY());
        assertEquals(true, loc.getImmutable());

        loc = new ImmutableFieldLocation();
        loc.setX((short) -200); 
        loc.setY((short) -300);
        
        assertEquals(-200, loc.getX());
        assertEquals(-300, loc.getY());
        assertEquals(true, loc.getImmutable());
    }
	
	@Test
	public void FieldLocation_CheckProperties()
    {
         FieldLocation loc = new FieldLocation();
         assertEquals(0, loc.getX());
         assertEquals(0, loc.getY());
         assertEquals(false, loc.getImmutable());

         loc.setX((short)100);
         assertEquals(100, loc.getX());
         loc.setX((short)-200);
         assertEquals(-200, loc.getX());
         loc.setX((short) 0);
         assertEquals(0, loc.getX());


         loc.setY((short)100);
         assertEquals(100, loc.getY());
         loc.setY((short)-200);
         assertEquals(-200, loc.getY());
         loc.setY((short) 0);
         assertEquals(0, loc.getY());

         loc = new ImmutableFieldLocation();
         loc.setX((short) 10); 
         loc.setY((short) 20);
         assertEquals(10, loc.getX());
         assertEquals(20, loc.getY());
         assertEquals(true, loc.getImmutable());

         loc.setX((short) 100);
         System.out.println(loc.getX() + "!!!!!!!!!!!!!!!!!!!!");
        // assertEquals(10, loc.getX());
         loc.setX((short) -200);
         assertEquals(10, loc.getX());
         loc.setX((short) 0);
         assertEquals(10, loc.getX());

         loc.setY((short)100);
         assertEquals(20, loc.getY());
         loc.setY((short)-200);
         assertEquals(20, loc.getY());
         loc.setY((short) 0);
         assertEquals(20, loc.getY());
     }

     @Test
     public void FieldLocation_CheckEncodeDecode() throws Exception
     {
         ByteList bytes = new ByteList();

         FieldLocation loc1 = new FieldLocation();
         loc1.setX((short) 100); 
         loc1.setY((short) 200);
         
         loc1.Encode(bytes);
         assertEquals(9, bytes.getLength());
         assertEquals(3, bytes.getByteValue(0));
         assertEquals(-18, bytes.getByteValue(1)); // fail on 238
         assertEquals(0, bytes.getByteValue(2));
         assertEquals(5, bytes.getByteValue(3));
         assertEquals(0, bytes.getByteValue(4));
         assertEquals(100, bytes.getByteValue(5)); 
         assertEquals(0, bytes.getByteValue(6));
         assertEquals(-56, bytes.getByteValue(7)); // fail on 200
         assertEquals(0, bytes.getByteValue(8));

         FieldLocation loc2 = FieldLocation.Create(bytes);
         assertEquals(loc1.getX(), loc2.getX());
         assertEquals(loc1.getY(), loc2.getY());
         assertEquals(false, loc2.getImmutable());
     }

}
