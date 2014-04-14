using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

using log4net;

namespace Common
{
    [DataContract]
    public class GameInfo : ComponentInfo
    {
        #region Private Data Methods
        private static readonly ILog log = LogManager.GetLogger(typeof(GameInfo));

        private string label;
        private GameStatus status;
        #endregion

        #region Public Properties and Other Stuff
        public enum GameStatus { NOT_INITIAlIZED = 0, AVAILABLE = 1, STARTING=2, RUNNING = 3, STOPPING=4, COMPLETED=5, DEAD=6 };

        [DataMember]
        public string Label
        {
            get { return label; }
            set
            {
                if (label != value)
                {
                    if (value!=null) log.DebugFormat("Change label to {0}", value);
                    label = value;
                    RaiseChangedEvent();
                }
            }
        }
        [DataMember]
        public GameStatus Status
        {
            get { return status; }
            set
            {
                if (status != value)
                {
                    log.DebugFormat("Change status to {0}", value.ToString());
                    status = value;
                    RaiseChangedEvent();
                }
            }
        }
        [DataMember]
        public DateTime AliveTimestamp { get ; set; }

        #endregion
      
        #region Constructors
        public GameInfo() { AliveTimestamp = DateTime.Now;}

        protected GameInfo(Int16 id, string label, EndPoint ep)
        {
            Id = id;
            Label = label;
            CommunicationEndPoint = ep;
            AliveTimestamp = DateTime.Now;
        }

        public GameInfo(Int16 id, string label, EndPoint ep, GameStatus status = GameStatus.NOT_INITIAlIZED) : this(id, label, ep)
        {
            Status = status;
        }

        public GameInfo(Int16 id, string label, EndPoint ep, string status) : this(id, label, ep)
        {
            Int16 tmp = 0;
            Int16.TryParse(status, out tmp);
            Status = (GameStatus) tmp;
        }
        #endregion

    }
}
