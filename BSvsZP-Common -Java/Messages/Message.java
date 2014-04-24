package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.MessageNumber;

public abstract class Message implements Comparable {

    private MessageNumber MessageNr;
    private MessageNumber ConversationId;
    private static int MinimumEncodingLength;

    public abstract MESSAGE_CLASS_IDS MessageTypeId();

    public enum MESSAGE_CLASS_IDS {

        Message(1),
        MessageNumber(2),
        Request(100),
        JoinGame(102),
        AddComponent(103),
        RemoveComponent(104),
        StartGame(105),
        EndGame(106),
        GetResource(107),
        TickDelivery(108),
        ValidateTick(109),
        Move(110),
        ThrowBomb(111),
        Eat(112),
        ChangeStrength(113),
        Collaborate(114),
        GetStatus(115),
        ExitGame(118),
        StartUpdateStream(119),
        Reply(200),
        AckNak(201),
        ReadyReply(205),
        ResourceReply(210),
        ConfigurationReply(215),
        PlayingFieldReply(220),
        AgentListReply(225),
        StatusReply(230),
        EndUpdateStream(231),
        MaxMessageClassId(299);

        private int value;

        private MESSAGE_CLASS_IDS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static short getStringValueFromInt(int i) {
            for (MESSAGE_CLASS_IDS status : MESSAGE_CLASS_IDS.values()) {
                if (status.value == i) {
                    return (short) status.value;
                }
            }
            // throw an IllegalArgumentException or return null
            throw new IllegalArgumentException("the given number doesn't match any Status.");
        }

        public static MESSAGE_CLASS_IDS fromShort(short i) {
            MESSAGE_CLASS_IDS temp = null;
            for (MESSAGE_CLASS_IDS status : MESSAGE_CLASS_IDS.values()) {
                if (status.value == i) {
                    temp = status;
                }
            }
            return temp;
        }

    }

    protected Message() {
        setMessageNr(MessageNumber.Create());
        setConversationId(MessageNr);
    }

    public static Message Create(ByteList bytes) throws ApplicationException, Exception {
        Message result = null;

        if (bytes == null || bytes.getRemainingToRead() < Message.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }

        short msgType = bytes.PeekInt16();
        if ((msgType > (short) MESSAGE_CLASS_IDS.Request.getValue()) && (msgType <= (short) MESSAGE_CLASS_IDS.Reply.getValue())) {
            result = Request.Create(bytes);
        } else if ((msgType > (short) MESSAGE_CLASS_IDS.Reply.getValue()) && msgType < (short) (MESSAGE_CLASS_IDS.MaxMessageClassId.getValue())) {
            result = Reply.Create(bytes);
        } else {
            throw new ApplicationException("Invalid Message Type", null);
        }

        return result;
    }

    public void Encode(ByteList bytes) throws UnknownHostException, NotActiveException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.Message.getValue()); // Write out the class type
        bytes.update();

        short lengthPos = bytes.getCurrentWritePosition(); // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0); 			// Write out a place holder for the length
        bytes.update();

        bytes.AddObjects(getMessageNr(), getConversationId());
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length); // Write out the length of this object
    }

    public void Decode(ByteList bytes) throws Exception {
        short objType = bytes.GetInt16();
        bytes.update();
        short objLength = bytes.GetInt16();
        bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();
        MessageNr = ((MessageNumber) bytes.GetDistributableObject());
        bytes.update();
        ConversationId = ((MessageNumber) bytes.GetDistributableObject());
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public static int Compare(Message a, Message b) {
        int result = 0;

        if ((a != b)) {
            if (((Object) a == null) && ((Object) b != null)) {
                result = -1;
            } else if (((Object) a != null) && ((Object) b == null)) {
                result = 1;
            } else if (operatorLessThan(a, b)) {
                result = -1;
            } else if (operatorGreaterThan(a, b)) {
                result = 1;
            }
        }
        return result;
    }

    public static boolean operatorEqual(Message a, Message b) {
        return (Compare(a, b) == 0);
    }

    public static boolean operatorNotEqual(Message a, Message b) {
        return (Compare(a, b) != 0);
    }

    public static boolean operatorLessThan(Message a, Message b) {
        return (Compare(a, b) < 0);
    }

    public static boolean operatorGreaterThan(Message a, Message b) {
        return (Compare(a, b) > 0);
    }

    public static boolean operatorLessThanOrEqual(Message a, Message b) {
        return (Compare(a, b) <= 0);
    }

    public static boolean operatorGreaterThanOrEqual(Message a, Message b) {
        return (Compare(a, b) >= 0);
    }

    public int CompareTo(Object obj) {
        return Compare(this, (Message) obj);
    }

    public boolean Equals(Object obj) {
        boolean flag = false;
        if (Compare(this, (Message) obj) == 0) {
            flag = true;
        }
        return flag;
    }

    public int GetHashCode() {
        return super.hashCode();
    }

    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.Message.getValue();
    }

    public MessageNumber getMessageNr() {
        return MessageNr;
    }

    public void setMessageNr(MessageNumber messageNr) {
        MessageNr = messageNr;
    }

    public MessageNumber getConversationId() {
        return ConversationId;
    }

    public void setConversationId(MessageNumber conversationId) {
        ConversationId = conversationId;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 1
                + 1;
        return MinimumEncodingLength;
    }
}
