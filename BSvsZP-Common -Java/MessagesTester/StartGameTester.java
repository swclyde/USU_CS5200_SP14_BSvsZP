package MessagesTester;

import static org.junit.Assert.*;

import org.junit.Test;

import Messages.Request;
import Messages.StartGame;

public class StartGameTester {

	@Test
	public void test() {
		StartGame sgame = new StartGame();
		assertEquals(sgame.RequestType.getValue(), Request.PossibleTypes.StartGame.getValue());
	}

}
