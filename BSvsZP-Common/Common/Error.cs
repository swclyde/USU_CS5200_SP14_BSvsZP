using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common
{
    public class Error
    {
        private static Dictionary<StandardErrorNumbers, Error> standardErrors;

        public enum StandardErrorNumbers
        {
            LocalProcessIdInMessageNumberMustBeZero = 1001,
            LocalProcessIdInMessageNumberCannotBeZero = 1002,
            LocalProcessIdInMessageNumberIsNotAnAgentId = 1003,
            SendersEndPointDoesNotMatchAgentsEndPoint = 1004,
            AgentCannotBeRemovedFromGame = 1101,
            AgentIsAlreadyPartOfGame = 1201,
            JoinRequestIsNotForCurrentGame = 1202,
            JoinRequestIsOnlyValidForAvailableGames = 1203,
            JoinRequestIsIncomplete = 1204,
            AgentCannotBeAddedToGame = 1205,
            InvalidResourceType = 1301
        }

        public StandardErrorNumbers Number { get; set; }
        public string Message { get; set; }

        static Error()
        {
            standardErrors = new Dictionary<StandardErrorNumbers, Error>();
            standardErrors.Add(StandardErrorNumbers.LocalProcessIdInMessageNumberMustBeZero,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.LocalProcessIdInMessageNumberMustBeZero,
                                    Message = "Local process Id in message number must be zero"
                                });
            standardErrors.Add(StandardErrorNumbers.LocalProcessIdInMessageNumberCannotBeZero,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.LocalProcessIdInMessageNumberCannotBeZero,
                                    Message = "Local process Id in message number cannot be zero"
                                });
            standardErrors.Add(StandardErrorNumbers.LocalProcessIdInMessageNumberIsNotAnAgentId,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.LocalProcessIdInMessageNumberIsNotAnAgentId,
                                    Message = "Local process Id in message number is not an agent Id"
                                });
            standardErrors.Add(StandardErrorNumbers.SendersEndPointDoesNotMatchAgentsEndPoint,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.SendersEndPointDoesNotMatchAgentsEndPoint,
                                    Message = "Senders end point does not match agents end point"
                                });
            standardErrors.Add(StandardErrorNumbers.AgentCannotBeRemovedFromGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentCannotBeRemovedFromGame,
                                    Message = "Agent cannot be removed from game"
                                });
            standardErrors.Add(StandardErrorNumbers.AgentIsAlreadyPartOfGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentIsAlreadyPartOfGame,
                                    Message = "Agent is already part of this game"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsNotForCurrentGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsNotForCurrentGame,
                                    Message = "Join request is not for the current game, i.e., wrong game Id"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames,
                                    Message = "Join request is only valid for AVAILABLE games"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsIncomplete,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsIncomplete,
                                    Message = "Join request is incomplete"
                                });
            standardErrors.Add(StandardErrorNumbers.AgentCannotBeAddedToGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentCannotBeAddedToGame,
                                    Message = "Agent cannot be added to game"
                                });
            standardErrors.Add(StandardErrorNumbers.InvalidResourceType,
                    new Error()
                    {
                        Number = StandardErrorNumbers.InvalidResourceType,
                        Message = "Invalid Resource Type"
                    });
        }

        public static Error Get(StandardErrorNumbers index)
        {
            return standardErrors[index];
        }

    }
}

