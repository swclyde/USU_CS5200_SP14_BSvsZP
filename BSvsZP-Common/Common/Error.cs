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
            NullEnvelopeOrMessage = 1000,
            InvalidTypeOfMessage = 1001,
            LocalProcessIdInMessageNumberCannotBeZero = 1002,
            LocalProcessIdInMessageNumberIsNotAnAgentId = 1003,
            SendersEndPointDoesNotMatchAgentsEndPoint = 1004,
            AgentNotInGame = 1005,
            NoContext = 1006,
            MessageNotFromGameServer = 1007,
            MessageNotFromReferee = 1008,
            AgentCannotBeRemovedFromGame = 1101,
            AgentIsAlreadyPartOfGame = 1201,
            JoinRequestIsNotForCurrentGame = 1202,
            JoinRequestIsOnlyValidForAvailableGames = 1203,
            JoinRequestIsIncomplete = 1204,
            AgentCannotBeAddedToGame = 1205,
            InvalidResourceType = 1301,
            StartProtocolFailed = 1401,
            SomeAgentsDidNotRespondToStartGameRequest = 1402,
            SomeAgentsNotReadyToStartGame = 1403,
            AttackingAgentIsNotAZombie = 1500,
            TargetAgentIsInvalid = 1501,
            AttackingAgentIsNotABrilliantStudent = 1502,
            AttackingAgentTooFarFromTarget = 1503,
            MovingAgentIsInvalid = 1603,
            InvalidTypeOfAgent = 1701,
            InvalidTick = 2000
        }

        public StandardErrorNumbers Number { get; set; }
        public string Message { get; set; }

        static Error()
        {
            standardErrors = new Dictionary<StandardErrorNumbers, Error>();
            standardErrors.Add(StandardErrorNumbers.NullEnvelopeOrMessage,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.NullEnvelopeOrMessage,
                                    Message = "Empty Envelope or Message"
                                });
            standardErrors.Add(StandardErrorNumbers.InvalidTypeOfMessage,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.InvalidTypeOfMessage,
                                    Message = "Invalid Type of Message"
                                });
            standardErrors.Add(StandardErrorNumbers.LocalProcessIdInMessageNumberCannotBeZero,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.InvalidTypeOfMessage,
                                    Message = "Invalid Type of Message"
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
            standardErrors.Add(StandardErrorNumbers.AgentNotInGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentNotInGame,
                                    Message = "The agent is not in the specified game. Check the message content in the logs."
                                });
            standardErrors.Add(StandardErrorNumbers.NoContext,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.NoContext,
                                    Message = "No context provided to the conversation strategy.  Check logs that is trying to execute the strategy."
                                });
            standardErrors.Add(StandardErrorNumbers.MessageNotFromGameServer,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.MessageNotFromGameServer,
                                    Message = "The message received is not from the Game Server, as expected.  Check logs for EP information."
                                });
            standardErrors.Add(StandardErrorNumbers.MessageNotFromReferee,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.MessageNotFromReferee,
                                    Message = "The message received is not from the Referee, as expected.  Check logs for EP information."
                                });
            standardErrors.Add(StandardErrorNumbers.AgentCannotBeRemovedFromGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentCannotBeRemovedFromGame,
                                    Message = "Agent cannot be removed from game"
                                });

            // Join Game protocol
            standardErrors.Add(StandardErrorNumbers.AgentIsAlreadyPartOfGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentIsAlreadyPartOfGame,
                                    Message = "Agent is already part of this game -- no action needed"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsNotForCurrentGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsNotForCurrentGame,
                                    Message = "Join request is not for the current game, i.e., wrong game Id -- try joining another game"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames,
                                    Message = "Join request is only valid for AVAILABLE games -- try joining another game"
                                });
            standardErrors.Add(StandardErrorNumbers.JoinRequestIsIncomplete,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.JoinRequestIsIncomplete,
                                    Message = "Join request is incomplete -- check request for missing information like A# or names"
                                });
            standardErrors.Add(StandardErrorNumbers.AgentCannotBeAddedToGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AgentCannotBeAddedToGame,
                                    Message = "Agent cannot be added to game -- look at GameServer log for more details"
                                });

            // Get-resouce related protocols
            standardErrors.Add(StandardErrorNumbers.InvalidResourceType,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.InvalidResourceType,
                                    Message = "Invalid Resource Type -- be sure the resource type is one the receiver of your request can handle"
                                });

            // Start protocol
            standardErrors.Add(StandardErrorNumbers.StartProtocolFailed,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.StartProtocolFailed,
                                    Message = "Start Protocol Failed - game is shutting done"
                                });
            standardErrors.Add(StandardErrorNumbers.SomeAgentsDidNotRespondToStartGameRequest,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.SomeAgentsDidNotRespondToStartGameRequest,
                                    Message = "Some agents did not respond to start game request - go back to waiting for a start request"
                                });
            standardErrors.Add(StandardErrorNumbers.SomeAgentsNotReadyToStartGame,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.SomeAgentsNotReadyToStartGame,
                                    Message = "Some agents not ready to start game - go back to waiting for a start request"
                                });
            standardErrors.Add(StandardErrorNumbers.AttackingAgentIsNotAZombie,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AttackingAgentIsNotAZombie,
                                    Message = "The attacking agent is not a zombie.  Only a zombie can send this kind of message"
                                });
            standardErrors.Add(StandardErrorNumbers.TargetAgentIsInvalid,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.TargetAgentIsInvalid,
                                    Message = "The target agent is either not in the game or the wrong kind of agent"
                                });
            standardErrors.Add(StandardErrorNumbers.AttackingAgentIsNotABrilliantStudent,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AttackingAgentIsNotABrilliantStudent,
                                    Message = "The attacking agent is not a brilliant student.  Only a brilliant student can send this kind of message"
                                });
            standardErrors.Add(StandardErrorNumbers.MovingAgentIsInvalid,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.MovingAgentIsInvalid,
                                    Message = "The moving agent is invalid.  Only a brilliant students and zombie professors can send a move message"
                                });
            standardErrors.Add(StandardErrorNumbers.InvalidTick,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.InvalidTick,
                                    Message = "Invalid Tick.  It may have been used before or it may not one intended for the agent trying to use it"
                                });
            standardErrors.Add(StandardErrorNumbers.AttackingAgentTooFarFromTarget,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.AttackingAgentTooFarFromTarget,
                                    Message = "The attaching agent is too far from its target"
                                });
            standardErrors.Add(StandardErrorNumbers.InvalidTypeOfAgent,
                                new Error()
                                {
                                    Number = StandardErrorNumbers.InvalidTypeOfAgent,
                                    Message = "Invalid Type of Agent"
                                });
        }

        public static Error Get(StandardErrorNumbers index)
        {
            return standardErrors[index];
        }

    }
}

