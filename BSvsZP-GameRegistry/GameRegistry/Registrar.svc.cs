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

        public void AmAlive(int gameId)
        {
            Registry.Instance.AmAlive(gameId);
        }

        public void ChangeStatus(int gameId, GameInfo.GameStatus status)
        {
            Registry.Instance.ChangeGameStatus(gameId, status);
        }
    }
}
