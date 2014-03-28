using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

using Common;

namespace GameRegistry
{
    /// <summary>
    /// Registrar for registering games, looking up games, etc., implemented using WCF Webservices.
    /// </summary>
    public class Registrar : IRegistrar
    {
        public GameInfo RegisterGame(string label, Common.EndPoint publicEP)
        {
            return Registry.Instance.RegisterGame(label, publicEP);
        }

        public GameInfo[] GetGames(GameInfo.GameStatus status = GameInfo.GameStatus.AVAILABLE)
        {
            return Registry.Instance.GetGames(status).ToArray();
        }

        public GameInfoAlt[] GetGamesAlt(GameInfo.GameStatus status = GameInfo.GameStatus.AVAILABLE)
        {
            List<GameInfo> games = Registry.Instance.GetGames(status);
            GameInfoAlt[] results = new GameInfoAlt[games.Count];
            for (int i=0; i<games.Count; i++)
            {
                results[i] = new GameInfoAlt()
                                    {
                                        Id = games[i].Id,
                                        CommunicationEndPoint = games[i].CommunicationEndPoint.ToString(),
                                        Status = games[i].Status.ToString(),
                                        AliveTimestamp = games[i].AliveTimestamp.ToString(),
                                        Label = games[i].Label
                                    };

            }

            return results;
        }

        public void AmAlive(int gameId)
        {
            Registry.Instance.AmAlive(gameId);
        }

        public void ChangeStatus(int gameId, GameInfo.GameStatus status)
        {
            Registry.Instance.ChangeGameStatus(gameId, status);
        }

        public string EndPointReflector()
        {
            System.Net.IPEndPoint reflectorEP = GameRegistry.EndPointReflector.Instance.EndPoint;
            string reflectorHost = OperationContext.Current.Host.BaseAddresses[0].Host;
            return string.Format("{0}:{1}", reflectorHost, reflectorEP.Port);
        }
    }
}
