package Common;

import java.io.NotActiveException;
import org.omg.CORBA.portable.ApplicationException;

import Common.DistributableObject.DISTRIBUTABLE_CLASS_IDS;

public class AgentInfo extends ComponentInfo {

    private static short ClassId = (short) DISTRIBUTABLE_CLASS_IDS.AgentInfo.getValue();
    private String ANumber;
    private String FirstName;
    private String LastName;
    private Double Strength;
    private Double Speed;
    private Double Points;
    private FieldLocation Location;
    private PossibleAgentType AgentType;
    private PossibleAgentStatus AgentStatus;
    private static int MinimumEncodingLength;

    public PossibleAgentStatus getAgentStatus() {
        return AgentStatus;
    }

    public Double getPoints() {
        return Points;
    }

    public void setPoints(Double points) {
        Points = points;
        RaiseChangedEvent();
    }

    public void setAgentStatus(PossibleAgentStatus agentStatus) {
        AgentStatus = agentStatus;
        RaiseChangedEvent();
    }

    public String getANumber() {
        return ANumber;
    }

    public void setANumber(String aNumber) {
        ANumber = aNumber;
        RaiseChangedEvent();
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
        RaiseChangedEvent();
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
        RaiseChangedEvent();
    }

    public Double getStrength() {
        return Strength;
    }

    public void setStrength(Double strength) {
        Strength = strength;
        RaiseChangedEvent();
    }

    public Double getSpeed() {
        return Speed;
    }

    public void setSpeed(Double speed) {
        Speed = speed;
        RaiseChangedEvent();
    }

    public FieldLocation getLocation() {
        return Location;
    }

    public void setLocation(FieldLocation location) {
        Location = location;
        RaiseChangedEvent();
    }

    @Override
    public short getClassId() {
        ClassId = (short) DISTRIBUTABLE_CLASS_IDS.AgentInfo.getValue();
        return ClassId;
    }

    public enum PossibleAgentType {

        BrilliantStudent(1),
        ExcuseGenerator(2),
        WhiningSpinner(3),
        ZombieProfessor(4);

        private int value;

        PossibleAgentType(int value) {
            this.value = value;
        }

        public int getValue() {
            if (value < 0 || value > 4) {
                return 0;
            } else {
                return value;
            }
        }

        public static PossibleAgentType fromByte(short b) {
            PossibleAgentType temp = null;
            for (PossibleAgentType t : PossibleAgentType.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;  //or throw exception
        }

        public static PossibleAgentType fromInt(int b) {
            PossibleAgentType temp = null;
            for (PossibleAgentType t : PossibleAgentType.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;  //or throw exception
        }
    }

    public enum PossibleAgentStatus {

        NotInGame(0),
        TryingToJoin(1),
        InGame(2),
        WonGame(3),
        LostGame(4);

        private int value;

        PossibleAgentStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static PossibleAgentStatus fromByte(short b) {
            PossibleAgentStatus temp = null;
            for (PossibleAgentStatus t : PossibleAgentStatus.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;  //or throw exception
        }

        public static PossibleAgentStatus fromInt(int b) {
            PossibleAgentStatus temp = null;
            for (PossibleAgentStatus t : PossibleAgentStatus.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;  //or throw exception
        }

    }

    public PossibleAgentType getAgentType() {
        return AgentType;
    }

    public void setAgentType(PossibleAgentType Type) {
        AgentType = Type;
        RaiseChangedEvent();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
                + 1 // Agent Types
                + 1 // Agent Status
                + 2 // ANumber
                + 2 // FirstName
                + 2 // LastName
                + 8 // Strength
                + 8 // Speed
                //	+ 8 			 // Points
                + 1;             // Location
        return MinimumEncodingLength;
    }

    public AgentInfo() {
        setStrength(0.0);
        setSpeed(0.0);
        setPoints(0.0);
    }

    public AgentInfo(short id, PossibleAgentType type) {
        super(id);
        setStrength(0.0);
        setSpeed(0.0);
        setPoints(0.0);
        AgentType = type;
    }

    public AgentInfo(short id, PossibleAgentType type, EndPoint ep) {
        super(id, ep);
        setStrength(0.0);
        setSpeed(0.0);
        setPoints(0.0);
        AgentType = type;
    }

    //new 
    public static AgentInfo Create(ByteList bytes) throws ApplicationException, Exception {
        AgentInfo result = new AgentInfo();
        result.Decode(bytes);
        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws NotActiveException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.AgentInfo.getValue());                             // Write out the class type
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        super.Encode(bytes);

        if (getANumber() == null) {
            setANumber("");
        }
        if (getFirstName() == null) {
            setFirstName("");
        }
        if (getLastName() == null) {
            setLastName("");
        }

        /* 
         if (getAgentType() == null)
         bytes.AddObjects(AgentType);
         else 
         bytes.AddObject(PossibleAgentType.fromInt(getAgentType().getValue()));
         bytes.update();
         
         if (getAgentStatus() == null)
         bytes.AddObjects(AgentStatus);
         else
         bytes.AddObject(PossibleAgentStatus.fromInt(getAgentStatus().getValue()));
         */
        bytes.AddObject((byte) AgentType.getValue());
        bytes.update();
        bytes.AddObject((byte) AgentStatus.getValue());
        bytes.update();
        bytes.AddObjects(getANumber());
        bytes.update();
        bytes.AddObjects(getFirstName());
        bytes.update();
        bytes.AddObjects(getLastName());
        bytes.update();
        bytes.AddObjects(getStrength());
        bytes.update();
        bytes.AddObjects(getSpeed());
        bytes.update();
        bytes.AddObjects(getPoints());
        bytes.update();
        bytes.AddObjects(getLocation());
        bytes.update();

        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
    }

    @Override
    protected void Decode(ByteList bytes) throws Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.AgentInfo.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();
            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);
            bytes.update();

            super.Decode(bytes);

            bytes.update();

            AgentType = PossibleAgentType.fromByte(bytes.GetByte());
            AgentStatus = PossibleAgentStatus.fromByte(bytes.GetByte());

            ANumber = bytes.GetString();
            bytes.update();
            FirstName = bytes.GetString();
            bytes.update();
            LastName = bytes.GetString();
            bytes.update();
            Strength = bytes.GetDouble();
            bytes.update();
            Speed = bytes.GetDouble();
            bytes.update();
            Points = bytes.GetDouble();
            bytes.update();
            Location = (FieldLocation) bytes.GetDistributableObject();
            bytes.update();

            bytes.RestorePreviosReadLimit();
        }
    }

}
