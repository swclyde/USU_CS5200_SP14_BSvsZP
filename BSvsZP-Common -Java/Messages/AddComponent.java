package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Common.ComponentInfo;

public class AddComponent extends Request
{
    public ComponentInfo Component;
    private static int MinimumEncodingLength;

    public AddComponent() {
        super(PossibleTypes.AddComponent);
    }

    public AddComponent(ComponentInfo component) {
        super(PossibleTypes.AddComponent);
        Component = component;
    }

    public static AddComponent Create(ByteList messageBytes) throws Exception {
        AddComponent result = null;

        if (messageBytes == null || messageBytes.getRemainingToRead() < AddComponent.getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        } else if (messageBytes.PeekInt16() != (short) MESSAGE_CLASS_IDS.AddComponent.getValue()) {
            throw new ApplicationException("Invalid message class id", null);
        } else {
            result = new AddComponent();
            result.Decode(messageBytes);
        }

        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, NotActiveException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.AddComponent.getValue());                              // Write out this class id first
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        bytes.update();                                                   // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();

        super.Encode(bytes);                        // Encode the part of the object defined by the base class

        bytes.Add(Component);
        bytes.update();
        Integer lenghtinbytes = (bytes.getCurrentWritePosition() - lengthPos - 2);
        short length = lenghtinbytes.shortValue();
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object
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

        Component = (ComponentInfo) bytes.GetDistributableObject();
        bytes.update();
        bytes.RestorePreviosReadLimit();
        bytes.update();
    }

    public ComponentInfo getComponent() {
        return Component;
    }

    public void setComponent(ComponentInfo component) {
        Component = component;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 1;             // Component
        return MinimumEncodingLength;
    }

    @Override
    public short getClassId() {
        return (short) MESSAGE_CLASS_IDS.AddComponent.getValue();
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public MESSAGE_CLASS_IDS MessageTypeId() {
        return Message.MESSAGE_CLASS_IDS.AddComponent;
    }

}
