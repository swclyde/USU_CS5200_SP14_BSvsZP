package Common;

import java.io.NotActiveException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.omg.CORBA.portable.ApplicationException;

public class EndPoint extends DistributableObject {

    private static short ClassId;
    private int Address;
    private int Port;
    private static int MinimumEncodingLength;

    public EndPoint() {
    }

    public EndPoint(int address, int port) {
        setAddress(address);
        setPort(port);
    }

    public EndPoint(InetSocketAddress ep) throws Exception {
        if (ep != null) {
            byte[] byteAddress = ep.getAddress().getAddress();
            Address = BitConverter.toInt32(byteAddress, 0);
            Port = ep.getPort();
        }
    }

    public EndPoint(byte[] addressBytes, int port) throws Exception {
        this(0, port);
        if (addressBytes != null && addressBytes.length == 4) {
            setAddress(BitConverter.toInt32(addressBytes, 0));
        }
    }

    private int ParseAddress(String hostname) throws Exception {
        int result = 0;
        InetAddress[] addressList = InetAddress.getAllByName(hostname);
        if (addressList.length > 0) {
            result = BitConverter.toInt32(addressList[0].getAddress(), 0);
        }
        return result;
    }

    public EndPoint(String hostNameAndPort) throws Exception {
        if ((hostNameAndPort != null) && (!(hostNameAndPort.contains(" ")))) {
            String[] tmp = hostNameAndPort.split(":");
            if ((tmp.length == 2) && ((tmp[0] != null) && (tmp[0].contains(" ")))) {
                Address = ParseAddress(tmp[0]);
                Integer.parseInt(tmp[1], Port);
            }
        }
    }

    //new 
    public static EndPoint Create(ByteList bytes) throws ApplicationException, Exception {
        EndPoint result = new EndPoint();
        result.Decode(bytes);
        return result;
    }

    public short getClassId() {
        ClassId = (short) DISTRIBUTABLE_CLASS_IDS.EndPoint.getValue();
        return ClassId;
    }

    public int getAddress() {
        return Address;
    }

    public void setAddress(int address) {
        Address = address;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        Port = port;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 4 // Address
                + 4;           // Port
        return MinimumEncodingLength;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.EndPoint.getValue());                             // Write out the class type

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length

        bytes.AddObjects(getAddress(), getPort());

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);
		       // Write out the length of this object        

    }

    @Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.EndPoint.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            setAddress(bytes.GetInt32());
            setPort(bytes.GetInt32());

            bytes.RestorePreviosReadLimit();

        }
    }

    public InetSocketAddress GetIPEndPoint() {
        return new InetSocketAddress(String.valueOf(Address), Port);
    }

    public static boolean Match(EndPoint ep1, EndPoint ep2) {
        return (ep1.Address == ep2.Address && ep1.Port == ep2.Port);
    }

    public static boolean Match(InetSocketAddress ep1, InetSocketAddress ep2) {
        return (ep1.getAddress().getAddress()[0] == ep2.getAddress().getAddress()[0]
                && ep1.getAddress().getAddress()[1] == ep2.getAddress().getAddress()[1]
                && ep1.getAddress().getAddress()[2] == ep2.getAddress().getAddress()[2]
                && ep1.getAddress().getAddress()[3] == ep2.getAddress().getAddress()[3]
                && ep1.getPort() == ep2.getPort());
    }

    public boolean Equals(Object obj) {
        return (obj != null && obj.getClass() == EndPoint.class) && Match(this, (EndPoint) obj);
    }

    public int GetHashCode() {
        return super.hashCode();
    }

    public String ToString() {
        byte[] addressBytes = BitConverter.getBytes(Address);
        return MessageFormat.format("{0}.{1}.{2}.{3}:{4}",
                addressBytes[0],
                addressBytes[1],
                addressBytes[2],
                addressBytes[3],
                Port);
    }
}
