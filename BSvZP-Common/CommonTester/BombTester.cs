using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class BombTester
    {
        [TestMethod]
        public void Bomb_CheckConstructors()
        {
            Bomb b = new Bomb();
            Assert.AreEqual(0, b.CreatorId);
            Assert.IsNotNull(b.Excuses);
            Assert.IsNotNull(b.Twine);
            Assert.AreEqual(0, b.Excuses.Count);
            Assert.AreEqual(0, b.Twine.Count);
            Assert.IsNull(b.BuiltOnTick);

            Tick t1 = new Tick();
            List<Excuse> eList = new List<Excuse> { CreateExcuse(10), CreateExcuse(11), CreateExcuse(12) };
            List<WhiningTwine> wtList = new List<WhiningTwine> { CreateTwine(20), CreateTwine(21), CreateTwine(22) };
            b = new Bomb(1, eList, wtList, t1);
            Assert.AreEqual(1, b.CreatorId);
            Assert.AreSame(eList, b.Excuses);
            Assert.AreSame(wtList, b.Twine);
            Assert.AreSame(t1, b.BuiltOnTick);
        }

        [TestMethod]
        public void Bomb_CheckProperties()
        {
            Tick t1 = new Tick();
            List<Excuse> eList = new List<Excuse> { CreateExcuse(10), CreateExcuse(11), CreateExcuse(12) };
            List<WhiningTwine> wtList = new List<WhiningTwine> { CreateTwine(20), CreateTwine(21), CreateTwine(22) };
            Bomb b = new Bomb(1, eList, wtList, t1);
            Assert.AreEqual(1, b.CreatorId);
            Assert.AreSame(eList, b.Excuses);
            Assert.AreSame(wtList, b.Twine);
            Assert.AreSame(t1, b.BuiltOnTick);

            b.CreatorId = 135;
            Assert.AreEqual(135, b.CreatorId);
            b.CreatorId = 0;
            Assert.AreEqual(0, b.CreatorId);
            b.CreatorId = Int16.MaxValue;
            Assert.AreEqual(Int16.MaxValue, b.CreatorId);

            b.Excuses = new List<Excuse>();
            Assert.IsNotNull(b.Excuses);
            Assert.AreNotSame(eList, b.Excuses);
            b.Excuses = eList;
            Assert.AreSame(eList, b.Excuses);

            b.Twine = new List<WhiningTwine>();
            Assert.IsNotNull(b.Twine);
            Assert.AreNotSame(wtList, b.Twine);
            b.Twine = wtList;
            Assert.AreSame(wtList, b.Twine);
        }

        [TestMethod]
        public void Bomb_CheckEncodeAndDecode()
        {
            Tick t1 = new Tick();
            List<Excuse> eList = new List<Excuse> { CreateExcuse(10), CreateExcuse(11), CreateExcuse(12) };
            List<WhiningTwine> wtList = new List<WhiningTwine> { CreateTwine(20), CreateTwine(21), CreateTwine(22) };
            Bomb b1 = new Bomb(1, eList, wtList, t1);
            Assert.AreEqual(1, b1.CreatorId);
            Assert.AreSame(eList, b1.Excuses);
            Assert.AreSame(wtList, b1.Twine);
            Assert.AreSame(t1, b1.BuiltOnTick);

            ByteList bytes = new ByteList();
            b1.Encode(bytes);
            Bomb b2 = Bomb.Create(bytes);
            Assert.AreEqual(b1.CreatorId, b2.CreatorId);
            Assert.AreEqual(b1.Excuses.Count, b2.Excuses.Count);
            Assert.AreEqual(b1.Twine.Count, b2.Twine.Count);

            Assert.AreEqual(b1.BuiltOnTick.LogicalClock, b2.BuiltOnTick.LogicalClock);
            Assert.AreEqual(b1.BuiltOnTick.HashCode, b2.BuiltOnTick.HashCode);

            bytes.Clear();
            b1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                b2 = Bomb.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            b1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                b2 = Bomb.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }
        }

        private Excuse CreateExcuse(Int16 id)
        {
            return new Excuse(id, new List<Tick> { new Tick(), new Tick() }, new Tick());
        }

        private WhiningTwine CreateTwine(Int16 id)
        {
            return new WhiningTwine(id, new List<Tick> { new Tick(), new Tick() }, new Tick());
        }

    }
}
