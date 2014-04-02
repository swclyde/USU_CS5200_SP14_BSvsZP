using System;
using System.Text;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Messages;
using Common;

namespace MessagesTester
{
    [TestClass]
    public class MessageEncodingSamples
    {
        [TestMethod]
        public void CreateEncodingSamples()
        {
            StreamWriter writer = new StreamWriter("MessageSamples.txt");

            MessageNumber msgNumber = MessageNumber.Create(100, 120);
            MessageNumber conversationNumber = MessageNumber.Create(200, 240);
            AgentInfo agentInfo = new AgentInfo(10, AgentInfo.PossibleAgentType.BrilliantStudent, new Common.EndPoint("129.123.5.10:1234"))
                                        {
                                            AgentStatus = AgentInfo.PossibleAgentStatus.InGame,
                                            ANumber = "A00001",
                                            FirstName = "Joe",
                                            LastName = "Jones",
                                            Location = new FieldLocation(10, 20),
                                            Points = 100,
                                            Strength = 200,
                                            Speed = 1.2
                                        };


            AckNak ackNak = new AckNak(Reply.PossibleStatus.Success, agentInfo, "Test Message")
                                        {
                                            MessageNr = msgNumber,
                                            ConversationId = conversationNumber,
                                            IntResult = 99,
                                            Note = "Test Note"
                                        };
            writer.WriteLine("AckNak");
            writer.WriteLine("\tMessageNr={0}", ackNak.MessageNr.ToString());
            writer.WriteLine("\tConversationId={0}", ackNak.ConversationId.ToString());
            writer.WriteLine("\tReplyType={0}", ackNak.ReplyType);
            writer.WriteLine("\tAckNak Status={0}", ackNak.Status);
            writer.WriteLine("\tAgent Info:");
            writer.WriteLine("\t\tId={0}", agentInfo.Id);
            writer.WriteLine("\t\tAgentStatus={0}", agentInfo.AgentStatus);
            writer.WriteLine("\t\tANumber={0}", agentInfo.ANumber);
            writer.WriteLine("\t\tFirstName={0}", agentInfo.FirstName);
            writer.WriteLine("\t\tLastName={0}", agentInfo.LastName);
            writer.WriteLine("\t\tLocation={0}", agentInfo.Location.ToString());
            writer.WriteLine("\t\tPoints={0}", agentInfo.Points);
            writer.WriteLine("\t\tStrength={0}", agentInfo.Strength);
            writer.WriteLine("\t\tSpeed={0}", agentInfo.Speed);

            ByteList byteList = new ByteList();
            ackNak.Encode(byteList);

            writer.WriteLine("");
            writer.WriteLine("Encoding:");
            writer.WriteLine(byteList.CreateLogString());
            writer.WriteLine("");
            writer.WriteLine("------------------------------------");
            writer.WriteLine("");

            JoinGame joinGame = new JoinGame(20, agentInfo)
                                        {
                                            MessageNr = msgNumber,
                                            ConversationId = conversationNumber,
                                        };

            writer.WriteLine("JoinGame");
            writer.WriteLine("\tMessageNr={0}", joinGame.MessageNr.ToString());
            writer.WriteLine("\tConversationId={0}", joinGame.ConversationId.ToString());
            writer.WriteLine("\tGameId={0}", joinGame.GameId);
            writer.WriteLine("\tAgent Info:");
            writer.WriteLine("\t\tId={0}", agentInfo.Id);
            writer.WriteLine("\t\tAgentStatus={0}", agentInfo.AgentStatus);
            writer.WriteLine("\t\tANumber={0}", agentInfo.ANumber);
            writer.WriteLine("\t\tFirstName={0}", agentInfo.FirstName);
            writer.WriteLine("\t\tLastName={0}", agentInfo.LastName);
            writer.WriteLine("\t\tLocation={0}", agentInfo.Location.ToString());
            writer.WriteLine("\t\tPoints={0}", agentInfo.Points);
            writer.WriteLine("\t\tStrength={0}", agentInfo.Strength);
            writer.WriteLine("\t\tSpeed={0}", agentInfo.Speed);

            byteList = new ByteList();
            joinGame.Encode(byteList);

            writer.WriteLine("");
            writer.WriteLine("Encoding:");
            writer.WriteLine(byteList.CreateLogString());
            writer.WriteLine("");
            writer.WriteLine("------------------------------------");
            writer.WriteLine("");

            // TODO: All of the other message types
        }
    }
}
