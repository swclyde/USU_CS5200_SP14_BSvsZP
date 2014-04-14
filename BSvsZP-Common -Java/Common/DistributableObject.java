package Common;

import java.net.UnknownHostException;
import org.omg.CORBA.portable.ApplicationException;

public class DistributableObject {

    public enum DISTRIBUTABLE_CLASS_IDS {

        MessageNumber(1000),
        GameConfiguration(1001),
        EndPoint(1002),
        PlayingFieldLayout(1004),
        FieldLocation(1006),
        AgentInfo(1008),
        ComponentInfo(1010),
        GameInfo(1011),
        AgentList(1012),
        StatusInfo(1014),
        Tick(1020),
        Excuse(1022),
        WhiningTwine(1024),
        Bomb(1026);

        private int value;

        private DISTRIBUTABLE_CLASS_IDS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static short getStringValueFromInt(int i) {

            for (DISTRIBUTABLE_CLASS_IDS status : DISTRIBUTABLE_CLASS_IDS.values()) {
                if (status.getValue() == i) {
                    return (short) status.getValue();
                }

            }
            throw new IllegalArgumentException("the given number doesn't match any Status.");
        }

        public static DISTRIBUTABLE_CLASS_IDS getByValue(int i) {
            DISTRIBUTABLE_CLASS_IDS temp = null;
            for (DISTRIBUTABLE_CLASS_IDS dt : DISTRIBUTABLE_CLASS_IDS.values()) {
                if (dt.value == i) {
                    temp = dt;
                }
            }
            return temp;
        }

        public static DISTRIBUTABLE_CLASS_IDS fromByte(short b) {
            DISTRIBUTABLE_CLASS_IDS temp = null;
            for (DISTRIBUTABLE_CLASS_IDS t : DISTRIBUTABLE_CLASS_IDS.values()) {
                if (t.value == b) {
                    temp = t;
                }
            }
            return temp;
        }
    };

    public static DistributableObject Create(ByteList bytes) throws Exception {
        DistributableObject result = null;

        if (bytes == null || bytes.getRemainingToRead() < 4) {
            throw new ApplicationException("Invalid byte array", null);
        }

        DISTRIBUTABLE_CLASS_IDS objType = DISTRIBUTABLE_CLASS_IDS.fromByte(bytes.PeekInt16());

        switch (objType) {
            case MessageNumber:
                result = MessageNumber.Create(bytes);
                break;
            case Bomb:
                result = Bomb.Create(bytes);
                break;
            case AgentInfo:
                result = AgentInfo.Create(bytes);
                break;
            case AgentList:
                result = AgentList.Create(bytes);
                break;
            case EndPoint:
                result = EndPoint.Create(bytes);
                break;
            case Excuse:
                result = Excuse.Create(bytes);
                break;
            case FieldLocation:
                result = FieldLocation.Create(bytes);
                break;
            case GameConfiguration:
                result = GameConfiguration.Create(bytes);
                break;
            case PlayingFieldLayout:
                result = PlayingFieldLayout.Create(bytes);
                break;
            case Tick:
                result = Tick.Create(bytes);
                break;
            case WhiningTwine:
                result = WhiningTwine.Create(bytes);
                break;
            default:
                throw new ApplicationException("Invalid Class IDs", null);

        }
        return result;
    }

    public void Encode(ByteList bytes) throws UnknownHostException, Exception {
    }

    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
    }
}
