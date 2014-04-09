using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;
using Messages;

namespace MessagesTester
{
    [TestClass]
    public class ReadyReplyTester
    {
        [TestMethod]
        public void ReadyReply_TestEverything()
        {
            ReadyReply r1 = new ReadyReply(Reply.PossibleStatus.Failure);
            Assert.AreEqual(Reply.PossibleStatus.Failure, r1.Status);

            r1 = new ReadyReply(Reply.PossibleStatus.Success, "test note");
            Assert.AreEqual(Reply.PossibleStatus.Success, r1.Status);
            Assert.AreEqual("test note", r1.Note);

            ByteList byteList = new ByteList();
            r1.Encode(byteList);

            Message msg = Message.Create(byteList);
            Assert.IsNotNull(msg);
            Assert.IsTrue(msg is ReadyReply);
            ReadyReply r2 = msg as ReadyReply;
            Assert.AreEqual(r1.Status, r2.Status);
            Assert.AreEqual(r1.Note, r2.Note);
        }
    }
}
