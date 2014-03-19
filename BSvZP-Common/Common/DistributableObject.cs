using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Common
{
    [DataContract]
    public class DistributableObject
    {
        public enum DISTRIBUTABLE_CLASS_IDS
        {
            MessageNumber = 1000,
            GameConfiguration = 1001,
            EndPoint = 1002, 
            PlayingFieldLayout = 1004,
            FieldLocation = 1006,
            AgentInfo = 1008,
            ComponentInfo = 1010,
            GameInfo = 1011,
            AgentList = 1012,
            StatusInfo = 1014,
            Tick = 1020,
            Excuse = 1022,
            WhiningTwine = 1024,
            Bomb = 1026,
        };

        public static DistributableObject Create(ByteList bytes)
        {
            DistributableObject result = null;

            if (bytes == null || bytes.RemainingToRead < 4)
                throw new ApplicationException("Invalid byte array");

            DISTRIBUTABLE_CLASS_IDS objType = (DISTRIBUTABLE_CLASS_IDS) bytes.PeekInt16();
            switch (objType)
            {
                case DISTRIBUTABLE_CLASS_IDS.MessageNumber:
                    result = MessageNumber.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.Bomb:
                    result = Bomb.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.AgentInfo:
                    result = AgentInfo.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.AgentList:
                    result = AgentList.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.EndPoint:
                    result = EndPoint.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.Excuse:
                    result = Excuse.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.FieldLocation:
                    result = FieldLocation.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.GameConfiguration:
                    result = GameConfiguration.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.PlayingFieldLayout:
                    result = PlayingFieldLayout.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.Tick:
                    result = Tick.Create(bytes);
                    break;
                case DISTRIBUTABLE_CLASS_IDS.WhiningTwine:
                    result = WhiningTwine.Create(bytes);
                    break;
                default:
                    throw new ApplicationException(string.Format("Invalid Class Id={0}", objType));
            }
            return result;
        }

        public virtual void Encode(ByteList bytes) {}
        protected virtual void Decode(ByteList bytes) {}
    }
}
