package Common;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.omg.CORBA.portable.ApplicationException;

public class PlayingFieldLayout extends DistributableObject {

    private short Width;
    private short Height;
    public ArrayList<FieldLocation> SidewalkSquares;
    private static int MinimumEncodingLength;

    public PlayingFieldLayout() {
        SidewalkSquares = new ArrayList<>();
    }

    public PlayingFieldLayout(short width, short height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public static PlayingFieldLayout Create(ByteList bytes) throws ApplicationException, Exception {
        PlayingFieldLayout result = new PlayingFieldLayout();
        result.Decode(bytes);
        return result;
    }

    public static short getClassId() {
        return (short) DISTRIBUTABLE_CLASS_IDS.PlayingFieldLayout.getValue();
        
    }

    public short getWidth() {
        return Width;
    }

    public void setWidth(short width) {
        Width = width;
    }

    public short getHeight() {
        return Height;
    }

    public void setHeight(short height) {
        Height = height;
    }

    public ArrayList<FieldLocation> getSidewalkSquares() {
        return SidewalkSquares;
    }

    public void setSidewalkSquares(ArrayList<FieldLocation> sidewalkSquares) {
        SidewalkSquares = sidewalkSquares;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 2 // Width
                + 2 // Height
                + 2;           // SidewalkSquare list
        return MinimumEncodingLength;
    }

    public static void setMinimumEncodingLength(int minimumEncodingLength) {
        MinimumEncodingLength = minimumEncodingLength;
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.PlayingFieldLayout.getValue());                             // Write out the class type

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length

        bytes.AddObjects(getWidth(), getHeight());
		               // Write out Width and Height

        short SidewalkCount = (SidewalkSquares == null) ? (short) 0 : (short) (SidewalkSquares.size());
        bytes.Add(SidewalkCount);
        if (SidewalkSquares != null) {
            for (FieldLocation loc : SidewalkSquares) {
                bytes.Add(loc);
            }
        }

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);
       						// Write out the length of this object        

    }

    @Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.PlayingFieldLayout.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            setWidth(bytes.GetInt16());
            setHeight(bytes.GetInt16());

            SidewalkSquares = new ArrayList<>();
            int SidewalkCount = bytes.GetInt16();
            for (int i = 0; i < SidewalkCount; i++) {
                SidewalkSquares.add((FieldLocation) bytes.GetDistributableObject());
            }

            bytes.RestorePreviosReadLimit();
        }

    }

}
