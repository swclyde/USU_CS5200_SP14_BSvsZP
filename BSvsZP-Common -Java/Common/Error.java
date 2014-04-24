package Common;

import java.util.HashMap;
import java.util.Map;

import Common.DistributableObject.DISTRIBUTABLE_CLASS_IDS;

public class Error 
{
	private static Map<StandardErrorNumbers, Error> standardErrors;
	
	public enum StandardErrorNumbers
     {
		NullEnvelopeOrMessage(1000),
	    InvalidTypeOfMessage(1001),
	    LocalProcessIdInMessageNumberCannotBeZero(1002),
	    LocalProcessIdInMessageNumberIsNotAnAgentId(1003),
	    SendersEndPointDoesNotMatchAgentsEndPoint(1004),
	    AgentNotInGame(1005),
	    NoContext(1006),
	    MessageNotFromGameServer(1007),
	    MessageNotFromReferee(1008),
	    AgentCannotBeRemovedFromGame(1101),
	    AgentIsAlreadyPartOfGame(1201),
	    JoinRequestIsNotForCurrentGame(1202),
	    JoinRequestIsOnlyValidForAvailableGames(1203),
	    JoinRequestIsIncomplete(1204),
	    AgentCannotBeAddedToGame(1205),
	    InvalidResourceType(1301),
	    StartProtocolFailed(1401),
	    SomeAgentsDidNotRespondToStartGameRequest(1402),
	    SomeAgentsNotReadyToStartGame(1403),
	    AttackingAgentIsNotAZombie(1500),
	    TargetAgentIsInvalid(1501),
	    AttackingAgentIsNotABrilliantStudent(1502),
	    AttackingAgentTooFarFromTarget(1503),
	    MovingAgentIsInvalid(1603),
	    MoveToLocationIsInvalid(1604),
	    MoveDistanceIsTooGreat(1605),
	    InvalidTypeOfAgent(1701),
	    InvalidTick(2000);
        
         private int value;
         
         StandardErrorNumbers(int value)
         {
        	 this.value = value;
         }
         
         public static StandardErrorNumbers getFromInt(int i)
         {
        	 StandardErrorNumbers temp = null;
        	 for(StandardErrorNumbers sen : StandardErrorNumbers.values())
        		 if (sen.value == i)
        			 temp = sen;
        	 return temp;
         }
         
         public int getValue()
         {
         	return this.value;
         }
         
         public int getIntValueFromEnum() 
         {
        	 return getValue();
         }
             
         
         
     }
	 
