using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Common
{
    public class Excuse : DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16) DISTRIBUTABLE_CLASS_IDS.Excuse; } }

        #endregion

        #region Public Properties
        public Int16 CreatorId { get; set; }
        public List<Tick> Ticks { get; set; }
        public Tick RequestTick { get; set; }
        public static int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 2              // Id
                       + 2              // List of ticks
                       + 1;             // Request Tick
            }
        }
        #endregion

        #region Constructors
        public Excuse()
        {
            Ticks = new List<Tick>();
        }

        
        public Excuse(Int16 creatorId, List<Tick> ticks, Tick requestTick)
        {
            CreatorId = creatorId;
            Ticks = ticks;
            RequestTick = requestTick;
        }

        /// <summary>
        /// Factor method to create a Excuse from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static Excuse Create(ByteList bytes)
        {
            Excuse result = new Excuse();
            result.Decode(bytes);
            return result;
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

            bytes.Add(CreatorId);                           // Write out Creator's Id

            if (Ticks == null) Ticks = new List<Tick>();
            bytes.Add(Convert.ToInt16(Ticks.Count));
            foreach (Tick tick in Ticks)
                bytes.Add(tick);

            bytes.Add(RequestTick);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes of this classes from a byte list.  It can onlt be called from within the class hierarchy.
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

                CreatorId = bytes.GetInt16();
                Ticks = new List<Tick>();
                int count = bytes.GetInt16();
                for (int i = 0; i < count; i++)
                    Ticks.Add(bytes.GetDistributableObject() as Tick);

                RequestTick = bytes.GetDistributableObject() as Tick;

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion
    }
}
