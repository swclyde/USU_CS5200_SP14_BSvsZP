package MessagesTester;

import static org.junit.Assert.*;

import org.junit.Test;

import Common.ByteList;
import Messages.EndGame;
import Messages.Request;

public class EndGameTester {

	@Test
	public void EndGame_TestEverything() throws Exception {
		EndGame eg = new EndGame((short) 10);
		eg.setRequestType(Request.PossibleTypes.EndGame);
		
		ByteList bytes = new ByteList();
		eg.Encode(bytes);
		EndGame eg2 = EndGame.Create(bytes);
		
		assertEquals(10, eg.getGameId());
		assertEquals(Request.PossibleTypes.EndGame.getValue(), eg.getRequestType().getValue());
		
		assertEquals(eg.getGameId(), eg2.getGameId());
		assertEquals(Request.PossibleTypes.EndGame.getValue(), eg2.getRequestType().getValue());
	}

}
