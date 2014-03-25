using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Common
{
    [DataContract]
    public class GameInfoAlt
    {
        #region Public Properties and Other Stuff

        [DataMember]
        public Int16 Id { get; set; }
        [DataMember]
        public string CommunicationEndPoint { get; set; }
        [DataMember]
        public string Label { get; set; }
        [DataMember]
        public string Status { get; set; }
        [DataMember]
        public string AliveTimestamp { get; set; }

        #endregion
      
    }
}
