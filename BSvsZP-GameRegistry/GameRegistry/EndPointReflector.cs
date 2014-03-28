using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;

namespace GameRegistry
{
    public class EndPointReflector : IDisposable
    {
        #region Private Data Members
        private static EndPointReflector instance;
        private static object myLock = new object();
        private UdpClient udpClient;
        private bool keepGoing = false;
        #endregion

        #region Constructors, Destructors, and Instance
        protected EndPointReflector()
        {
            keepGoing = true;
            udpClient = new UdpClient(0, AddressFamily.InterNetwork);
            
            udpClient.BeginReceive(ReceiveCallback, null);
        }

        public void Dispose()
        {
            keepGoing = false;
            if (udpClient != null)
                udpClient.Close();
        }

        public static EndPointReflector Instance
        {
            get
            {
                lock (myLock)
                {
                    if (instance == null)
                        instance = new EndPointReflector();
                    return instance;
                }
            }
        }
        #endregion

        #region Public Methods and Properties
        public IPEndPoint EndPoint { get { return udpClient.Client.LocalEndPoint as IPEndPoint; } }
        #endregion

        #region Private methods
        private void ReceiveCallback(IAsyncResult asyncResult)
        {
            IPEndPoint ep = new IPEndPoint(IPAddress.Any, 0);
            byte[] receiveBytes = null;
            lock (myLock)
            {
                try
                {
                    receiveBytes = udpClient.EndReceive(asyncResult, ref ep);
                }
                catch { }
            }
            if (receiveBytes != null)
            {
                string remoteEP = ep.ToString();
                byte[] sendBuffer = ASCIIEncoding.ASCII.GetBytes(remoteEP);
                udpClient.Send(sendBuffer, sendBuffer.Length, ep);
            }

            // Start another receive
            if (keepGoing)
                udpClient.BeginReceive(ReceiveCallback, null); 
        }
        #endregion

    }
}