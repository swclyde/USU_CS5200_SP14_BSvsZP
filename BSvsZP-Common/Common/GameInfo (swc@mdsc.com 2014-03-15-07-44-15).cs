using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Common
{
    [DataContract]
    public class GameInfo
    {
        #region Public Properties and Other Stuff
        public enum GameStatus { NOT_INITIAlIZED = 0, AVAILABLE = 1, RUNNING = 2, COMPLETED = 3, DEAD = 4 };

        [DataMember]
        public Int16 Id { get; set; }
        [DataMember]
        public string Label { get; set; }
        [DataMember]
        public EndPoint CommmunicationEndPoint { get; set; }
        [DataMember]
        public GameStatus Status { get; set; }
        [DataMember]
        public DateTime AliveTimestamp { get; set; }

        #endregion
      
        #region Constructors
        public GameInfo() { AliveTimestamp = DateTime.Now;}

        public GameInfo(Int16 id, string label, EndPoint ep, GameStatus status = GameStatus.NOT_INITIAlIZED)
        {
            Id = id;
            Label = label;
            CommmunicationEndPoint = ep;
            Status = status;
            AliveTimestamp = DateTime.Now;
        }

        public GameInfo(Int16 id, string label, EndPoint ep, string status)
        {
            Id = id;
            Label = label;
            CommmunicationEndPoint = ep;
            Int16 tmp = 0;
            Int16.TryParse(status, out tmp);
            Status = (GameStatus) tmp;
            AliveTimestamp = DateTime.Now;
        }
        #endregion

    }
}
