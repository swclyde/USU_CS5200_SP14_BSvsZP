using System;
using System.Collections.Generic;
using System.Text;
using Common;

namespace Messages
{
    abstract public class Message : IComparable
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.Message; } }
        #endregion

        #region Public Properties
        public abstract MESSAGE_CLASS_IDS MessageTypeId();

        public MessageNumber MessageNr { get; set; }
        public MessageNumber ConversationId { get; set; }
        public static int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 1
                       + 1;
            }
        }


        // This enumeration provides a list of identifiers for the classes of objects that need to be
        // encoded and decoded.  When encode a object of some class X, the encoding method includes
        // the identifier for that class in the byte stream so the decoder know what kind of object
        // to create in the decoding processes.  There's nothing magical about the numbers except that
        // requests class identifiers become before reply class identifiers.
        public enum MESSAGE_CLASS_IDS
        {
            Message = 1,
            MessageNumber = 2,
            Request = 100,
            GameAnnouncement = 101,
            JoinGame = 102,
            AddComponent = 103,
            RemoveComponent = 104,
            StartGame = 105,
            EndGame = 106,
            GetResource = 107,
            TickDelivery = 108,
            ValidateTick = 109,
            Move = 110,
            ThrowBomb = 111,
            Eat = 112,
            ChangeStrength = 113,
            Collaborate = 114,
            GetStatus = 115,
            Reply = 200,
            AckNak = 201,
            ReadyReply = 205,
            ResourceReply =  210,
            ConfigurationReply = 215,
            PlayingFieldReply = 220,
            AgentListReply = 225,
            StatusReply = 230,
            MaxMessageClassId = 299
        };
        #endregion

        #region Constructors and Factory Methods
        /// <summary>
        /// Default Constructed called by the Request and Reply constructors used by the Senders.
        /// Note how this construct creates a new message number and set the conversation Id to
        /// the message number.  This is the expected behavior for an initial messsage in a conversation.
        /// </summary>
        protected Message()
        {
            MessageNr = MessageNumber.Create();
            ConversationId = MessageNr;
        }


        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="bytes"></param>
        /// <returns>A new message of the right specialization</returns>
        public static Message Create(ByteList bytes)
        {
            Message result = null;

            if (bytes == null || bytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");

            Int16 msgType = bytes.PeekInt16();
            if (msgType > (Int16) MESSAGE_CLASS_IDS.Request && msgType <= (Int16) MESSAGE_CLASS_IDS.Reply)
                result = Request.Create(bytes);
            else if (msgType > (Int16) MESSAGE_CLASS_IDS.Reply && msgType < (Int16) MESSAGE_CLASS_IDS.MaxMessageClassId)
                result = Reply.Create(bytes);
            else
                throw new ApplicationException("Invalid Message Type");

            return result;
        }
        #endregion

        #region Encoding and Decoding methods

        /// <summary>
        /// This method encodes
        /// </summary>
        /// <param name="bytes"></param>
        virtual public void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                            // Write out the class type

            Int16 lengthPos = bytes.CurrentWritePosition;    // Get the current write position, so we
                                                                    // can write the length here later

            bytes.Add((Int16) 0);                            // Write out a place holder for the length

            bytes.AddObjects(MessageNr, ConversationId);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes a message from a byte list
        /// </summary>
        /// <param name="bytes"></param>
        virtual public void Decode(ByteList bytes)
        {
            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);
            
            MessageNr = bytes.GetDistributableObject() as MessageNumber;
            ConversationId = bytes.GetDistributableObject() as MessageNumber;

            bytes.RestorePreviosReadLimit();
        }

        #endregion

        #region Comparison Methods and Operators
        public static int Compare(Message a, Message b)
        {
            int result = 0;

            if (!System.Object.ReferenceEquals(a, b))
            {
                if (((object)a == null) && ((object)b != null))
                    result = -1;
                else if (((object)a != null) && ((object)b == null))
                    result = 1;
                else if (a.MessageNr < b.MessageNr)
                    result = -1;
                else if (a.MessageNr > b.MessageNr)
                    result = 1;
            }
            return result;
        }

        public static bool operator ==(Message a, Message b)
        {
            return (Compare(a, b) == 0);
        }

        public static bool operator !=(Message a, Message b)
        {
            return (Compare(a, b) != 0);
        }

        public static bool operator <(Message a, Message b)
        {
            return (Compare(a, b) < 0);
        }

        public static bool operator >(Message a, Message b)
        {
            return (Compare(a, b) > 0);
        }

        public static bool operator <=(Message a, Message b)
        {
            return (Compare(a, b) <= 0);
        }

        public static bool operator >=(Message a, Message b)
        {
            return (Compare(a, b) >= 0);
        }
        #endregion

        #region IComparable Members

        public int CompareTo(object obj)
        {
            return Compare(this, obj as Message);
        }

        public override bool Equals(object obj)
        {
            return (Compare(this, obj as Message)==0);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
        #endregion
    }
}
