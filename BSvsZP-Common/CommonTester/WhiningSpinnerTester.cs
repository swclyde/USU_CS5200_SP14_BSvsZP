using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class WhiningSpinnerTester
    {
        [TestMethod]
        public void WhiningSpinner_CheckConstructors()
        {
            WhiningTwine e = new WhiningTwine();
            Assert.AreEqual(0, e.CreatorId);
            Assert.IsNotNull(e.Ticks);
            Assert.AreEqual(0, e.Ticks.Count);
            Assert.IsNull(e.RequestTick);

            Tick t1 = new Tick();
            Tick t2 = new Tick();
            Tick t3 = new Tick();
            Tick t4 = new Tick();
            List<Tick> ticks = new List<Tick> { t1, t2, t3 };
            e = new WhiningTwine(10, ticks, t4);
            Assert.AreEqual(10, e.CreatorId);
            Assert.IsNotNull(e.Ticks);
            Assert.AreEqual(3, e.Ticks.Count);
            Assert.AreSame(t1, e.Ticks[0]);
            Assert.AreSame(t2, e.Ticks[1]);
            Assert.AreSame(t3, e.Ticks[2]);
            Assert.AreSame(t4, e.RequestTick);
        }

        [TestMethod]
        public void WhiningSpinner_CheckProperties()
        {
            Tick t1 = new Tick();
            Tick t2 = new Tick();
            Tick t3 = new Tick();
            Tick t4 = new Tick();
            List<Tick> ticks = new List<Tick> { t1, t2, t3 };
            WhiningTwine e = new WhiningTwine(10, ticks, t4);
            Assert.AreEqual(10, e.CreatorId);
            Assert.IsNotNull(e.Ticks);
            Assert.AreEqual(3, e.Ticks.Count);
            Assert.AreSame(t1, e.Ticks[0]);
            Assert.AreSame(t2, e.Ticks[1]);
            Assert.AreSame(t3, e.Ticks[2]);
            Assert.AreSame(t4, e.RequestTick);

            e.CreatorId = 135;
            Assert.AreEqual(135, e.CreatorId);
            e.CreatorId = 0;
            Assert.AreEqual(0, e.CreatorId);
            e.CreatorId = Int16.MaxValue;
            Assert.AreEqual(Int16.MaxValue, e.CreatorId);

            e.Ticks = null;
            Assert.IsNull(e.Ticks);
            e.Ticks = ticks;
            Assert.AreSame(ticks, e.Ticks);

            e.RequestTick = null;
            Assert.IsNull(e.RequestTick);
            e.RequestTick = t4;
            Assert.AreSame(t4, e.RequestTick);
        }

        [TestMethod]
        public void WhiningSpinner_CheckEncodeAndDecode()
        {
            Tick t1 = new Tick();
            Tick t2 = new Tick();
            Tick t3 = new Tick();
            Tick t4 = new Tick();
            List<Tick> ticks = new List<Tick> { t1, t2, t3 };
            WhiningTwine e1 = new WhiningTwine(10, ticks, t4);
            Assert.AreEqual(10, e1.CreatorId);
            Assert.IsNotNull(e1.Ticks);
            Assert.AreEqual(3, e1.Ticks.Count);
            Assert.AreSame(t1, e1.Ticks[0]);
            Assert.AreSame(t2, e1.Ticks[1]);
            Assert.AreSame(t3, e1.Ticks[2]);
            Assert.AreSame(t4, e1.RequestTick);

            ByteList bytes = new ByteList();
            e1.Encode(bytes);
            WhiningTwine e2 = WhiningTwine.Create(bytes);
            Assert.AreEqual(e1.CreatorId, e2.CreatorId);
            Assert.AreEqual(e1.Ticks.Count, e2.Ticks.Count);
            Assert.AreEqual(e1.Ticks[0].LogicalClock, e2.Ticks[0].LogicalClock);
            Assert.AreEqual(e1.Ticks[0].HashCode, e2.Ticks[0].HashCode);
            Assert.AreEqual(e1.Ticks[1].LogicalClock, e2.Ticks[1].LogicalClock);
            Assert.AreEqual(e1.Ticks[1].HashCode, e2.Ticks[1].HashCode);
            Assert.AreEqual(e1.Ticks[2].LogicalClock, e2.Ticks[2].LogicalClock);
            Assert.AreEqual(e1.Ticks[2].HashCode, e2.Ticks[2].HashCode);
            Assert.AreEqual(e1.RequestTick.LogicalClock, e2.RequestTick.LogicalClock);
            Assert.AreEqual(e1.RequestTick.HashCode, e2.RequestTick.HashCode);

            bytes.Clear();
            e1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                e2 = WhiningTwine.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            e1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                e2 = WhiningTwine.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }
    }
}
