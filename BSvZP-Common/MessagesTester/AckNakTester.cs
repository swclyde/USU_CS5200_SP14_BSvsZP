using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Common;
using Messages;

namespace MessagesTester
{
    [TestClass]
    public class AckNakTester
    {
        [TestMethod]
        public void AckNak_CheckConstructorsAndFactories()
        {
            Tick t1 = new Tick();
            AckNak m = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Success, m.Status);
            Assert.AreEqual(10, m.IntResult);
            Assert.AreSame(t1, m.ObjResult);
            Assert.AreEqual("Test Message", m.Message);
            Assert.AreEqual("Test Note", m.Note);

            m = new AckNak(Reply.PossibleStatus.Failure, 20);
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Failure, m.Status);
            Assert.AreEqual(20, m.IntResult);
            Assert.IsNull(m.ObjResult);
            Assert.AreEqual("", m.Message);
            Assert.AreEqual("", m.Note);

            m = new AckNak(Reply.PossibleStatus.Failure, 20, "Test Message");
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Failure, m.Status);
            Assert.AreEqual(20, m.IntResult);
            Assert.IsNull(m.ObjResult);
            Assert.AreEqual("Test Message", m.Message);
            Assert.AreEqual("", m.Note);

            m = new AckNak(Reply.PossibleStatus.Failure, t1);
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Failure, m.Status);
            Assert.AreEqual(0, m.IntResult);
            Assert.AreSame(t1, m.ObjResult);
            Assert.AreEqual("", m.Message);
            Assert.AreEqual("", m.Note);

            m = new AckNak(Reply.PossibleStatus.Failure, t1, "Test Message");
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Failure, m.Status);
            Assert.AreEqual(0, m.IntResult);
            Assert.AreSame(t1, m.ObjResult);
            Assert.AreEqual("Test Message", m.Message);
            Assert.AreEqual("", m.Note);

            ByteList bytes = new ByteList();
            m.Encode(bytes);
            Message msg = Message.Create(bytes);
            Assert.IsNotNull(msg);
            Assert.IsTrue(msg is AckNak);
            AckNak m2 = msg as AckNak;
            Assert.AreEqual(m.Status, m2.Status);
            Assert.AreEqual(m.Note, m2.Note);

        }

        [TestMethod]
        public void AckNak_CheckProperties()
        {
            Tick t1 = new Tick();
            AckNak m = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Success, m.Status);
            Assert.AreEqual(10, m.IntResult);
            Assert.AreSame(t1, m.ObjResult);
            Assert.AreEqual("Test Message", m.Message);
            Assert.AreEqual("Test Note", m.Note);

            m.IntResult = 200;
            Assert.AreEqual(200, m.IntResult);

            m.ObjResult = null;
            Assert.IsNull(m.ObjResult);
            m.ObjResult = t1;
            Assert.AreSame(t1, m.ObjResult);

            m.Message = "Testing";
            Assert.AreEqual("Testing", m.Message);

            m.Note = "Test Note";
            Assert.AreEqual("Test Note", m.Note);

            Assert.AreEqual( Message.MESSAGE_CLASS_IDS.AckNak, m.MessageTypeId());
        }

        [TestMethod]
        public void AckNak_CheckEncodeDecode()
        {
            Tick t1 = new Tick();
            AckNak m1 = new AckNak(Reply.PossibleStatus.Success, 10, t1, "Test Message", "Test Note");
            Assert.AreEqual(Reply.PossibleTypes.AckNak, m1.ReplyType);
            Assert.AreEqual(Reply.PossibleStatus.Success, m1.Status);
            Assert.AreEqual(10, m1.IntResult);
            Assert.AreSame(t1, m1.ObjResult);
            Assert.AreEqual("Test Message", m1.Message);
            Assert.AreEqual("Test Note", m1.Note);

            ByteList bytes = new ByteList();
            m1.Encode(bytes);
            AckNak m2 = AckNak.Create(bytes);
            Assert.AreEqual(m1.Status, m2.Status);
            Assert.AreEqual(m1.IntResult, m2.IntResult);
            Assert.AreEqual(((Tick)m1.ObjResult).LogicalClock, ((Tick)m2.ObjResult).LogicalClock);
            Assert.AreEqual(m1.Message, m2.Message);
            Assert.AreEqual(m1.Note, m2.Note);

            bytes.Clear();
            m1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                m2 = AckNak.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            m1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                m2 = AckNak.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }

    }
}
