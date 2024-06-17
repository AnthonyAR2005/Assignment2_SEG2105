//**** Changed for E50 AAR
import java.io.*;
import client.ChatClient;
import common.*;
import ocsf.client.*;

public class ServerConsole implements ChatIF {

		@Override
		public void display(String message) {
		    System.out.println("SERVER MSG> " + message);
			
		}
}
