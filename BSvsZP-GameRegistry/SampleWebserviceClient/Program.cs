using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

using SampleWebserviceClient.Registry;

namespace SampleWebserviceClient
{
    class Program
    {
        static void Main(string[] args)
        {
            RegistrarClient registry = new RegistrarClient();

            EndPoint myEP = new EndPoint() {Address = 1352522, Port=2354 };
            GameInfo myGame = registry.RegisterGame("Test Game",  myEP);

            GameInfo[] games = registry.GetGames(GameInfo.GameStatus.NOT_INITIAlIZED);

            registry.ChangeStatus(myGame.Id, GameInfo.GameStatus.AVAILABLE);
            games = registry.GetGames(GameInfo.GameStatus.AVAILABLE);

            registry.AmAlive(myGame.Id);
            games = registry.GetGames(GameInfo.GameStatus.AVAILABLE);

            Thread.Sleep(90000);
            games = registry.GetGames(GameInfo.GameStatus.AVAILABLE);
        }
    }
}
