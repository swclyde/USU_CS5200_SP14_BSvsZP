﻿using System;
using System.Collections.Generic;
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

            byte[] addressBytes = new byte[4];
            addressBytes[0] = 10;
            addressBytes[1] = 211;
            addressBytes[2] = 55;
            addressBytes[3] = 20;

            Common.EndPoint ep3 = new Common.EndPoint(addressBytes, 2001);
            byte[] tmpBytes = BitConverter.GetBytes(ep3.Address);
            Assert.AreEqual(10, tmpBytes[0]);
            Assert.AreEqual(211, tmpBytes[1]);
            Assert.AreEqual(55, tmpBytes[2]);
            Assert.AreEqual(20, tmpBytes[3]);
            Assert.AreEqual(2001, ep3.Port);

            IPEndPoint ipEp = ep3.GetIPEndPoint();
            Assert.AreEqual(10, ipEp.Address.GetAddressBytes()[0]);
            Assert.AreEqual(211, ipEp.Address.GetAddressBytes()[1]);
            Assert.AreEqual(55, ipEp.Address.GetAddressBytes()[2]);
            Assert.AreEqual(20, ipEp.Address.GetAddressBytes()[3]);
            Assert.AreEqual(2001, ipEp.Port);
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

        [TestMethod]
        public void AgentInfo_CheckEquals()
        {
            Common.EndPoint ep1 = new Common.EndPoint("129.123.7.49:3520");
            Common.EndPoint ep2 = new Common.EndPoint("129.123.7.49:3520");
            Common.EndPoint ep3 = new Common.EndPoint("129.123.7.49:3521");
            Common.EndPoint ep4 = new Common.EndPoint("129.123.7.48:3521");

            Assert.IsTrue(ep1.Equals(ep2));
            Assert.IsTrue(ep2.Equals(ep1));
            Assert.IsFalse(ep1.Equals(ep3));
            Assert.IsFalse(ep3.Equals(ep1));
            Assert.IsFalse(ep1.Equals(ep4));
            Assert.IsFalse(ep4.Equals(ep1));
            Assert.IsFalse(ep2.Equals(ep4));
            Assert.IsFalse(ep4.Equals(ep2));
            Assert.IsFalse(ep3.Equals(ep4));
            Assert.IsFalse(ep4.Equals(ep3));

            ep3.Port = 3520;
            Assert.IsTrue(ep1.Equals(ep3));
            Assert.IsTrue(ep3.Equals(ep1));

            ep4.Address = ep1.Address;
            ep4.Port = ep1.Port;
            Assert.IsTrue(ep1.Equals(ep4));
            Assert.IsTrue(ep4.Equals(ep1));

            List<Common.EndPoint> epList = new List<Common.EndPoint>();
            epList.Add(new Common.EndPoint("129.123.7.49:3530"));
            epList.Add(new Common.EndPoint("129.123.7.49:3531"));
            epList.Add(new Common.EndPoint("129.123.7.49:3532"));
            epList.Add(new Common.EndPoint("129.123.7.49:3533"));
            epList.Add(new Common.EndPoint("129.123.7.49:3534"));
            epList.Add(new Common.EndPoint("129.123.7.49:3535"));
            Assert.AreEqual(6, epList.Count);

            Common.EndPoint t1 = new Common.EndPoint("129.123.7.49:3530");
            Common.EndPoint t2 = new Common.EndPoint("129.123.7.49:3532");
            Common.EndPoint t3 = new Common.EndPoint("129.123.7.49:3535");
            Common.EndPoint t4 = new Common.EndPoint("129.123.7.49:9999");
            Assert.AreEqual(0, epList.FindIndex(delegate(Common.EndPoint ep) { return ep.Equals(t1); }));
            Assert.AreEqual(2, epList.FindIndex(delegate(Common.EndPoint ep) { return ep.Equals(t2); }));
            Assert.AreEqual(5, epList.FindIndex(delegate(Common.EndPoint ep) { return ep.Equals(t3); }));
            Assert.AreEqual(-1, epList.FindIndex(delegate(Common.EndPoint ep) { return ep.Equals(t4); }));
        }

    }
}
