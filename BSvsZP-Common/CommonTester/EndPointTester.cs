using System;
using System.Net;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class EndPointTester
    {
        [TestMethod]
        public void EndPoint_CheckConstructors()
        {
            Common.EndPoint ep = new Common.EndPoint();
            Assert.AreEqual(0, ep.Address);
            Assert.AreEqual(0, ep.Port);

            byte[] addressBytes = new byte[4];
            addressBytes[0] = 10;
            addressBytes[1] = 211;
            addressBytes[2] = 55;
            addressBytes[3] = 20;

            ep = new Common.EndPoint(addressBytes, 2001);
            byte[] tmpBytes = BitConverter.GetBytes(ep.Address);
            Assert.AreEqual(10, tmpBytes[0]);
            Assert.AreEqual(211, tmpBytes[1]);
            Assert.AreEqual(55, tmpBytes[2]);
            Assert.AreEqual(20, tmpBytes[3]);
            Assert.AreEqual(2001, ep.Port);

            ep = new Common.EndPoint(3255420, 3004);
            Assert.AreEqual(3255420, ep.Address);
            Assert.AreEqual(3004, ep.Port);
        }

        [TestMethod]
        public void EndPoint_CheckPropertiesAndMethods()
        {
            Common.EndPoint ep1 = new Common.EndPoint(3255420, 3004);
            Assert.AreEqual(3255420, ep1.Address);
            Assert.AreEqual(3004, ep1.Port);

            ep1.Address = 54365439;
            ep1.Port = 4354;
            Assert.AreEqual(54365439, ep1.Address);
            Assert.AreEqual(4354, ep1.Port);

            ep1.Address = 0;
            ep1.Port = 0;
            Assert.AreEqual(0, ep1.Address);
            Assert.AreEqual(0, ep1.Port);

            ep1.Address = Int32.MaxValue;
            ep1.Port = IPEndPoint.MaxPort;
            Assert.AreEqual(Int32.MaxValue, ep1.Address);
            Assert.AreEqual(IPEndPoint.MaxPort, ep1.Port);
            ep1.Port = IPEndPoint.MinPort;
            Assert.AreEqual(IPEndPoint.MinPort, ep1.Port);

            try
            {
                ep1.Port = IPEndPoint.MaxPort + 1;
                Assert.Fail("Excepted exception not thrown for too big of a port number");
            }
            catch (ApplicationException) { }

            try
            {
                ep1.Port = IPEndPoint.MinPort - 1;
                Assert.Fail("Excepted exception not thrown for too small of a port number");
            }
            catch (ApplicationException) { }

            ep1.Address = 54365439;
            ep1.Port = 4354;
            Common.EndPoint ep2 = new Common.EndPoint(34445, 3255);
            Assert.IsFalse(Common.EndPoint.Match(ep1, ep2));
            Assert.IsFalse(Common.EndPoint.Match(ep1.GetIPEndPoint(), ep2.GetIPEndPoint()));
            ep2.Port = 4354;
            Assert.IsFalse(Common.EndPoint.Match(ep1, ep2));
            Assert.IsFalse(Common.EndPoint.Match(ep1.GetIPEndPoint(), ep2.GetIPEndPoint()));
            ep2.Address = 54365439;
            Assert.IsTrue(Common.EndPoint.Match(ep1, ep2));
            Assert.IsTrue(Common.EndPoint.Match(ep1.GetIPEndPoint(), ep2.GetIPEndPoint()));

        }

        [TestMethod]
        public void EndPoint_CheckEncodeAndDecode()
        {
            Common.EndPoint ep1 = new Common.EndPoint(3255420, 3004);
            Assert.AreEqual(3255420, ep1.Address);
            Assert.AreEqual(3004, ep1.Port);

            ByteList bytes = new ByteList();
            ep1.Encode(bytes);
            Common.EndPoint ep2 = Common.EndPoint.Create(bytes);
            Assert.AreEqual(ep1.Address, ep2.Address);
            Assert.AreEqual(ep1.Port, ep2.Port);

            bytes.Clear();
            ep1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                ep2 = Common.EndPoint.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            ep1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                ep2 = Common.EndPoint.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }
        }

    }
}
