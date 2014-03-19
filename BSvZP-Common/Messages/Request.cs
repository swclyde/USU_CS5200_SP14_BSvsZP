using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Common;

namespace Messages
{
    abstract public class Request : Message
    {
        #region Private Properties
        private static Int16 ClassId { get { return (Int16)MESSAGE_CLASS_IDS.Request; } }
        #endregion

        #region Public Properties
        public PossibleTypes RequestType { get; set; }
        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 1;             // RequestType
            }
        }

        public enum PossibleTypes
        {
            GameAnnouncement = 1,
            JoinGame = 2,
            AddComponent = 3,
            RemoveComponent = 4,
            StartGame = 5,
            EndGame = 6,
            GetResource = 7,
            TickDelivery = 8,
            ValidateTick = 9,
            Move = 10,
            ThrowBomb = 11,
            Eat = 12,
            ChangeStrength = 13,
            Collaborate = 14,
            GetStatus = 15
        }

        #endregion

        #region Constructors and Factory Methods
        /// <summary>
        /// Constructor used by specializations
        /// </summary>
        /// <param name="type">Type of request that being created</param>
        protected Request(PossibleTypes type)
        {
            RequestType = type;
        }

        /// <summary>
        /// Factor method to create a message from a byte list
        /// </summary>
        /// <param name="bytes"></param>
        /// <returns>A new message of the right specialization</returns>
        new public static Request Create(ByteList bytes)
        {
            Request result = null;
            
            if (bytes == null || bytes.RemainingToRead < MinimumEncodingLength)
                throw new ApplicationException("Invalid message byte array");

            Int16 msgType = bytes.PeekInt16();
            switch (msgType)
            {
                case (Int16) MESSAGE_CLASS_IDS.GameAnnouncement:
                    result = GameAnnouncement.Create(bytes);
                    break;
                case (Int16) MESSAGE_CLASS_IDS.JoinGame:
                    result = JoinGame.Create(bytes);
                    break;
                case (Int16) MESSAGE_CLASS_IDS.AddComponent:
                    result = AddComponent.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.RemoveComponent:
                    result = RemoveComponent.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.StartGame:
                    result = StartGame.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.EndGame:
                    result = EndGame.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.GetResource:
                    result = GetResource.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.TickDelivery:
                    result = TickDelivery.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.ValidateTick:
                    result = ValidateTick.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.Move:
                    result = Move.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.ThrowBomb:
                    result = ThrowBomb.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.Eat:
                    result = Eat.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.ChangeStrength:
                    result = ChangeStrength.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.Collaborate:
                    result = Collaborate.Create(bytes);
                    break;
                case (Int16)MESSAGE_CLASS_IDS.GetStatus:
                    result = GetStatus.Create(bytes);
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
        override public void Encode(ByteList bytes)
        {
            bytes.Add(ClassId);                           // Write out this class id first

            Int16 lengthPos = bytes.CurrentWritePosition;   // Get the current write position, so we
                                                                   // can write the length here later
            bytes.Add((Int16) 0);                           // Write out a place holder for the length

            base.Encode(bytes);                             // Encode stuff from base class

            bytes.Add(Convert.ToByte(RequestType));         // Write out a place holder for the length

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes a message from a byte list
        /// </summary>
        /// <param name="bytes"></param>
        override public void Decode(ByteList bytes)
        {
            Int16 objType = bytes.GetInt16();
            Int16 objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);

            base.Decode(bytes);

            RequestType = (PossibleTypes) Convert.ToInt32(bytes.GetByte());

            bytes.RestorePreviosReadLimit();
        }
        #endregion


    }
}
