using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Common;

namespace Messages
{
    public class AgentListReply : Reply
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.AgentListReply; } }
        #endregion

        #region Public Properties
        public override Message.MESSAGE_CLASS_IDS MessageTypeId() { return (Message.MESSAGE_CLASS_IDS)ClassId; }

        public AgentList Agents { get; set; }
        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + AgentList.MinimumEncodingLength;
            }
        }
        #endregion
        
        #region Constructors and Factory Methods
        /// <summary>
        /// Default message constructor, used by Factory methods (i.e., the Create methods)
        /// </summary>

        protected AgentListReply() { }

        /// <summary>
        /// Constructor used by all specializations, which in turn are used by
        /// senders of a message 
        /// </summary>
        /// <param name="conversationId">conversation id</param>
        /// <param name="status">Status of the ack/nak</status>
        /// <param name="note">error message or note</note>
        public AgentListReply(PossibleStatus status, AgentList agents, string note = null) :
            base(Reply.PossibleTypes.AgentListReply, status, note)
        {
            Agents = agents;
        }

        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="messageBytes">A byte list from which the message will be decoded</param>
        /// <returns>A new message of the right specialization</returns>
        new public static AgentListReply Create(ByteList messageBytes)
        {
            AgentListReply result = null;

            if (messageBytes==null || messageBytes.RemainingToRead<MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");
            if (messageBytes.PeekInt16() != ClassId)
                throw new ApplicationException("Invalid message class id");
            else
            {
                result = new AgentListReply();
                result.Decode(messageBytes);
            }

            return result;
        }
        #endregion

        #region Encoding and Decoding methods

        override public void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                           // Write out this class id first

            Int16 lengthPos = bytes.CurrentWritePosition;   // Get the current write position, so we
                                                                // can write the length here later
            bytes.Add((Int16) 0);                           // Write out a place holder for the length

            base.Encode(bytes);                             // Encode stuff from base class

            bytes.Add(Agents);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        

        }

        override public void Decode(ByteList bytes)
        {
            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            base.Decode(bytes);

            Agents = bytes.GetDistributableObject() as AgentList;
            
            bytes.RestorePreviosReadLimit();
        }

        #endregion
    }
}
