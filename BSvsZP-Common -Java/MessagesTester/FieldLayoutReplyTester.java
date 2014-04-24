package MessagesTester;

import static org.junit.Assert.*;
import java.io.NotActiveException;
import java.net.UnknownHostException;
import org.junit.Test;
import Common.ByteList;
import Common.PlayingFieldLayout;
import Messages.Message;
import Messages.PlayingFieldReply;
import Messages.Reply;
public class FieldLayoutReplyTester {

	@Test
	public void FieldLayoutReply_Everything() throws NotActiveException, UnknownHostException, Exception
        {
            PlayingFieldLayout pfl = new PlayingFieldLayout((short)100, (short)120);
            assertEquals(100, pfl.getWidth());
            assertEquals(120, pfl.getHeight());
            assertNotNull(pfl.getSidewalkSquares());    

            PlayingFieldReply r1 = new PlayingFieldReply(Reply.PossibleStatus.Success, pfl, "Test");

            assertEquals(Reply.PossibleStatus.Success, r1.Status);
            assertNotNull(r1.Layout);
            assertSame(pfl, r1.Layout);

            ByteList bytes = new ByteList();
            r1.Encode(bytes);
            Message msg = Message.Create(bytes);
           
            assertNotNull(msg);
            
            PlayingFieldReply r2 = (PlayingFieldReply) msg;
            assertEquals(Reply.PossibleStatus.Success, r2.Status);

            assertEquals(pfl.getHeight(), r2.Layout.getHeight());
            assertEquals(pfl.getWidth(), r2.Layout.getWidth());
        }
}
