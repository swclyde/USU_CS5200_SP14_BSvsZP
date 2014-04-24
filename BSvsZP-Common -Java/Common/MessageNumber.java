package Common;

import java.net.UnknownHostException;
import java.util.Objects;

import org.omg.CORBA.portable.ApplicationException;

public class MessageNumber extends DistributableObject implements Comparable {

    private static short nextSeqNumber = 1;              // Start with message #1

    public static MessageNumber Empty;
    public static short LocalProcessId; // Local process Id -- set once when the process joins the distributed application
    public short ProcessId;
    public short SeqNumber;
    private static int MinimumEncodingLength;

    public static MessageNumber Create() {
        MessageNumber result = new MessageNumber();
        result.ProcessId = LocalProcessId;
        result.SeqNumber = GetNextSeqNumber();
        return result;
    }

    public static MessageNumber Create(ByteList bytes) throws Exception {
        MessageNumber result = null;
        if (bytes == null || bytes.getRemainingToRead() < MessageNumber.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (bytes.PeekInt16() !=(short) DistributableObject.DISTRIBUTABLE_CLASS_IDS.MessageNumber.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new MessageNumber();
            result.Decode(bytes);
        }
        return result;
    }

    public static MessageNumber Create(short processId, short seqNumber) {
        MessageNumber result = new MessageNumber();
        result.ProcessId = processId;
        result.SeqNumber = seqNumber;
        return result;
    }

    private MessageNumber() {
    }

    @Override
    public String toString() {
        return ProcessId + "." + SeqNumber;
    }

    public boolean Equals(Object obj) {
        boolean tag = false;
        int result = Compare(this, (MessageNumber) obj);

        if (result > 0) {
            tag = false;
        } else if (result < 0) {
            tag = false;
        } else if (result == 0) {
            tag = true;
        }
        return tag;
    }

    public int GetHashCode() {
        return super.hashCode();
    }

    public static int Compare(MessageNumber a, MessageNumber b) {
        int result = 0;

        if (!(a == b)) {
            if (((Object) a == null) && ((Object) b != null)) {
                result = -1;
            } else if (((Object) a != null) && ((Object) b == null)) {
                result = 1;
            } else {
                if (a.ProcessId < b.ProcessId) {
                    result = -1;
                } else if (a.ProcessId > b.ProcessId) {
                    result = 1;
                } else if (a.SeqNumber < b.SeqNumber) {
                    result = -1;
                } else if (a.SeqNumber > b.SeqNumber) {
                    result = 1;
                }
            }
        }
        return result;
    }

    public static boolean operatorEqual(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) == 0);
    }

    public static boolean operatorNotEqual(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) != 0);
    }

    public static boolean operatorLessThan(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) < 0);
    }

    public static boolean operatorGreaterThan(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) > 0);
    }

    public static boolean operatorLessThankOrEqual(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) <= 0);
    }

    public static boolean operatorGreaterThanOrEqual(MessageNumber a, MessageNumber b) {
        return (Compare(a, b) >= 0);
    }

    public int CompareTo(Object obj) {
        return Compare(this, (MessageNumber) obj);
    }

    public static MessageNumber getEmpty() {
        return new MessageNumber();
    }

    public static void setEmpty(MessageNumber empty) {
        Empty = empty;
    }

    public static short getLocalProcessId() {
        return LocalProcessId;
    }

    public static void setLocalProcessId(short localProcessId) {
        LocalProcessId = localProcessId;
    }

    public short getProcessId() {
        return ProcessId;
    }

    public void setProcessId(short processId) {
        ProcessId = processId;
    }

    public short getSeqNumber() {
        return SeqNumber;
    }

    public void setSeqNumber(short seqNumber) {
        SeqNumber = seqNumber;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 2 // ProcessId
                + 2;             // SeqNumber
        return MinimumEncodingLength;
    }

    public static short getClassId() {
        return (short) DistributableObject.DISTRIBUTABLE_CLASS_IDS.MessageNumber.getValue();
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, ApplicationException {
        bytes.Add((short) DistributableObject.DISTRIBUTABLE_CLASS_IDS.MessageNumber.getValue());                              // Write out the class type
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                            // Write out a place holder for the length
        bytes.update();
        bytes.Add(ProcessId);                            // Write out a place holder for the length
        bytes.update();
        bytes.Add(SeqNumber);
        bytes.update();

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);
		         // Write out the length of this object

    }

    @Override
    protected void Decode(ByteList bytes) throws Exception {
        short objType, objLength;

        objType = bytes.GetInt16();
        objLength = bytes.GetInt16();

        bytes.SetNewReadLimit(objLength);

        ProcessId = bytes.GetInt16();

        SeqNumber = bytes.GetInt16();

        bytes.RestorePreviosReadLimit();

    }

    private static short GetNextSeqNumber() {
        if (nextSeqNumber == Short.MAX_VALUE) {
            nextSeqNumber = 1;
        }
        return nextSeqNumber++;
    }

    @Override
    public int compareTo(Object obj) {
        return Compare(this, (MessageNumber) obj);
    }

}
