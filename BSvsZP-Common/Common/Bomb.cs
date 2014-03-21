using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Common
{
    public class Bomb : DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16) DISTRIBUTABLE_CLASS_IDS.Bomb; } }

        #endregion

        #region Public Properties
        public Int16 CreatorId { get; set; }
        public List<Excuse> Excuses { get; set; }
        public List<WhiningTwine> Twine { get; set; }
        public Tick BuiltOnTick { get; set; }
        public static int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 2              // CreatorId
                       + 2              // Excuses
                       + 2              // Twine
                       + 1;             // BuiltOnTick
            }
        }
        #endregion

        #region Constructors
        public Bomb()
        {
            Excuses = new List<Excuse>();
            Twine = new List<WhiningTwine>();
        }


        public Bomb(Int16 creatorId, List<Excuse> excuses, List<WhiningTwine> twine, Tick builtOnTick)
        {
            CreatorId = creatorId;
            Excuses = excuses;
            Twine = twine;
            BuiltOnTick = builtOnTick;
        }

        /// <summary>
        /// Factor method to create a Excuse from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static Bomb Create(ByteList bytes)
        {
            Bomb result = new Bomb();
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

            if (Excuses == null) Excuses = new List<Excuse>();
            bytes.Add(Convert.ToInt16(Excuses.Count));
            foreach (Excuse excuse in Excuses)
                bytes.Add(excuse);

            if (Twine == null) Twine = new List<WhiningTwine>();
            bytes.Add(Convert.ToInt16(Twine.Count));
            foreach (WhiningTwine twine in Twine)
                bytes.Add(twine);

            bytes.Add(BuiltOnTick);

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

                Excuses = new List<Excuse>();
                int count = bytes.GetInt16();
                for (int i = 0; i < count; i++)
                    Excuses.Add(bytes.GetDistributableObject() as Excuse);

                Twine = new List<WhiningTwine>();
                count = bytes.GetInt16();
                for (int i = 0; i < count; i++)
                    Twine.Add(bytes.GetDistributableObject() as WhiningTwine);

                BuiltOnTick = bytes.GetDistributableObject() as Tick;

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion

    }
}
