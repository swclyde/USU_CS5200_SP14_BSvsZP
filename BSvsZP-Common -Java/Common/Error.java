package Common;

import java.util.HashMap;
import java.util.Map;

import Common.DistributableObject.DISTRIBUTABLE_CLASS_IDS;

public class Error 
{
	private static Map<StandardErrorNumbers, Error> standardErrors;
	
	public enum StandardErrorNumbers
     {
         LocalProcessIdInMessageNumberMustBeZero(1001),
         LocalProcessIdInMessageNumberCannotBeZero(1002),
         LocalProcessIdInMessageNumberIsNotAnAgentId(1003),
         SendersEndPointDoesNotMatchAgentsEndPoint(1004),
         AgentCannotBeRemovedFromGame(1101),
         AgentIsAlreadyPartOfGame(1201),
         JoinRequestIsNotForCurrentGame(1202),
         JoinRequestIsOnlyValidForAvailableGames(1203),
         JoinRequestIsIncomplete(1204),
         AgentCannotBeAddedToGame(1205),
         InvalidResourceType(1301);
         
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
        err1.Number = StandardErrorNumbers.LocalProcessIdInMessageNumberMustBeZero;
        err1.Message = "Local process Id in message number must be zero";
    	standardErrors.put(StandardErrorNumbers.LocalProcessIdInMessageNumberMustBeZero,err1);
        
        
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
