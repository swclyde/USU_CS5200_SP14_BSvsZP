using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Common;

namespace CommonTester
{
    [TestClass]
    public class TickTester
    {
        [TestMethod]
        public void TickTester_CheckConstructors()
        {
            Tick tick1 = new Tick(10, 20);
            Assert.AreEqual(10, tick1.LogicalClock);
            Assert.AreEqual(20, tick1.HashCode);

            tick1 = new Tick();
            Tick tick2 = new Tick();
            Assert.AreEqual(tick1.LogicalClock + 1, tick2.LogicalClock);
            Assert.AreNotEqual(tick1.HashCode, tick2.HashCode);
        }

        [TestMethod]
        public void TickTester_CheckProperties()
        {
            Tick tick1 = new Tick();
            tick1.LogicalClock = 100;
            Assert.AreEqual(100, tick1.LogicalClock);
            Assert.AreNotEqual(100, tick1.HashCode);

            Tick tick2 = new Tick();
            tick2.LogicalClock = tick1.LogicalClock + 1;
            Assert.AreEqual(101, tick2.LogicalClock);
            Assert.AreNotEqual(tick1.HashCode, tick2.HashCode);
        }

        [TestMethod]
        public void TickTester_CheckEncodeDecode()
        {
            Tick tick1 = new Tick();
            tick1.LogicalClock = 100;
            ByteList bytes = new ByteList();
            tick1.Encode(bytes);
            Tick tick2 = Tick.Create(bytes);
            Assert.AreEqual(tick1.LogicalClock, tick2.LogicalClock);
            Assert.AreEqual(tick1.HashCode, tick2.HashCode);

            tick1.LogicalClock = 0;
            bytes = new ByteList();
            tick1.Encode(bytes);
            tick2 = Tick.Create(bytes);
            Assert.AreEqual(tick1.LogicalClock, tick2.LogicalClock);
            Assert.AreEqual(tick1.HashCode, tick2.HashCode);

            tick1.LogicalClock = Int32.MaxValue;
            bytes = new ByteList();
            tick1.Encode(bytes);
            tick2 = Tick.Create(bytes);
            Assert.AreEqual(tick1.LogicalClock, tick2.LogicalClock);
            Assert.AreEqual(tick1.HashCode, tick2.HashCode);

            bytes.Clear();
            tick1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                tick2 = Tick.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            tick1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                tick2 = Tick.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }

        [TestMethod]
        public void TickTester_CheckOtherMethods()
        {
            Tick tick1 = new Tick(10, 10);
            Assert.AreEqual(10, tick1.LogicalClock);
            Assert.AreEqual(10, tick1.HashCode);
            Assert.IsFalse(tick1.IsValid);

            for (int i = 0; i < 100; i++)
            {
                tick1 = new Tick();
                Assert.IsTrue(tick1.IsValid);
            }

        }

    }
}
