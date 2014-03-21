using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Common
{
    public class AgentList : DistributableObject, IEnumerable<AgentInfo>
    {
        #region Private Data Members
        // Define this, the Message class, identifier
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.AgentList; } }
        private List<AgentInfo> agents = new List<AgentInfo>();
        private object myLock = new object();
        #endregion

        #region Public Properties and Other Stuff
        public static int MinimumEncodingLength
        {
            get
            {
                return 4              // Object header
                       + 2;           // Components
            }
        }

        #endregion
      
        #region Constructors, Factories, and Destructors
        public AgentList() { }

        /// <summary>
        /// Factor method to create an object of this class from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static AgentList Create(ByteList bytes)
        {
            AgentList result = new AgentList();
            result.Decode(bytes);
            return result;
        }

        public void Dispose()
        {
            Clear();
        }

        #endregion

        #region Public Methods and Properties
        public int Count
        {
            get
            {
                int result = 0;
                lock (myLock) { result = agents.Count; }
                return result;
            }
        }

        public AgentInfo this[int index]
        {
            get
            {
                AgentInfo result = null;
                if (index >= 0 && index < agents.Count)
                    result = agents[index];
                return result;
            }
        }

        public void Add(AgentInfo agentInfo)
        {
            lock (myLock)
            {
                agents.Add(agentInfo);
            }
        }

        public void Remove(AgentInfo agentInfo)
        {
            if (agentInfo != null)
                Remove(agentInfo.Id);
        }

        public void Remove(Int16 agentId)
        {
            lock (myLock)
            {
                foreach (AgentInfo agent in agents)
                    if (agent.Id == agentId)
                    {
                        agents.Remove(agent);
                        break;
                    }
            }
        }

        public void Clear()
        {
            lock (myLock)
            {
                agents.Clear();
            }
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

            lock (myLock)
            {
                bytes.Add(Convert.ToInt16(agents.Count));
                foreach (AgentInfo component in agents)
                    bytes.Add(component);
            }
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

                lock (myLock)
                {
                    Clear();
                    Int16 count = bytes.GetInt16();
                    for (int i = 0; i < count; i++)
                        agents.Add(bytes.GetDistributableObject() as AgentInfo);
                }

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion

        #region IEmunerator Interface
        public IEnumerator<AgentInfo> GetEnumerator()
        {
            return agents.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
        #endregion


    }
}
