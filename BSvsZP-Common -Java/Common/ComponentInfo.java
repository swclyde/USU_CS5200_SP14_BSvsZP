package Common;

import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;
import Common.StateChange.StateChangeHandler;

public class ComponentInfo extends DistributableObject {

    private static short ClassId = (short) DISTRIBUTABLE_CLASS_IDS.ComponentInfo.getValue();
    private short Id;
    private EndPoint CommunicationEndPoint;
    private static int MinimumEncodingLength;
    public StateChangeHandler handler;

    public ComponentInfo() {
    }

    public ComponentInfo(short id) {
        Id = id;
    }

    public ComponentInfo(short id, EndPoint endPoint) {
        this(id);
        setCommmunicationEndPoint(endPoint);
        printAll();
    }

    private void printAll() {
        getClassId();
        getMinimumEncodingLength();

    }

    /*// new
     public static ComponentInfo Create(ByteList bytes) throws ApplicationException, Exception
     {
     ComponentInfo result = new ComponentInfo();
     result.Decode(bytes);
     return result;
     }
     */
    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.ComponentInfo.getValue());                             // Write out the class type
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        bytes.AddObjects(getId(), getCommunicationEndPoint()); //bytes.AddObjects( (byte) AgentType.getValue(), getId());
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
    }

    @Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.ComponentInfo.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();
            bytes.update();
            bytes.SetNewReadLimit(objLength);
            bytes.update();

            setId((bytes.GetInt16()));
            bytes.update();
            setCommmunicationEndPoint(((EndPoint) bytes.GetDistributableObject()));
            bytes.update();

            RaiseChangedEvent();

            bytes.RestorePreviosReadLimit();
            bytes.update();
        }
    }

    public short getId() {
        return Id;
    }

    public void setId(short id) {
        Id = id;
        RaiseChangedEvent();
        System.out.println("ComponentInfo.Id " + Id);
    }

    public EndPoint getCommunicationEndPoint() {
        return CommunicationEndPoint;
    }

    public void setCommmunicationEndPoint(EndPoint commmunicationEndPoint) {
        CommunicationEndPoint = commmunicationEndPoint;
        RaiseChangedEvent();
    }

    public static int getMinimumEncodingLength() {

        MinimumEncodingLength = 4 // Object header
                + 2 // Id
                + 1;   					// CommunicationEndPoint
                				/*	+ 1          		  	// Agent Types
         + 1 					//EndPoint.MinimumEncodingLength
         + 1; 					//Tick.MinimumEncodingLength;
         */ System.out.println("ComponentInfo.MinimumEncodingLength =" + MinimumEncodingLength);
        return MinimumEncodingLength;
    }

    public short getClassId() {
        ClassId = (short) DISTRIBUTABLE_CLASS_IDS.ComponentInfo.getValue();
        System.out.println("ComponentInfo.ClassId " + ClassId);
        return ClassId;
    }

    protected void RaiseChangedEvent() {
        if (handler != null) {
            handler.addStateChangeHandler();
        }
    }
}
