using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Common
{
    /// <summary>
    /// FieldLocation
    /// 
    /// Objects of this class represent locations in the playing field.  A field location may be immutable, meaning
    /// that it can't change once it is setup.
    /// </summary>
    public class FieldLocation : DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.FieldLocation; } }

        private Int16 x;
        private Int16 y;
        private bool xSet = false;
        private bool ySet = false;
        private bool immutable = false;
        #endregion

        #region Public Properties
        public bool Immutable { get { return immutable; } }
        public Int16 X
        {
            get { return x; }
            set { if (!Immutable || !xSet) x = value; xSet = true; }
        }

        public Int16 Y
        {
            get { return y; }
            set { if (!Immutable || !ySet) y = value; ySet = true; }
        }

        public static int MinimumEncodingLength
        {
            get
            {
                return 4              // Object header
                       + 2            // X
                       + 2            // Y
                       + 1;           // Immutable
            }
        }

        #endregion

        #region Constructors
        public FieldLocation(bool immutable = false) { this.immutable = immutable; }
        public FieldLocation(Int16 x, Int16 y, bool immutable = false)
        {
            X = x;
            Y = y;
            this.immutable = immutable;
        }

        /// <summary>
        /// Factor method to create a FieldLocation from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static FieldLocation Create(ByteList bytes)
        {
            FieldLocation result = new FieldLocation();
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

            bytes.AddObjects(X, Y, Immutable);              // Write out X, Y, and Immutable properties

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
            else if (Immutable)
                throw new ApplicationException("Cannot use Decode to alter an immutable FieldLocation object");
            else
            {
                Int16 objType = bytes.GetInt16();
                Int16 objLength = bytes.GetInt16();

                bytes.SetNewReadLimit(objLength);

                X = bytes.GetInt16();
                Y = bytes.GetInt16();
                immutable = bytes.GetBool();

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion
    }

    public class ImmutableFieldLocation : FieldLocation
    {
        public ImmutableFieldLocation() : base(true) { }
    }
}
