using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

using Common;

namespace GameRegistry
{
    /// <summary>
    /// Summary description for RegistrarAlt
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class RegistrarAlt : System.Web.Services.WebService
    {
        [WebMethod]
        public GameInfo RegisterGame(string label, Common.EndPoint publicEP)
        {
            return Registry.Instance.RegisterGame(label, publicEP);
        }

        [WebMethod]
        public GameInfo[] GetGames(GameInfo.GameStatus status = GameInfo.GameStatus.AVAILABLE)
        {
            return Registry.Instance.GetGames(status).ToArray();
        }

        [WebMethod]
        public GameInfoAlt[] GetGamesAlt(GameInfo.GameStatus status = GameInfo.GameStatus.AVAILABLE)
        {
            List<GameInfo> games = Registry.Instance.GetGames(status);
            GameInfoAlt[] results = new GameInfoAlt[games.Count];
            for (int i = 0; i < games.Count; i++)
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

        [WebMethod]
        public void AmAlive(int gameId)
        {
            Registry.Instance.AmAlive(gameId);
        }

        [WebMethod]
        public void ChangeStatus(int gameId, GameInfo.GameStatus status)
        {
            Registry.Instance.ChangeGameStatus(gameId, status);
        }

    }
}
