package JavaSerialization;

import Common.AgentInfo;
import Common.AgentInfo.PossibleAgentType;
import Common.ByteList;
import Common.FieldLocation;
import Common.MessageNumber;
import Messages.JoinGame;

public class TestJoinMessage 
{
	public static void main(String [] args) throws Exception
	{
	        AgentInfo agent1 = new AgentInfo();
			agent1.setId((short) 10);
			agent1.setAgentStatus(AgentInfo.PossibleAgentStatus.InGame);
			agent1.setANumber("A00001");
			agent1.setFirstName("Joe");
			agent1.setLastName("Jones");
			FieldLocation location = new FieldLocation((short) 10, (short) 20);
			
			agent1.setLocation(location);
			agent1.setPoints((Double)100.0);
			agent1.setStrength((Double)200.0);
			agent1.setSpeed((Double)1.2);
	        agent1.setAgentType(PossibleAgentType.BrilliantStudent); // to solve when PossibleAgentType is null
	        JoinGame jg = new JoinGame((short) 20, agent1);
	        
	        MessageNumber mNumber = MessageNumber.Create((short)100, (short)120);
	        MessageNumber cId = MessageNumber.Create((short)200, (short)240);
	        jg.setMessageNr(mNumber);
	        jg.setConversationId(cId);
	        
	        ByteList bytes = new ByteList();
	        
	        jg.Encode(bytes);
	        
	        byte[] b = bytes.GetBytes(bytes.getLength());
	        for(int i =0; i<b.length; i++)
	            System.out.println("b[" + i + "] = " + b[i] + " ");
	   
	}
}