	private StandardErrorNumbers Number; 
    private String Message;
	
    
    public Error()
    {
    	standardErrors = new HashMap<StandardErrorNumbers, Error>();
    	
    	Error err1 = new Error();
        err1.Number = StandardErrorNumbers.NullEnvelopeOrMessage;
        err1.Message = "Empty Envelope or Message";
    	standardErrors.put(StandardErrorNumbers.NullEnvelopeOrMessage,err1);
        
        
        Error err2 = new Error();
        err2.Number =  StandardErrorNumbers.LocalProcessIdInMessageNumberCannotBeZero;
        err2.Message = "Local process Id in message number cannot be zero";
        standardErrors.put(StandardErrorNumbers.LocalProcessIdInMessageNumberCannotBeZero, err2);
        
        Error err3 = new Error();
        err3.Number =  StandardErrorNumbers.LocalProcessIdInMessageNumberIsNotAnAgentId;
        err3.Message = "Local process Id in message number is not an agent Id";
        standardErrors.put(StandardErrorNumbers.LocalProcessIdInMessageNumberIsNotAnAgentId, err3);
        
        Error err4 = new Error();
        err4.Number =  StandardErrorNumbers.SendersEndPointDoesNotMatchAgentsEndPoint;
        err4.Message = "Senders end point does not match agents end point";
        standardErrors.put(StandardErrorNumbers.SendersEndPointDoesNotMatchAgentsEndPoint, err4);
        
        Error err5 = new Error();
        err5.Number =  StandardErrorNumbers.AgentCannotBeRemovedFromGame;
        err5.Message = "Agent cannot be removed from game";
        standardErrors.put(StandardErrorNumbers.AgentCannotBeRemovedFromGame, err5 );
        
        Error err6 = new Error();
        err6.Number =  StandardErrorNumbers.AgentIsAlreadyPartOfGame;
        err6.Message = "Agent is already part of this game";
        standardErrors.put(StandardErrorNumbers.AgentIsAlreadyPartOfGame, err6);
        
        Error err7 = new Error();
        err7.Number =  StandardErrorNumbers.JoinRequestIsNotForCurrentGame;
        err7.Message =  "Join request is not for the current game, i.e., wrong game Id";
        standardErrors.put(StandardErrorNumbers.JoinRequestIsNotForCurrentGame, err7);
        
        Error err8 = new Error();
        err8.Number =  StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames;
        err8.Message =  "Join request is only valid for AVAILABLE games";
        standardErrors.put(StandardErrorNumbers.JoinRequestIsOnlyValidForAvailableGames, err8);
        
        Error err9 = new Error();
        err9.Number =   StandardErrorNumbers.JoinRequestIsIncomplete;
        err9.Message =  "Join request is incomplete";
        standardErrors.put(StandardErrorNumbers.JoinRequestIsIncomplete, err9);
        
        Error err10 = new Error();
        err10.Number =   StandardErrorNumbers.AgentCannotBeAddedToGame;
        err10.Message =  "Agent cannot be added to game";
        standardErrors.put(StandardErrorNumbers.AgentCannotBeAddedToGame, err10);
        
        Error err11 = new Error();
        err11.Number =   StandardErrorNumbers.InvalidResourceType;
        err11.Message =  "Invalid Resource Type";
        standardErrors.put(StandardErrorNumbers.InvalidResourceType, err11);
        
        Error err12 = new Error();
        err12.Number= StandardErrorNumbers.InvalidTick;
        err12.Message = "Invalid Tick";
        standardErrors.put(StandardErrorNumbers.InvalidTick, err12);
        
        Error err13 = new Error();
        err13.Number= StandardErrorNumbers.InvalidTypeOfAgent;
        err13.Message = "Invalid Type of Agent";
        standardErrors.put(StandardErrorNumbers.InvalidTypeOfAgent, err13);
        
        Error err14 = new Error();
        err14.Number= StandardErrorNumbers.MoveDistanceIsTooGreat;
        err14.Message = "The move distance is too great relative to agent's speed";
        standardErrors.put(StandardErrorNumbers.MoveDistanceIsTooGreat, err14);
        
        Error err15 = new Error();
        err15.Number= StandardErrorNumbers.MoveToLocationIsInvalid;
        err15.Message = "The Move-to Location is Invalid";
        standardErrors.put(StandardErrorNumbers.MoveToLocationIsInvalid, err15);
        
        Error err16 = new Error();
        err16.Number= StandardErrorNumbers.MovingAgentIsInvalid;
        err16.Message = "The moving agent is invalid.  Only a brilliant students and zombie professors can send a move message";
        standardErrors.put(StandardErrorNumbers.MovingAgentIsInvalid, err16);
        
        Error err17 = new Error();
        err17.Number= StandardErrorNumbers.AttackingAgentTooFarFromTarget;
        err17.Message = "The attaching agent is too far from its target";
        standardErrors.put(StandardErrorNumbers.AttackingAgentTooFarFromTarget, err17);
        
        Error err18 = new Error();
        err18.Number= StandardErrorNumbers.AttackingAgentIsNotABrilliantStudent;
        err18.Message = "The attacking agent is not a brilliant student.  Only a brilliant student can send this kind of message";
        standardErrors.put(StandardErrorNumbers.AttackingAgentIsNotABrilliantStudent, err18);
        
        Error err19 = new Error();
        err19.Number= StandardErrorNumbers.TargetAgentIsInvalid;
        err19.Message = "The target agent is either not in the game or the wrong kind of agent";
        standardErrors.put(StandardErrorNumbers.TargetAgentIsInvalid, err19);
        
        Error err20 = new Error();
        err20.Number= StandardErrorNumbers.AttackingAgentIsNotAZombie;
        err20.Message = "The attacking agent is not a zombie.  Only a zombie can send this kind of message";
        standardErrors.put(StandardErrorNumbers.AttackingAgentIsNotAZombie, err20);
        
        Error err21 = new Error();
        err21.Number= StandardErrorNumbers.SomeAgentsNotReadyToStartGame;
        err21.Message = "Some agents not ready to start game - go back to waiting for a start request";
        standardErrors.put(StandardErrorNumbers.SomeAgentsNotReadyToStartGame, err21);
        
        Error err22 = new Error();
        err22.Number= StandardErrorNumbers.SomeAgentsDidNotRespondToStartGameRequest;
        err22.Message = "Some agents did not respond to start game request - go back to waiting for a start request";
        standardErrors.put(StandardErrorNumbers.SomeAgentsDidNotRespondToStartGameRequest, err22);
        
        Error err23 = new Error();
        err23.Number= StandardErrorNumbers.StartProtocolFailed;
        err23.Message = "Start Protocol Failed - game is shutting done";
        standardErrors.put(StandardErrorNumbers.StartProtocolFailed, err23);
        
        Error err24 = new Error();
        err24.Number= StandardErrorNumbers.MessageNotFromReferee;
        err24.Message = "The message received is not from the Referee, as expected.  Check logs for EP information.";
        standardErrors.put(StandardErrorNumbers.MessageNotFromReferee, err24);
        
        Error err25 = new Error();
        err25.Number= StandardErrorNumbers.MessageNotFromGameServer;
        err25.Message = "The message received is not from the Game Server, as expected.  Check logs for EP information.";
        standardErrors.put(StandardErrorNumbers.MessageNotFromGameServer, err25);
        
        Error err26 = new Error();
        err26.Number= StandardErrorNumbers.NoContext;
        err26.Message = "No context provided to the conversation strategy.  Check logs that is trying to execute the strategy.";
        standardErrors.put(StandardErrorNumbers.NoContext, err26);
        
        Error err27 = new Error();
        err27.Number= StandardErrorNumbers.AgentNotInGame;
        err27.Message = "The agent is not in the specified game. Check the message content in the logs.";
        standardErrors.put(StandardErrorNumbers.AgentNotInGame, err27);
        
        Error err28 = new Error();
        err28.Number= StandardErrorNumbers.InvalidTypeOfMessage;
        err28.Message = "Invalid Type of Message";
        standardErrors.put(StandardErrorNumbers.InvalidTypeOfMessage, err28);
        
      
     
    }
    
    
     public StandardErrorNumbers getNumber() {
	 	return Number;
	 }
	 
     public void setNumber(StandardErrorNumbers number) {
	 	Number = number;
	 }
	 
     public String getMessage() {
		return Message;
	 }
	 
     public void setMessage(String message) {
		Message = message;
	 }
     
	 public static Error Get(StandardErrorNumbers index)
     {
         return standardErrors.get(index);
     }

}
