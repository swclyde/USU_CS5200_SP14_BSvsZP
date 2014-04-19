package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;
import Common.ByteList;
import Common.GameConfiguration;

public class ConfigurationReply extends Reply {

    public GameConfiguration Configuration;
    private static int MinimumEncodingLength;

    protected ConfigurationReply() {
    }

    public ConfigurationReply(PossibleStatus status, GameConfiguration config, String... note) {
        super(Reply.PossibleTypes.ConfigurationReply, status, ((note.length == 1) ? note[0] : null));
        Configuration = config;
    }

   
    public static ConfigurationReply Create(ByteList messageBytes) throws Exception {
        ConfigurationReply result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }
        if (messageBytes.PeekInt16() !=(short) MESSAGE_CLASS_IDS.ConfigurationReply.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new ConfigurationReply();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, UnknownHostException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.ConfigurationReply.getValue());                           // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        bytes.update();                                              // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                             // Encode stuff from base class

        bytes.Add(Configuration);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        bytes.update();
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

        Configuration = (GameConfiguration) bytes.GetDistributableObject();
        bytes.update();

        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public GameConfiguration getConfiguration() {
        return Configuration;
    }

    public void setConfiguration(GameConfiguration configuration) {
        Configuration = configuration;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 		// Object header
        						+ GameConfiguration.getMinimumEncodingLength();
        return MinimumEncodingLength;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.ConfigurationReply.getValue();
    }

    @Override
    public Message.MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.ConfigurationReply;
    }

    @Override
    public int compareTo(Object arg0) {

        return 0;
    }
}
