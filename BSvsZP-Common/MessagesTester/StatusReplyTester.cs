using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;
using Messages;

namespace MessagesTester
{
    [TestClass]
    public class StatusReplyTester
    {
        [TestMethod]
        public void StatusReply_TestEverything()
        {
            AgentInfo agentInfo = new AgentInfo(1001, AgentInfo.PossibleAgentType.BrilliantStudent) { ANumber = "A0001", FirstName = "Joe", LastName = "Jone" };
            
            StatusReply r1 = new StatusReply(Reply.PossibleStatus.Success, agentInfo);
            Assert.AreEqual(Reply.PossibleStatus.Success, r1.Status);
            Assert.AreSame(agentInfo, r1.Info);

            r1 = new StatusReply(Reply.PossibleStatus.Success, agentInfo, "test note");
            Assert.AreEqual(Reply.PossibleStatus.Success, r1.Status);
            Assert.AreSame(agentInfo, r1.Info);
            Assert.AreEqual("test note", r1.Note);

            ByteList byteList = new ByteList();
            r1.Encode(byteList);

            Message msg = Message.Create(byteList);
            Assert.IsNotNull(msg);
            Assert.IsTrue(msg is StatusReply);
            StatusReply r2 = msg as StatusReply;
            Assert.AreEqual(r1.Status, r2.Status);
            Assert.AreEqual(r1.Info.Id, r2.Info.Id);
            Assert.AreEqual(r1.Info.LastName, r2.Info.LastName);
            Assert.AreEqual(r1.Note, r2.Note);
        }
    }
}
