﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Common
{
    public class Tick : DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.Tick; } }

        protected Int16 forAgentId;
        protected Int32 logicalClock;
        protected Int64 hashCode;
        #endregion

        #region Constructors
        public Tick()
        {
            this.logicalClock = GetNextClockTime();
        }

        public Tick(Int16 forAgentId)
        {
            this.forAgentId = forAgentId;
            this.logicalClock = GetNextClockTime();
            hashCode = ComputeHashCode();
        }

        public Tick(Int16 forAgentId, Int32 logicalClock, Int64 hashCode)
        {
            this.forAgentId = forAgentId;
            this.logicalClock = logicalClock;
            this.hashCode = hashCode;
        }

        public static int MinimumEncodingLength
        {
            get
            {
                return 4              // Object header
                       + 2            // For Agent Id
                       + 4            // Logical Clock
                       + 8;           // Hash Code
            }
        }
        
        /// <summary>
        /// Factor method to create a FieldLocation from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static Tick Create(ByteList bytes)
        {
            Tick result = new Tick();
            result.Decode(bytes);
            return result;
        }

        #endregion

        #region Public Properties
        public Int16 ForAgentId
        {
            get { return forAgentId; }
            set
            {
                forAgentId = value;
                hashCode = ComputeHashCode();
            }
        }

        public Int32 LogicalClock
        {
            get { return logicalClock; }
            set
            {
                logicalClock = value;
                hashCode = ComputeHashCode();
            }
        }
        public Int64 HashCode { get { return hashCode; } }
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

            bytes.AddObjects(ForAgentId, LogicalClock, HashCode);       // Write out Address and Port

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

                forAgentId = bytes.GetInt16();
                logicalClock = bytes.GetInt32();
                hashCode = bytes.GetInt64();

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion

        #region Other Public Properties and Methods

        public bool IsValid
        {
            get { return (ForAgentId!=0 && HashCode == ComputeHashCode()); }
        }
        #endregion

        #region Protected Methods
        protected virtual Int64 ComputeHashCode() { return 0; }
        protected virtual Int32 GetNextClockTime() { return 0; }
        #endregion

    }
}
