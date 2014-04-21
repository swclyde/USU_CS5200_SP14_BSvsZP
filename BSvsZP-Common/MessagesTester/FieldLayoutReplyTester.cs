using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;
using Messages;

namespace MessagesTester
{
    [TestClass]
    public class FieldLayoutReplyTester
    {
        [TestMethod]
        public void FieldLayoutReply_TestEverything()
        {
            PlayingFieldLayout pfl = new PlayingFieldLayout(20, 25);
            Assert.AreEqual(20, pfl.Width);
            Assert.AreEqual(25, pfl.Height);
            Assert.IsNotNull(pfl.SidewalkSquares);    

            PlayingFieldReply r1 = new PlayingFieldReply(Reply.PossibleStatus.Success, pfl, "Test");

            Assert.AreEqual(Reply.PossibleStatus.Success, r1.Status);
            Assert.IsNotNull(r1.Layout);
            Assert.AreSame(pfl, r1.Layout);

            ByteList bytes = new ByteList();
            r1.Encode(bytes);
            Message msg = Message.Create(bytes);
            Assert.IsNotNull(msg);
            Assert.IsTrue(msg is PlayingFieldReply);
            PlayingFieldReply r2 = msg as PlayingFieldReply;
            Assert.AreEqual(Reply.PossibleStatus.Success, r2.Status);

            Assert.AreEqual(pfl.Height, r2.Layout.Height);
            Assert.AreEqual(pfl.Width, r2.Layout.Width);
        }
    }
}
