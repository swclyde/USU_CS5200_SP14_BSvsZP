using System;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;

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

            EndPoint ep0 = new EndPoint("129.143.23.10:2300");
            GameInfo g0 = registrar.RegisterGame("Test Game 0", ep0);
            Assert.IsNotNull(g0);
            Assert.AreEqual("Test Game 0", g0.Label);
            Assert.AreEqual(ep0.Address, g0.CommunicationEndPoint.Address);
            Assert.AreEqual(ep0.Port, g0.CommunicationEndPoint.Port);
            Assert.AreEqual(GameInfo.GameStatus.NOT_INITIAlIZED, g0.Status);

            EndPoint ep1 = new EndPoint("129.143.23.10:2304");
            GameInfo g1 = registrar.RegisterGame("Test Game 1", ep1);
            Assert.IsNotNull(g1);
            Assert.AreEqual("Test Game 1", g1.Label);
            Assert.AreEqual(ep1.Address, g1.CommunicationEndPoint.Address);
            Assert.AreEqual(ep1.Port, g1.CommunicationEndPoint.Port);
            Assert.AreEqual(GameInfo.GameStatus.NOT_INITIAlIZED, g0.Status);

            GameInfo[] games = registrar.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED);
            Assert.IsTrue(GamesContain(games, g0.Id, g0.Label));
            Assert.IsTrue(GamesContain(games, g1.Id, g1.Label));

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

    }

}
