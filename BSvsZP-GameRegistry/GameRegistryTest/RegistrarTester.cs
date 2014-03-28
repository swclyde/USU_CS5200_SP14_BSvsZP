using System;
using System.Text;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Net;
using System.Net.Sockets;

using Common;
using GameRegistryTester.GameRegistry;

namespace GameRegistryTester
{
    [TestClass]
    public abstract class RegistrarTester
    {
        [TestMethod]
        public void Registrar_TestEverything()
        {
            RegistrarClient registrar = new RegistrarClient("LocalHttpBinding_IRegistrar");
            // RegistrarClient registrar = new RegistrarClient("ProdHttpBinding_IRegistrar");

            Common.EndPoint ep0 = new Common.EndPoint("129.143.23.10:2300");
            GameInfo g0 = registrar.RegisterGame("Test Game 0", ep0);
            Assert.IsNotNull(g0);
            Assert.AreEqual("Test Game 0", g0.Label);
            Assert.AreEqual(ep0.Address, g0.CommunicationEndPoint.Address);
            Assert.AreEqual(ep0.Port, g0.CommunicationEndPoint.Port);
            Assert.AreEqual(GameInfo.GameStatus.NOT_INITIAlIZED, g0.Status);

            Common.EndPoint ep1 = new Common.EndPoint("129.143.23.10:2304");
            GameInfo g1 = registrar.RegisterGame("Test Game 1", ep1);
            Assert.IsNotNull(g1);
            Assert.AreEqual("Test Game 1", g1.Label);
            Assert.AreEqual(ep1.Address, g1.CommunicationEndPoint.Address);
            Assert.AreEqual(ep1.Port, g1.CommunicationEndPoint.Port);
            Assert.AreEqual(GameInfo.GameStatus.NOT_INITIAlIZED, g0.Status);

            GameInfo[] games = registrar.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED);
            Assert.IsTrue(GamesContain(games, g0.Id, g0.Label));
            Assert.IsTrue(GamesContain(games, g1.Id, g1.Label));

            GameInfoAlt[] gamesAlt = registrar.GetGamesAlt(GameInfo.GameStatus.NOT_INITIAlIZED);
            Assert.IsTrue(GamesContainAlt(gamesAlt, g0.Id, g0.Label));
            Assert.IsTrue(GamesContainAlt(gamesAlt, g1.Id, g1.Label));

            registrar.ChangeStatus(g1.Id, GameInfo.GameStatus.AVAILABLE);
            games = registrar.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED);
            Assert.IsTrue(GamesContain(games, g0.Id, g0.Label));
            games = registrar.GetGames(GameInfo.GameStatus.AVAILABLE);
            Assert.IsTrue(GamesContain(games, g1.Id, g1.Label));

            for (int i = 0; i < 90; i++)
            {
                Thread.Sleep(1000);
                registrar.AmAlive(g1.Id);
            }

            games = registrar.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED);
            Assert.IsFalse(GamesContain(games, g0.Id, g0.Label));
            games = registrar.GetGames(GameInfo.GameStatus.AVAILABLE);
            Assert.IsTrue(GamesContain(games, g1.Id, g1.Label));
        }

        [TestMethod]
        public void Registrar_TestEndPointReflection()
        {
            RegistrarClient registrar = new RegistrarClient("LocalHttpBinding_IRegistrar");
            // RegistrarClient registrar = new RegistrarClient("ProdHttpBinding_IRegistrar");

            string reflectorEndPointString = registrar.EndPointReflector();
            Assert.IsNotNull(reflectorEndPointString);
            Common.EndPoint reflectorEndPoint = new Common.EndPoint(reflectorEndPointString);

            UdpClient testClient = new UdpClient(0, AddressFamily.InterNetwork);
            byte[] sendBuffer = ASCIIEncoding.ASCII.GetBytes("hello");
            testClient.Send(sendBuffer, sendBuffer.Length, reflectorEndPoint.GetIPEndPoint());

            testClient.Client.ReceiveTimeout = 1000;
            IPEndPoint sendingEP = new IPEndPoint(IPAddress.Any, 0);
            byte[] receiveBuffer = testClient.Receive(ref sendingEP);

            Assert.IsNotNull(receiveBuffer);
            string reflectedEP = ASCIIEncoding.ASCII.GetString(receiveBuffer);
            Assert.IsNotNull(reflectedEP);
            Assert.IsTrue(reflectedEP.Length > 8);
            string[] tmp = reflectedEP.Split(':');
            Assert.IsTrue(tmp.Length == 2);
        }

        private bool GamesContain(GameInfo[] games, Int16 id, string label)
        {
            bool result = false;
            foreach (GameInfo game in games)
                if (game.Id == id && game.Label == label)
                {
                    result = true;
                    break;
                }
            return result;
        }

        private bool GamesContainAlt(GameInfoAlt[] games, Int16 id, string label)
        {
            bool result = false;
            foreach (GameInfoAlt game in games)
                if (game.Id == id && game.Label == label)
                {
                    result = true;
                    break;
                }
            return result;
        }

    }

}
