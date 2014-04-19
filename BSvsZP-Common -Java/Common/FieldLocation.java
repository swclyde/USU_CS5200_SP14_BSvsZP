package Common;

import java.io.NotActiveException;

import org.omg.CORBA.portable.ApplicationException;

public class FieldLocation extends DistributableObject {

    private boolean xSet = false;
    private boolean ySet = false;
    private boolean immutable = false;
    private short X;
    private short Y;
    private static int MinimumEncodingLength;

    public FieldLocation(boolean... immutable) {
        boolean temp = (immutable.length > 0 ? immutable[0] : false);
        setImmutable(temp);
    }

    public FieldLocation(short x, short y, boolean... immutable) {
        setX(x);
        setY(y);
        boolean temp = (immutable.length > 0 ? immutable[0] : false);
        setImmutable(temp);
    }

    //new 
    public static FieldLocation Create(ByteList bytes) throws ApplicationException, Exception {
        FieldLocation result = new FieldLocation();
        result.Decode(bytes);
        return result;
    }

    public short getX() {
        return X;
    }

    public void setX(short value) {
        if (!immutable || !xSet) {
            X = value;
        }
        xSet = true;
    }

    public boolean getImmutable() {
        return immutable;
    }

    public void setImmutable(boolean immu) {
        immutable = immu;
    }

    public short getY() {
        return Y;
    }

    public void setY(short value) {
        if (!immutable || !ySet) {
            Y = value;
        }
        ySet = true;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 2 // X
                + 2 // Y
                + 1;           // Immutable
        return MinimumEncodingLength;
    }

    public static void setMinimumEncodingLength(int minimumEncodingLength) {
        MinimumEncodingLength = minimumEncodingLength;
    }

    public static short getClassId() {
        return (short)DISTRIBUTABLE_CLASS_IDS.FieldLocation.getValue();
    }
    public String ToString()
    {
        return String.format("{0}x{1}", X, Y);
    }
    @Override
    public void Encode(ByteList bytes) throws NotActiveException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.FieldLocation.getValue());                             // Write out the class type

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length

        bytes.AddObjects(this.getX(), this.getY(), this.getImmutable());
        // Write out X, Y, and Immutable properties

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);

    }

    @Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.FieldLocation.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else if (immutable) {
            throw new ApplicationException("Cannot use Decode to alter an immutable FieldLocation object", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            setX(bytes.GetInt16());
            setY(bytes.GetInt16());
            setImmutable(bytes.GetBool());
            bytes.RestorePreviosReadLimit();
        }
    }
}
