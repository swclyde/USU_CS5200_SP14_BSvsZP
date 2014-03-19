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
    [ServiceContract]
    public interface IRegistrar
    {
        [OperationContract]
        GameInfo RegisterGame(string label, Common.EndPoint publicEP);

        [OperationContract]
        GameInfo[] GetGames(GameInfo.GameStatus status = GameInfo.GameStatus.AVAILABLE);

        [OperationContract]
        void AmAlive(int gameId);

        [OperationContract]
        void ChangeStatus(int gameId, GameInfo.GameStatus status);
    }
}
