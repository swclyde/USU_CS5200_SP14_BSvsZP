using System;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using Common;
using GameRegistry;

namespace GameRegistryTester
{
    [TestClass]
    public class RegistryTester
    {
        [TestMethod]
        public void Registry_TestEverything()
        {
            Registry myRegistry = Registry.Instance;
            Assert.IsNotNull(myRegistry);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            GameInfo g0 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10010"));
            myRegistry.ChangeGameStatus(g0.Id, GameInfo.GameStatus.AVAILABLE);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);

            GameInfo g1 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10012"));
            myRegistry.ChangeGameStatus(g1.Id, GameInfo.GameStatus.AVAILABLE);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);

            GameInfo g2 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10014"));
            myRegistry.ChangeGameStatus(g2.Id, GameInfo.GameStatus.COMPLETED);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);

            GameInfo g3 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10016"));
            myRegistry.ChangeGameStatus(g3.Id, GameInfo.GameStatus.DEAD);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);
            Assert.AreSame(g3, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[0]);

            GameInfo g4 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10018"));
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);
            Assert.AreSame(g3, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[0]);
            Assert.AreSame(g4, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED)[0]);

            GameInfo g5 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10020"));
            myRegistry.ChangeGameStatus(g5.Id, GameInfo.GameStatus.RUNNING);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);
            Assert.AreSame(g3, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[0]);
            Assert.AreSame(g4, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED)[0]);
            Assert.AreSame(g5, myRegistry.GetGames(GameInfo.GameStatus.RUNNING)[0]);

            Registry.Instance.ChangeGameStatus(g0.Id, GameInfo.GameStatus.COMPLETED);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[1]);
            Assert.AreSame(g3, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[0]);
            Assert.AreSame(g4, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED)[0]);
            Assert.AreSame(g5, myRegistry.GetGames(GameInfo.GameStatus.RUNNING)[0]);

            Registry.Instance.ChangeGameStatus(g1.Id, GameInfo.GameStatus.DEAD);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[0]);
            Assert.AreSame(g2, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED)[1]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[0]);
            Assert.AreSame(g3, myRegistry.GetGames(GameInfo.GameStatus.DEAD)[1]);
            Assert.AreSame(g4, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED)[0]);
            Assert.AreSame(g5, myRegistry.GetGames(GameInfo.GameStatus.RUNNING)[0]);

            Thread.Sleep(90000);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            g0 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10010"));
            myRegistry.ChangeGameStatus(g0.Id, GameInfo.GameStatus.AVAILABLE);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);

            g1 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10012"));
            myRegistry.ChangeGameStatus(g1.Id, GameInfo.GameStatus.AVAILABLE);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
            Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
            Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);

            for (int i = 0; i < 90; i++)
            {
                Thread.Sleep(1000);
                myRegistry.AmAlive(g0.Id);
                myRegistry.AmAlive(g1.Id);
                Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
                Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
                Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
                Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
                Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);
                Assert.AreSame(g0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[0]);
                Assert.AreSame(g1, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE)[1]);
            }

            myRegistry.SaveToFile("testGames.csv");

            Registry.TakeDown();
            myRegistry = Registry.Instance;
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            myRegistry.LoadFromFile("testGames.csv");
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            g2 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10014"));
            myRegistry.ChangeGameStatus(g2.Id, GameInfo.GameStatus.COMPLETED);
            g3 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10016"));
            myRegistry.ChangeGameStatus(g3.Id, GameInfo.GameStatus.DEAD);
            g4 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10018"));
            g5 = myRegistry.RegisterGame("Game0", new EndPoint("123.129.7.14:10020"));
            myRegistry.ChangeGameStatus(g5.Id, GameInfo.GameStatus.RUNNING);
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            myRegistry.Save();

            Registry.TakeDown();
            myRegistry = Registry.Instance;
            myRegistry.LoadFromFile("testGames.csv");
            Assert.AreEqual(2, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(1, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

            Thread.Sleep(90000);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.AVAILABLE).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.COMPLETED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.DEAD).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED).Count);
            Assert.AreEqual(0, myRegistry.GetGames(GameInfo.GameStatus.RUNNING).Count);

        }


    }
}
