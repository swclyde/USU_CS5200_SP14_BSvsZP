package Common;

import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

public class Tick extends DistributableObject
{
    private int LogicalClock;
    private long HashCode;
    private static int MinimumEncodingLength;
    private short ForAgentId;

    public short getForAgentId() {
		return ForAgentId;
	}

	public void setForAgentId(short forAgentId) {
		this.ForAgentId = forAgentId;
        HashCode = ComputeHashCode();
	}

	public int getLogicalClock() {
        return this.LogicalClock;
    }

    public void setLogicalClock(int value) {
        this.LogicalClock = value;
        HashCode = ComputeHashCode();
    }

    public static short getClassId() {
        return (short) DISTRIBUTABLE_CLASS_IDS.Tick.getValue();
    }

    public long getHashCode() {
        return HashCode;
    }

    public void setHashCode(long value) {
        HashCode = value;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4		 // Object header
                				+ 2      // forAgentId 
                				+ 4 	 // Logical Clock
                				+ 8;     // Hash Code
        return MinimumEncodingLength;
    }

    public Tick() {}
    
    public Tick(short forAgentId)
    {
    	this.ForAgentId = forAgentId;
        this.LogicalClock = GetNextClockTime();   
        HashCode = ComputeHashCode();
    }
   

    public Tick(short forAgentId, int logicalClock, long hashCode) {
    	this.ForAgentId = forAgentId;
    	this.setLogicalClock(logicalClock);
        this.setHashCode(hashCode);
    }
  
    public static Tick Create(ByteList bytes) throws Exception {
        Tick result = new Tick();
        result.Decode(bytes);
        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.Tick.getValue());                             // Write out the class type

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length

        bytes.AddObjects(this.getForAgentId(), this.getLogicalClock(), this.getHashCode());
        // Write out Address and Port

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);

        bytes.WriteInt16To(lengthPos, length);
	  // Write out the length of this object     

    }

    @Override
    protected void Decode(ByteList bytes) throws Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.Tick.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            setForAgentId(bytes.GetInt16());
            setLogicalClock(bytes.GetInt32());
            setHashCode(bytes.GetInt64());

            bytes.RestorePreviosReadLimit();
        }

    }
    private long ComputeHashCode() { return 0;   }
    protected int GetNextClockTime() { return 0; } 
        
    public boolean IsValid() {
        return (ForAgentId!=0 && getHashCode() == ComputeHashCode());
    }
}
