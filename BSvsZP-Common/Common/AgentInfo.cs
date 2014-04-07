using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;

namespace Common
{
    public class AgentInfo : ComponentInfo
    {
        #region Private Data Members
        // Define this, the Message class, identifier
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.AgentInfo; } }

        private PossibleAgentType agentType;
        private PossibleAgentStatus agentStatus;
        private string aNumber;
        private string firstName;
        private string lastName;
        private Double strength;
        private Double speed;
        private Double points;
        private FieldLocation location;

        #endregion

        #region Public Properties and Other Stuff
        public enum PossibleAgentType { Other = 0, BrilliantStudent = 1, ExcuseGenerator = 2, WhiningSpinner = 3, ZombieProfessor = 4, Referee = 5 };
        public enum PossibleAgentStatus { NotInGame = 0, InGame = 1, WonGame = 2, LostGame = 3 };
        public PossibleAgentType AgentType
        {
            get { return agentType; }
            set
            {
                agentType = value;
                RaiseChangedEvent();
            }
        }
        public PossibleAgentStatus AgentStatus
        {
            get { return agentStatus; }
            set
            {
                agentStatus = value;
                RaiseChangedEvent();
            }
        }
        public string ANumber
        {
            get { return aNumber; }
            set
            {
                aNumber = value;
                RaiseChangedEvent();
            }
        }
        public string FirstName
        {
            get { return firstName; }
            set
            {
                firstName = value;
                RaiseChangedEvent();
            }
        }
        public string LastName
        {
            get { return lastName; }
            set
            {
                lastName = value;
                RaiseChangedEvent();
            }
        }
        public Double Strength
        {
            get { return strength; }
            set
            {
                strength = value;
                RaiseChangedEvent();
            }
        }
        public Double Speed
        {
            get { return speed; }
            set
            {
                speed = value;
                RaiseChangedEvent();
            }
        }
        public Double Points
        {
            get { return points; }
            set
            {
                points = value;
                RaiseChangedEvent();
            }
        }
        public FieldLocation Location
        {
            get { return location; }
            set
            {
                location = value;
                RaiseChangedEvent();
            }
        }

        public static new int MinimumEncodingLength
        {
            get
            {
                return 4                // Object header
                       + 1              // Agent Types
                       + 1              // Agent Status
                       + 2              // ANumber
                       + 2              // FirstName
                       + 2              // LastName
                       + 8              // Strength
                       + 8              // Speed
                       + 1;             // Location
            }
        }
        #endregion
      
        #region Constructors
        public AgentInfo() {}

        public AgentInfo(Int16 id, PossibleAgentType type) : base(id)
        {
            AgentType = type;
        }

        public AgentInfo(Int16 id, PossibleAgentType type, EndPoint ep) : base(id, ep)
        {
            AgentType = type;
        }

        /// <summary>
        /// Factor method to create an object of this class from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static AgentInfo Create(ByteList bytes)
        {
            AgentInfo result = new AgentInfo();
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

            base.Encode(bytes);

            if (ANumber == null)
                ANumber = string.Empty;
            if (FirstName == null)
                FirstName = string.Empty;
            if (LastName == null)
                LastName = string.Empty;

            bytes.AddObjects(   (byte) AgentType,
                                (byte) AgentStatus,
                                ANumber,
                                FirstName,
                                LastName,
                                Strength,
                                Speed,
                                Points,
                                Location);

            Int16 length = Convert.ToInt16(bytes.CurrentWritePosition - lengthPos - 2);
            bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object        
        }

        /// <summary>
        /// This method decodes a message from a byte list.  It can onlt be called from within the class hierarchy.
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

                base.Decode(bytes);

                agentType = (PossibleAgentType)bytes.GetByte();
                agentStatus = (PossibleAgentStatus)bytes.GetByte();
                aNumber = bytes.GetString();
                firstName = bytes.GetString();
                lastName = bytes.GetString();
                strength = bytes.GetDouble();
                speed = bytes.GetDouble();
                points = bytes.GetDouble();
                location = bytes.GetDistributableObject() as FieldLocation;

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion
    }
}
