using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Common;

namespace Messages
{
    public class AckNak : Reply
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.AckNak; } }
        #endregion

        #region Public Properties
        public override Message.MESSAGE_CLASS_IDS MessageTypeId() { return (Message.MESSAGE_CLASS_IDS) ClassId; }

        public int IntResult { get; set; }
        public DistributableObject ObjResult { get; set; }
        public string Message { get; set; }
        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 2              // IntResult
                       + 1              // ObjResult
                       + 2              // Message
                       + 1;
            }
        }
        #endregion

        #region Constructors and Factory Methods
        /// <summary>
        /// Default message constructor, used by Factory methods (i.e., the Create methods)
        /// </summary>

        protected AckNak() { }

        /// <summary>
        /// Constructor used by all specializations, which in turn are used by
        /// senders of a message 
        /// </summary>
        /// <param name="conversationId">conversation id</param>
        /// <param name="status">Status of the ack/nak</status>
        /// <param name="note">error message or note</note>
        public AckNak(PossibleStatus status, int intResult, DistributableObject objResult, string message, string note) :
            base(Reply.PossibleTypes.AckNak, status, note)
        {
            IntResult = intResult;
            ObjResult = objResult;
            Message = message;
        }

        public AckNak(PossibleStatus status, int intResult) : this(status, intResult, null, string.Empty, string.Empty) { }
        public AckNak(PossibleStatus status, int intResult, string message) : this(status, intResult, null, message, string.Empty) { }
        public AckNak(PossibleStatus status, DistributableObject objResult) : this(status, 0, objResult, string.Empty, string.Empty) { }
        public AckNak(PossibleStatus status, DistributableObject objResult, string message) : this(status, 0, objResult, message, string.Empty) { }

        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="messageBytes">A byte list from which the message will be decoded</param>
        /// <returns>A new message of the right specialization</returns>
        new public static AckNak Create(ByteList messageBytes)
        {
            AckNak result = null;

            if (messageBytes==null || messageBytes.RemainingToRead<MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");
            if (messageBytes.PeekInt16() != ClassId)
                throw new ApplicationException("Invalid message class id");
            else
            {
                result = new AckNak();
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

            if (Message == null) Message = string.Empty;
            bytes.AddObjects(IntResult, ObjResult, Message);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        

        }

        override public void Decode(ByteList bytes)
        {
            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            base.Decode(bytes);

            IntResult = bytes.GetInt32();
            ObjResult = bytes.GetDistributableObject();
            Message = bytes.GetString();

            bytes.RestorePreviosReadLimit();
        }

        #endregion
    }
}
