using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace Messages
{
    public class TickDelivery : Request
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.TickDelivery; } }
        #endregion

        #region Public Properties
        public override Message.MESSAGE_CLASS_IDS MessageTypeId() { return (Message.MESSAGE_CLASS_IDS)ClassId; }

        public Tick CurrentTick { get; set; }
        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 1;
            }
        }
        #endregion

        #region Constructors and Factories

        /// <summary>
        /// Constructor used by factory methods, which is in turn used by the receiver of a message
        /// </summary>
        public TickDelivery() : base(PossibleTypes.TickDelivery) { }

        /// <summary>
        /// Constructor used by senders of a message
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password"></param>
        public TickDelivery(Tick tick)
            : base(PossibleTypes.TickDelivery)
        {
            CurrentTick = tick;
        }

        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the message will be decoded</param>
        /// <returns>A new message of the right specialization</returns>
        new public static TickDelivery Create(ByteList bytes)
        {
            TickDelivery result = null;

            if (bytes == null || bytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");
            else if (bytes.PeekInt16() != ClassId)
                throw new ApplicationException("Invalid message class id");
            else
            {
                result = new TickDelivery();
                result.Decode(bytes);
            }

            return result;
        }

        #endregion

        #region Encoding and Decoding methods

        override public void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                              // Write out this class id first

            Int16 lengthPos = bytes.CurrentWritePosition;    // Get the current write position, so we
                                                                    // can write the length here later
            bytes.Add((Int16)0);                             // Write out a place holder for the length


            base.Encode(bytes);                              // Encode the part of the object defined
                                                                    // by the base class

            bytes.AddObject(CurrentTick);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
        }

        override public void Decode(ByteList bytes)
        {

            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            base.Decode(bytes);

            CurrentTick = bytes.GetDistributableObject() as Tick;

            bytes.RestorePreviosReadLimit();
        }

        #endregion

    }
}
