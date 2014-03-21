using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class GameInfoTester
    {
        [TestMethod]
        public void GameInfo_TestEverything()
        {
            GameInfo g0 = new GameInfo();
            Assert.AreEqual(0, g0.Id);
            Assert.IsNull(g0.Label);
            Assert.IsNotNull(g0.AliveTimestamp);
            Assert.IsTrue(g0.AliveTimestamp.AddMilliseconds(3000) > DateTime.Now);
            Assert.IsNull(g0.CommunicationEndPoint);
            Assert.AreEqual(GameInfo.GameStatus.NOT_INITIAlIZED, g0.Status);

            EndPoint ep = new EndPoint("113.24.4.1:1325");
            g0 = new GameInfo()
                    {
                            Id = 10,
                            Label = "Test",
                            CommunicationEndPoint = ep,
                            AliveTimestamp = DateTime.Now,
                            Status = GameInfo.GameStatus.RUNNING
                    };
            Assert.AreEqual(10, g0.Id);
            Assert.AreEqual("Test", g0.Label);
            Assert.IsNotNull(g0.AliveTimestamp);
            Assert.IsTrue(g0.AliveTimestamp.AddMilliseconds(1000) > DateTime.Now);
            Assert.AreSame(ep, g0.CommunicationEndPoint);
            Assert.AreEqual(GameInfo.GameStatus.RUNNING, g0.Status);

            g0 = new GameInfo(200, "Testing", ep, GameInfo.GameStatus.COMPLETED);
            Assert.AreEqual(200, g0.Id);
            Assert.AreEqual("Testing", g0.Label);
            Assert.IsNotNull(g0.AliveTimestamp);
            Assert.IsTrue(g0.AliveTimestamp.AddMilliseconds(1000) > DateTime.Now);
            Assert.AreSame(ep, g0.CommunicationEndPoint);
            Assert.AreEqual(GameInfo.GameStatus.COMPLETED, g0.Status);

            g0 = new GameInfo(300, "More Testing", ep, "1");
            Assert.AreEqual(300, g0.Id);
            Assert.AreEqual("More Testing", g0.Label);
            Assert.IsNotNull(g0.AliveTimestamp);
            Assert.IsTrue(g0.AliveTimestamp.AddMilliseconds(1000) > DateTime.Now);
            Assert.AreSame(ep, g0.CommunicationEndPoint);
            Assert.AreEqual(GameInfo.GameStatus.AVAILABLE, g0.Status);

        }
    }
}
