using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;

namespace CommonTester
{
    [TestClass]
    public class AgentListTester
    {
        [TestMethod]
        public void AgentList_CheckConstructors()
        {
            AgentList list = new AgentList();
            Assert.AreEqual(0, list.Count);
        }

        [TestMethod]
        public void AgentList_CheckProperties()
        {
            AgentInfo info1 = new AgentInfo(10, AgentInfo.PossibleAgentType.ExcuseGenerator);
            AgentInfo info2 = new AgentInfo(11, AgentInfo.PossibleAgentType.WhiningSpinner);
            AgentInfo info3 = new AgentInfo(12, AgentInfo.PossibleAgentType.BrilliantStudent);

            AgentList list = new AgentList();
            list.Add(info1);
            list.Add(info2);
            list.Add(info3);
            Assert.AreSame(info1, list[0]);
            Assert.AreSame(info2, list[1]);
            Assert.AreSame(info3, list[2]);
        }

        [TestMethod]
        public void AgentList_CheckEncodeAndDecode()
        {
            AgentInfo info1 = new AgentInfo(10, AgentInfo.PossibleAgentType.ExcuseGenerator);
            AgentInfo info2 = new AgentInfo(11, AgentInfo.PossibleAgentType.WhiningSpinner);
            AgentInfo info3 = new AgentInfo(12, AgentInfo.PossibleAgentType.BrilliantStudent);

            AgentList list1 = new AgentList();
            list1.Add(info1);
            list1.Add(info2);
            list1.Add(info3);

            ByteList bytes = new ByteList();
            list1.Encode(bytes);
            AgentList list2 = AgentList.Create(bytes);
            Assert.AreEqual(3, list2.Count);
            Assert.AreEqual(10, list2[0].Id);
            Assert.AreEqual(11, list2[1].Id);
            Assert.AreEqual(12, list2[2].Id);

            bytes.Clear();
            list1.Encode(bytes);
            bytes.GetByte();            // Read one byte, which will throw the length off
            try
            {
                list2 = AgentList.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

            bytes.Clear();
            list1.Encode(bytes);
            bytes.Add((byte)100);       // Add a byte
            bytes.GetByte();            // Read one byte, which will make the ID wrong
            try
            {
                list2 = AgentList.Create(bytes);
                Assert.Fail("Expected an exception to be thrown");
            }
            catch (ApplicationException)
            {
            }

        }
    }
}
