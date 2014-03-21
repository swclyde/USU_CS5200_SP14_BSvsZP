using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.Serialization;

namespace Common
{
    [DataContract]
    public class ComponentInfo : DistributableObject
    {
        
        #region Private Data Members
        // Define this, the Message class, identifier
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.ComponentInfo; } }

        private Int16 id;
        private EndPoint communicationEndPoint;
        #endregion

        #region Public Properties and Other Stuff

        [DataMember]
        public Int16 Id
        {
            get { return id; }
            set
            {
                id = value;
                RaiseChangedEvent();
            }
        }
        [DataMember]
        public EndPoint CommunicationEndPoint
        {
            get { return communicationEndPoint; }
            set
            {
                communicationEndPoint = value;
                RaiseChangedEvent();
            }
        }
        
        public static int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 2              // Id
                       + 1;             // CommunicationEndPoint
            }
        }

        public event StateChangeHandler Changed;

        #endregion
      
        #region Constructors
        public ComponentInfo() {}

        public ComponentInfo(Int16 id)
        {
            Id = id;
        }

        public ComponentInfo(Int16 id, EndPoint endPoint)
            : this(id)
        {
            CommunicationEndPoint = endPoint;
        }
        #endregion

        #region Encoding and Decoding methods

        /// <summary>
        /// This method encodes an object of this class into a byte list
        /// </summary>
        /// <param name="bytes"></param>
        public override void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                             // Write out the class type

            Int16 lengthPos = bytes.CurrentWritePosition;   // Get the current write position, so we
                                                            // can write the length here later

            bytes.Add((Int16) 0);                           // Write out a place holder for the length

            bytes.AddObjects(Id, CommunicationEndPoint);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes a message from a byte list.  It can onlt be called from within the class hierarchy.
        /// </summary>
        /// <param name="messageBytes"></param>
        protected override void Decode(ByteList bytes)
        {
            if (bytes == null || bytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid byte array");
            else if (bytes.PeekInt16() != ClassId)
                throw new ApplicationException("Invalid class id");
            else
            {
                Int16 objType = bytes.GetInt16();
                Int16 objLength = bytes.GetInt16();

                bytes.SetNewReadLimit(objLength);

                id = bytes.GetInt16();
                communicationEndPoint = bytes.GetDistributableObject() as EndPoint;
                RaiseChangedEvent();

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion

        #region Event raising methods
        protected void RaiseChangedEvent()
        {
            if (Changed != null)
                Changed(new StateChange() { Type = StateChange.ChangeType.UPDATE, Subject = this });
        }
        #endregion
    }
}
