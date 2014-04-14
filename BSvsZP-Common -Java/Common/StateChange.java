package Common;

public class StateChange {

    public enum ChangeType {

        NONE, ADDITION, UPDATE, MOVE, DELETION
    };
    public ChangeType Type;
    public Object Subject;

    public ChangeType getType() {
        return Type;
    }

    public void setType(ChangeType type) {
        Type = type;
    }

    public Object getSubject() {
        return Subject;
    }

    public void setSubject(Object subject) {
        Subject = subject;
    }

    public class StateChangeHandler {

        public void addStateChangeHandler() {
            StateChange Changed = new StateChange();
            Changed.Type = StateChange.ChangeType.UPDATE;
            Changed.Subject = this;

        }
    }

}
