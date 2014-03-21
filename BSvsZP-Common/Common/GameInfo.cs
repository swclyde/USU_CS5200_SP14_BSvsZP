using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Common
{
    [DataContract]
    public class GameInfo : ComponentInfo
    {
        #region Public Properties and Other Stuff
        public enum GameStatus { NOT_INITIAlIZED = 0, AVAILABLE = 1, RUNNING = 2, COMPLETED = 3, DEAD = 4 };

        [DataMember]
        public string Label { get; set; }
        [DataMember]
        public GameStatus Status { get; set; }
        [DataMember]
        public DateTime AliveTimestamp { get; set; }

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
