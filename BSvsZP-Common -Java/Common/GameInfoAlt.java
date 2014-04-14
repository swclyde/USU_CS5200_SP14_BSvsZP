package Common;

public class GameInfoAlt {

    public short Id;
    public String CommunicationEndPoint;
    public String Labe;
    public String Status;
    public String AliveTimestamp;

    public short getId() {
        return Id;
    }

    public void setId(short id) {
        Id = id;
    }

    public String getCommunicationEndPoint() {
        return CommunicationEndPoint;
    }

    public void setCommunicationEndPoint(String communicationEndPoint) {
        CommunicationEndPoint = communicationEndPoint;
    }

    public String getLabe() {
        return Labe;
    }

    public void setLabe(String labe) {
        Labe = labe;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAliveTimestamp() {
        return AliveTimestamp;
    }

    public void setAliveTimestamp(String aliveTimestamp) {
        AliveTimestamp = aliveTimestamp;
    }

}
