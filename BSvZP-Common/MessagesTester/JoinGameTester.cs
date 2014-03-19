using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;
using Messages;

namespace MessagesTester
{
    /// <summary>
    /// Summary description for JoinGame
    /// </summary>
    [TestClass]
    public class JoinGameTester
    {
        [TestMethod]
        public void JoinGame_TestConstructorsAndFactories()
        {
            JoinGame jg = new JoinGame();
            Assert.AreEqual(0, jg.GameId);
            Assert.IsNull(jg.AgentInfo);

            AgentInfo agentInfo = new AgentInfo(1001, AgentInfo.PossibleAgentType.BrilliantStudent) { ANumber = "A0001", FirstName = "Joe", LastName = "Jone" };
            jg = new JoinGame(10, agentInfo);
            Assert.AreEqual(10, jg.GameId);

            Assert.AreSame(agentInfo, jg.AgentInfo);

            ByteList bytes = new ByteList();
            jg.Encode(bytes);
            Message msg = Message.Create(bytes);
            Assert.IsNotNull(msg);
            Assert.IsTrue(msg is JoinGame);
            JoinGame jg2 = msg as JoinGame;
            Assert.AreEqual(jg.GameId, jg2.GameId);
        }

        [TestMethod]
        public void JoinGame_Properties()
        {
            AgentInfo agentInfo = new AgentInfo(1001, AgentInfo.PossibleAgentType.BrilliantStudent) { ANumber = "A00001", FirstName = "Joe", LastName = "Jones" };
            JoinGame jg = new JoinGame(10, agentInfo);
            Assert.AreEqual(10, jg.GameId);
            Assert.AreEqual("A00001", jg.AgentInfo.ANumber);
            Assert.AreEqual("Joe", jg.AgentInfo.FirstName);
            Assert.AreEqual("Jones", jg.AgentInfo.LastName);
            Assert.AreSame(agentInfo, jg.AgentInfo);

            jg.GameId = 20;
            Assert.AreEqual(20, jg.GameId);
            jg.AgentInfo = null;
            Assert.IsNull(jg.AgentInfo);
            jg.AgentInfo = agentInfo;
            Assert.AreSame(agentInfo, jg.AgentInfo);

            Assert.AreEqual(Message.MESSAGE_CLASS_IDS.JoinGame, jg.MessageTypeId());
        }

        [TestMethod]
        public void JoinGame_EncodingAndDecoding()
        {
            AgentInfo agentInfo = new AgentInfo(1001, AgentInfo.PossibleAgentType.BrilliantStudent) { ANumber = "A0001", FirstName = "Joe", LastName = "Jone" };
            JoinGame jg1 = new JoinGame(10, agentInfo);
            Assert.AreEqual(10, jg1.GameId);
            Assert.AreSame(agentInfo, jg1.AgentInfo);

            ByteList bytes = new ByteList();
            jg1.Encode(bytes);
            JoinGame jg2 = JoinGame.Create(bytes);
            Assert.AreEqual(jg1.GameId, jg2.GameId);


            bytes.Clear();
            jg1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                jg2 = JoinGame.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            jg1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                jg2 = JoinGame.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }

    }
}
