package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.PlayingFieldLayout;

public class PlayingFieldReply extends Reply {

    public PlayingFieldLayout Layout;
    private static int MinimumEncodingLength;

    protected PlayingFieldReply() {
    }

    public PlayingFieldReply(PossibleStatus status, PlayingFieldLayout layout, String... note) {
        super(Reply.PossibleTypes.PlayingFieldReply, status, (note.length == 1 ? note[0] : null));
        Layout = layout;
    }

    public static PlayingFieldReply Create(ByteList messageBytes) throws Exception {
        PlayingFieldReply result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }
        if (messageBytes.PeekInt16() != MESSAGE_CLASS_IDS.PlayingFieldReply.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new PlayingFieldReply();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.PlayingFieldReply.getValue());                           // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                             // Encode stuff from base class
        bytes.update();
        bytes.Add(Layout);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        

    }

    @Override
    public void Decode(ByteList bytes) throws Exception {
        short objType = bytes.GetInt16();
        bytes.update();
        short objLength = bytes.GetInt16();
        bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();

        super.Decode(bytes);
        bytes.update();
        Layout = (PlayingFieldLayout) bytes.GetDistributableObject();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public PlayingFieldLayout getLayout() {
        return Layout;
    }

    public void setLayout(PlayingFieldLayout layout) {
        Layout = layout;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.PlayingFieldReply.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + PlayingFieldLayout.getMinimumEncodingLength();
        return MinimumEncodingLength;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.PlayingFieldReply;
    }

}
