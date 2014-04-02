package JavaSerialization;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.*;
import Messages.*;

public class TestAckNakMessage {
	public static void main(String[] args) throws NotActiveException, UnknownHostException, Exception 
	{
		DistributableObject disO = new DistributableObject();
     
        AckNak ack = new AckNak(Reply.PossibleStatus.Success, 20, disO, "AckNak Message", "Note to AckNak message");
        ByteList ackBytes = new ByteList();
        ack.Encode(ackBytes);
        
        printAll(ackBytes);
	}
	public static void printAll(ByteList bytes) throws ApplicationException
	{
		  byte[] b = bytes.GetBytes(bytes.getLength());
	        for(int i =0; i<b.length; ++i)
	            System.out.print(b[i]);
	        System.out.println();
	}

}
