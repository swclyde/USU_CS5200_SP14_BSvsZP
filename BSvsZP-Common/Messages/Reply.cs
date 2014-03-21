using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace Messages
{
    abstract public class Reply : Message
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.Reply; } }
        #endregion

        #region Public Properties
        public PossibleTypes ReplyType { get; set; }
        public PossibleStatus Status { get; set; }
        public string Note {get; set;}
        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 1              // ReplyType
                       + 1              // Status
                       + 2;             // Note
            }
        }

        public enum PossibleTypes
        {
            AckNak = 1,
            ReadyReply = 2,
            ResourceReply = 3,
            ConfigurationReply = 4,
            PlayingFieldReply = 5,
            AgentListReply = 6,
            StatusReply = 7
        }

        public enum PossibleStatus
        {
            Success = 1,
            Failure = 2
        }

        #endregion

        #region Constructors and Factory Methods
        /// <summary>
        /// Default message constructor, used by Factory methods (i.e., the Create methods)
        /// </summary>
        protected Reply() { }

        /// <summary>
        /// Constructor used by all specializations, which in turn are used by
        /// senders of a message 
        /// </summary>
        /// <param name="type">Type of request that being created</param>
        /// <param name="status">Status of the ack/nak</status>
        /// <param name="note">error message or note</note>
        protected Reply(PossibleTypes type, PossibleStatus status, string note)
        {
            ReplyType = type;
            Status = status;
            Note = note;
        }

        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="messageBytes">A byte list from which the message will be decoded</param>
        /// <returns>A new message of the right specialization</returns>
        new public static Reply Create(ByteList messageBytes)
        {
            Reply result = null;

            if (messageBytes == null || messageBytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");

            Int16 msgType = messageBytes.PeekInt16();
            switch (msgType)
            {
                case (Int16) MESSAGE_CLASS_IDS.AckNak:
                    result = AckNak.Create(messageBytes);
                    break;
                case (Int16) MESSAGE_CLASS_IDS.ReadyReply:
                    break;
                case (Int16) MESSAGE_CLASS_IDS.ResourceReply:
                    break;
                case (Int16) MESSAGE_CLASS_IDS.ConfigurationReply:
                    break;
                case (Int16) MESSAGE_CLASS_IDS.PlayingFieldReply:
                    break;
                case (Int16) MESSAGE_CLASS_IDS.AgentListReply:
                    break;
                case (Int16) MESSAGE_CLASS_IDS.StatusReply:
                    break;
                default:
                    throw new ApplicationException("Invalid Message Class Id");
            }

            return result;
        }
        #endregion

        #region Encoding and Decoding methods

        /// <summary>
        /// This method encodes
        /// </summary>
        /// <param name="bytes"></param>
        public override void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                              // Write out this class id first

            Int16 lengthPos = bytes.CurrentWritePosition;    // Get the current write position, so we
                                                                    // can write the length here later
            bytes.Add((Int16)0);                             // Write out a place holder for the length

            base.Encode(bytes);                              // Encode stuff from base class

            bytes.Add(Convert.ToByte(ReplyType));            // Write out a place holder for the length

            bytes.Add(Convert.ToByte(Status));               // Write out a place holder for the length

            if (Note == null) Note = string.Empty;
            bytes.Add(Note);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes a message from a byte list
        /// </summary>
        /// <param name="bytes"></param>
        public override void Decode(ByteList bytes)
        {
            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            base.Decode(bytes);

            ReplyType = (PossibleTypes)Convert.ToInt32(bytes.GetByte());
            Status = (PossibleStatus)Convert.ToInt32(bytes.GetByte());
            Note = bytes.GetString();

            bytes.RestorePreviosReadLimit();
        }

        #endregion

    }
}
