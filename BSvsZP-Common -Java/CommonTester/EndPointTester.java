package CommonTester;

import static org.junit.Assert.*;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.junit.Test;
import org.omg.CORBA.portable.ApplicationException;

import Common.BitConverter;
import Common.ByteList;
import Common.EndPoint;

public class EndPointTester {

	@Test
	public void EndPoint_CheckConstructors() throws Exception
    {
        EndPoint ep = new EndPoint();
        assertEquals(0, ep.getAddress());
        assertEquals(0, ep.getPort());

        byte[] addressBytes = new byte[4];
        addressBytes[0] = 10;
        addressBytes[1] = (byte) 211;
        addressBytes[2] = 55;
        addressBytes[3] = 20;

        ep = new EndPoint(addressBytes, 2001);
        byte[] tmpBytes = BitConverter.getBytes(ep.getAddress());
        assertEquals(10, tmpBytes[0]);
        assertEquals((byte)211, tmpBytes[1]);
        assertEquals(55, tmpBytes[2]);
        assertEquals(20, tmpBytes[3]);
        assertEquals(2001, ep.getPort());

        ep = new EndPoint(3255420, 3004);
        assertEquals(3255420, ep.getAddress());
        assertEquals(3004, ep.getPort());
    }

	@Test
	 public void EndPoint_CheckProperties()
    {
        EndPoint ep = new EndPoint(3255420, 3004);
        assertEquals(3255420, ep.getAddress());
        assertEquals(3004, ep.getPort());

        ep.setAddress(54365439);
        ep.setPort(4354);
        assertEquals(54365439, ep.getAddress());
        assertEquals(4354, ep.getPort());

        ep.setAddress(0);
        ep.setPort(0);
        assertEquals(0, ep.getAddress());
        assertEquals(0, ep.getPort());

        ep.setAddress(Integer.MAX_VALUE);
        ep.setPort(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, ep.getAddress());
        assertEquals(Integer.MAX_VALUE, ep.getPort());
    }

	@Test
	public void EndPoint_CheckEncodeAndDecode() throws NotActiveException, Exception
    {
        EndPoint ep1 = new EndPoint(3255420, 3004);
        assertEquals(3255420, ep1.getAddress());
        assertEquals(3004, ep1.getPort());

        ByteList bytes = new ByteList();
        ep1.Encode(bytes);
        EndPoint ep2 = EndPoint.Create(bytes);
        assertEquals(ep1.getAddress(), ep2.getAddress());
        assertEquals(ep1.getPort(), ep2.getPort());

        bytes.Clear();
        ep1.Encode(bytes);
        bytes.GetByte();            // Read one byte, which will throw the length off
        try
        {
        	ep2 = EndPoint.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}

        bytes.Clear();
        ep1.Encode(bytes);
        bytes.Add((byte)100);       // Add a byte
        bytes.GetByte();            // Read one byte, which will make the ID wrong
        try
        {
        	ep2 = EndPoint.Create(bytes);
        	fail("Expected an exception to be thrown");
        }
        catch (ApplicationException e) {}
    }
}
