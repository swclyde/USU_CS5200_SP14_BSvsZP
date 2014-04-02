package CommonTester;

import static org.junit.Assert.*;

import org.junit.Test;

import Common.ByteList;
import Common.EndPoint;
import Common.GameInfo;

public class GameInfoTester {

	@Test
	public void test_Constructor() throws Exception {
		EndPoint ep = new EndPoint("129.123.7.53:1001");
		GameInfo gameInfo = new GameInfo((short) 10, "NawafGame", ep);
		assertEquals(10, gameInfo.getId());
		assertEquals("NawafGame", gameInfo.getLabel());
		assertEquals(ep, gameInfo.getCommunicationEndPoint());
		
		GameInfo gameInfo2 = new GameInfo((short) 20, "NewGame", ep, GameInfo.GameStatus.AVAILABLE);
		assertEquals(20, gameInfo2.getId());
		assertEquals("NewGame", gameInfo2.getLabel());
		assertEquals(ep, gameInfo2.getCommunicationEndPoint());
		assertEquals(GameInfo.GameStatus.AVAILABLE, gameInfo2.Status.AVAILABLE);
		
	}

	@Test
	public void test_DecodeEncode() throws Exception {
		EndPoint ep = new EndPoint("129.123.7.53:1001");
		GameInfo gameInfo = new GameInfo((short) 10, "NawafGame", ep);
		ByteList bytes = new ByteList();
		gameInfo.Encode(bytes);
		assertEquals(10, gameInfo.getId());
		assertEquals("NawafGame", gameInfo.getLabel());
		assertEquals(ep, gameInfo.getCommunicationEndPoint());
	}
	
}
