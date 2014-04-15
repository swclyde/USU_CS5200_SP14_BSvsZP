using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class AgentInfoTester
    {
        private StateChange recentStateChange = null;

        [TestMethod]
        public void AgentInfo_CheckConstructors()
        {
            AgentInfo info = new AgentInfo();
            Assert.AreEqual(0, info.Id);
            Assert.IsNull(info.ANumber);
            Assert.IsNull(info.FirstName);
            Assert.IsNull(info.LastName);
            Assert.AreEqual(0, info.Strength);
            Assert.AreEqual(0, info.Speed);
            Assert.AreEqual(0, info.Points);
            Assert.IsNull(info.Location);
            Assert.IsNull(info.CommunicationEndPoint);

            info = new AgentInfo(10, AgentInfo.PossibleAgentType.ExcuseGenerator);
            Assert.AreEqual(10, info.Id);
            Assert.IsNull(info.ANumber);
            Assert.IsNull(info.FirstName);
            Assert.IsNull(info.LastName);
            Assert.AreEqual(0, info.Strength);
            Assert.AreEqual(0, info.Speed);
            Assert.AreEqual(0, info.Points);
            Assert.IsNull(info.Location);
            Assert.IsNull(info.CommunicationEndPoint);
            Assert.AreEqual(AgentInfo.PossibleAgentType.ExcuseGenerator, info.AgentType);

            EndPoint ep = new EndPoint("129.123.7.24:1345");
            info = new AgentInfo(20, AgentInfo.PossibleAgentType.WhiningSpinner, ep);
            Assert.AreEqual(20, info.Id);
            Assert.IsNull(info.ANumber);
            Assert.IsNull(info.FirstName);
            Assert.IsNull(info.LastName);
            Assert.AreEqual(0, info.Strength);
            Assert.AreEqual(0, info.Speed);
            Assert.AreEqual(0, info.Points);
            Assert.IsNull(info.Location);
            Assert.AreSame(ep, info.CommunicationEndPoint);
            Assert.AreEqual(AgentInfo.PossibleAgentType.WhiningSpinner, info.AgentType);

        }

        [TestMethod]
        public void AgentInfo_CheckProperties()
        {
            EndPoint ep = new EndPoint("129.123.7.24:1345");
            AgentInfo info = new AgentInfo(20, AgentInfo.PossibleAgentType.WhiningSpinner, ep)
                    {   ANumber = "A00001",
                        FirstName = "Joe",
                        LastName = "Jones",
                        Location = new FieldLocation(10, 20, false),
                        Strength = 1200.5,
                        Speed = 1500.0,
                        Points = 3332.42 };

            Assert.AreEqual(20, info.Id);
            Assert.AreEqual(AgentInfo.PossibleAgentType.WhiningSpinner, info.AgentType);
            Assert.AreEqual("A00001", info.ANumber);
            Assert.AreEqual("Joe", info.FirstName);
            Assert.AreEqual("Jones", info.LastName);
            Assert.AreEqual(1200.5, info.Strength);
            Assert.AreEqual(1500.0, info.Speed);
            Assert.AreEqual(3332.42, info.Points);
            Assert.AreEqual(10, info.Location.X);
            Assert.AreEqual(20, info.Location.Y);
            Assert.AreSame(ep, info.CommunicationEndPoint);

            info.Changed += ChangedEventHandler;

            // Id Property
            recentStateChange = null;
            info.Id = 1002;
            Assert.AreEqual(1002, info.Id);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            recentStateChange = null;
            info.Id = 0;
            Assert.AreEqual(0, info.Id);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            info.Id = Int16.MaxValue;
            Assert.AreEqual(Int16.MaxValue, info.Id);
            info.Id = 10;
            Assert.AreEqual(10, info.Id);

            // AgentType
            recentStateChange = null;
            info.AgentType = AgentInfo.PossibleAgentType.BrilliantStudent;
            Assert.AreEqual(AgentInfo.PossibleAgentType.BrilliantStudent, info.AgentType);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            // Status
            info.AgentStatus = AgentInfo.PossibleAgentStatus.InGame;
            Assert.AreEqual(AgentInfo.PossibleAgentStatus.InGame, info.AgentStatus);
            info.AgentStatus = AgentInfo.PossibleAgentStatus.TryingToJoin;
            Assert.AreEqual(AgentInfo.PossibleAgentStatus.TryingToJoin, info.AgentStatus);
            info.AgentStatus = AgentInfo.PossibleAgentStatus.WonGame;
            Assert.AreEqual(AgentInfo.PossibleAgentStatus.WonGame, info.AgentStatus);

            // ANumber
            recentStateChange = null;
            info.ANumber = "A000234";
            Assert.AreEqual("A000234", info.ANumber);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            info.ANumber = null;
            Assert.IsNull(info.ANumber);
            info.ANumber = "A012345";
            Assert.AreEqual("A012345", info.ANumber);

            // FirstName
            recentStateChange = null;
            info.FirstName = "Henry";
            Assert.AreEqual("Henry", info.FirstName);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            info.FirstName = null;
            Assert.IsNull(info.FirstName);
            info.FirstName = "John";
            Assert.AreEqual("John", info.FirstName);

            // LastName
            recentStateChange = null;
            info.LastName = "Franks";
            Assert.AreEqual("Franks", info.LastName);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            info.LastName = null;
            Assert.IsNull(info.LastName);
            info.LastName = "Jones";
            Assert.AreEqual("Jones", info.LastName);

            // Strength
            recentStateChange = null;
            info.Strength = 123.45;
            Assert.AreEqual(123.45, info.Strength);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            // Speed
            recentStateChange = null;
            info.Speed = 23.456;
            Assert.AreEqual(23.456, info.Speed);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            // Speed
            recentStateChange = null;
            info.Points = 53.6;
            Assert.AreEqual(53.6, info.Points);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            // Location
            recentStateChange = null;
            FieldLocation f = new FieldLocation(10, 20);
            info.Location = f;
            Assert.AreSame(f, info.Location);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

            // CommunicationEndPoint
            recentStateChange = null;
            EndPoint ep1 = new EndPoint(3242, 1000);
            info.CommunicationEndPoint = ep1;
            Assert.AreSame(ep1, info.CommunicationEndPoint);
            Assert.IsNotNull(recentStateChange);
            Assert.AreEqual(recentStateChange.Type, StateChange.ChangeType.UPDATE);
            Assert.AreSame(info, recentStateChange.Subject);

        }

        [TestMethod]
        public void AgentInfo_CheckEncodeAndDecode()
        {
            EndPoint ep = new EndPoint("129.123.7.24:1345");
            AgentInfo info1 = new AgentInfo(20, AgentInfo.PossibleAgentType.WhiningSpinner, ep)
            {
                ANumber = "A00001",
                FirstName = "Joe",
                LastName = "Jones",
                Location = new FieldLocation(10, 20, false),
                Strength = 1200.5,
                Speed = 1500.0
            };

            ByteList bytes = new ByteList();
            info1.Encode(bytes);
            AgentInfo info2 = AgentInfo.Create(bytes);
            Assert.AreEqual(info1.Id, info2.Id);
            Assert.AreEqual(info1.AgentType, info2.AgentType);
            Assert.AreEqual(info1.ANumber, info2.ANumber);
            Assert.AreEqual(info1.FirstName, info2.FirstName);
            Assert.AreEqual(info1.LastName, info2.LastName);
            Assert.AreEqual(info1.Strength, info2.Strength);
            Assert.AreEqual(info1.Speed, info2.Speed);
            Assert.AreEqual(info1.Points, info2.Points);
            Assert.AreEqual(info1.Location.X, info2.Location.X);
            Assert.AreEqual(info1.Location.Y, info2.Location.Y);
            Assert.AreEqual(info1.CommunicationEndPoint.Address, info2.CommunicationEndPoint.Address);
            Assert.AreEqual(info1.CommunicationEndPoint.Port, info2.CommunicationEndPoint.Port);

            bytes.Clear();
            info1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                info2 = AgentInfo.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            info1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                info2 = AgentInfo.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }
        }

        private void ChangedEventHandler(StateChange stateChange)
        {
            recentStateChange = stateChange;
        }
    }
}
