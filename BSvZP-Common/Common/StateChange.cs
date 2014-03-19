using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common
{
    public delegate void StateChangeHandler(StateChange change);

    public class StateChange
    {
        public enum ChangeType { NONE, ADDITION, UPDATE, MOVE, DELETION };
        public ChangeType Type { get; set; }
        public object Subject { get; set; }
    }
}
