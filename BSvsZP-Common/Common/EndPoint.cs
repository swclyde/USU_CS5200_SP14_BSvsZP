using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Runtime.Serialization;

namespace Common
{
    public class EndPoint :  DistributableObject
    {
        #region Private Data Members and Properties
        // Define this class, identifier for serialization / deserialiation purposes
        private static Int16 ClassId { get { return (Int16)DISTRIBUTABLE_CLASS_IDS.EndPoint; } }
        private int port;
        #endregion

        #region Public Properties
        [DataMember]
        public Int32 Address { get; set; }
        [DataMember]
        public Int32 Port
        {
            get { return port; }
            set
            {
                if (value < IPEndPoint.MinPort || value > IPEndPoint.MaxPort)
                    throw new ApplicationException("Invalid Port Number");
                else
                    port = value;
            }
        }

        public static int MinimumEncodingLength
        {
            get
            {
                return 4              // Object header
                       + 4            // Address
                       + 4;           // Port
            }
        }
        #endregion

        #region Constructors
        public EndPoint() {}
        
        public EndPoint(Int32 address, Int32 port)
        {
            Address = address;
            Port = port;
        }

        public EndPoint(IPEndPoint ep)
        {
            if (ep!=null)
            {
                Address = BitConverter.ToInt32(ep.Address.GetAddressBytes(), 0);
                Port = ep.Port;
            }
        }

        public EndPoint(byte[] addressBytes, Int32 port) : this(0, port)
        {
             if (addressBytes!=null && addressBytes.Length==4)
                Address = BitConverter.ToInt32(addressBytes, 0);
        }

        public EndPoint(string hostname, Int32 port)
            : this(0, port)
        {
            if (!string.IsNullOrWhiteSpace(hostname))
                Address = ParseAddress(hostname);
        }

        private int ParseAddress(string hostname)
        {
            int result = 0;
            IPAddress[] addressList = Dns.GetHostAddresses(hostname);
            if (addressList.Length > 0)
                result = BitConverter.ToInt32(addressList[0].GetAddressBytes(), 0);
            return result;
        }

        public EndPoint(string hostnameAndPort)
        {
            if (!string.IsNullOrWhiteSpace(hostnameAndPort))
            {
                string[] tmp = hostnameAndPort.Split(':');
                if (tmp.Length == 2 && !string.IsNullOrWhiteSpace(tmp[0]))
                {
                    Address = ParseAddress(tmp[0]);
                    Int32.TryParse(tmp[1], out port);
                }
            }
        }

        /// <summary>
        /// Factor method to create a FieldLocation from a byte list
        /// </summary>
        /// <param name="bytes">A byte list from which the distributable object will be decoded</param>
        /// <returns>A new object of this class</returns>
        new public static EndPoint Create(ByteList bytes)
        {
            EndPoint result = new EndPoint();
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

            bytes.AddObjects(Address, Port);                // Write out Address and Port

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

                Address = bytes.GetInt32();
                Port = bytes.GetInt32();

                bytes.RestorePreviosReadLimit();
            }
        }

        #endregion

        #region Other Public Methods

        public IPEndPoint GetIPEndPoint()
        {
            return new IPEndPoint(Convert.ToInt64(Address), Port);
        }

        public static bool Match(EndPoint ep1, EndPoint ep2)
        {
            return (ep1.Address == ep2.Address && ep1.Port == ep2.Port);
        }

        public static bool Match(IPEndPoint ep1, IPEndPoint ep2)
        {
            return (ep1.Address.GetAddressBytes()[0] == ep2.Address.GetAddressBytes()[0] &&
                    ep1.Address.GetAddressBytes()[1] == ep2.Address.GetAddressBytes()[1] &&
                    ep1.Address.GetAddressBytes()[2] == ep2.Address.GetAddressBytes()[2] &&
                    ep1.Address.GetAddressBytes()[3] == ep2.Address.GetAddressBytes()[3] &&
                    ep1.Port == ep2.Port);
        }

        public override bool Equals(object obj)
        {
            return (obj != null && obj.GetType() == typeof(EndPoint) && Match(this, (EndPoint)obj));
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            byte[] addressBytes = BitConverter.GetBytes(Address);
            return string.Format("{0}.{1}.{2}.{3}:{4}", addressBytes[0], addressBytes[1], addressBytes[2], addressBytes[3], Port);
        }

        #endregion

    }

}
