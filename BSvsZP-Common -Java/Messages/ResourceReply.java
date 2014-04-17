package Messages;

import java.io.IOException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.DistributableObject;

public class ResourceReply extends Reply {

    public DistributableObject Resource;
    private static int MinimumEncodingLength;

    protected ResourceReply() {
    }

    public ResourceReply(PossibleStatus status, DistributableObject resource, String... note) {
        super(Reply.PossibleTypes.ResourceReply, status, (note.length == 1 ? note[0] : null));
        Resource = resource;
    }

    public static ResourceReply Create(ByteList messageBytes) throws Exception {
        ResourceReply result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }
        if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.ResourceReply.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new ResourceReply();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws ApplicationException, IOException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.ResourceReply.getValue());                           // Write out this class id first

        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);                             // Encode stuff from base class
        bytes.update();
        bytes.Add(Resource);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        bytes.update();
    }

    @Override
    public void Decode(ByteList bytes) throws ApplicationException, IOException, Exception {
        short objType = bytes.GetInt16();
        short objLength = bytes.GetInt16();

        bytes.SetNewReadLimit(objLength);
        bytes.update();
        super.Decode(bytes);
        bytes.update();
        Resource = bytes.GetDistributableObject();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public DistributableObject getResource() {
        return Resource;
    }

    public void setResource(DistributableObject resource) {
        Resource = resource;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.ResourceReply.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 1;              // Distributable object;
        return MinimumEncodingLength;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.ResourceReply;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }
}
