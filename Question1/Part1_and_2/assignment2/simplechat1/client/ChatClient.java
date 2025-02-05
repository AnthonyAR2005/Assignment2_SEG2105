// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	//**** Changed for E50 AAR
	// Checks if the message is a command if it is then it executes it, else it sends the message
	String array[] = message.split(" ");
			
	if (message.startsWith("#")) {
		if (message.equals("#quit")) {
			try {
				System.out.println("Quitting the sevrer.");
				closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
			
		} else if (message.equals("#logoff")) {
			try {
				System.out.println("Logging off.");
				closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else if (array[0].equals("#sethost")) {
			if (!isConnected()) {
				System.out.println("Setting host to: " + array[1]);
				setHost(array[1]);
			} else {
				System.out.println("Error, client must be logged off.");
			}
		} else if (array[0].equals("#setport")) {
			if (!isConnected()) {
				System.out.println("Setting port to: " + array[1]);
				setPort(Integer.valueOf(array[1]));
			} else {
				System.out.println("Error, client must be logged off.");
			}
		} else if (message.equals("#login")) {
			if (!isConnected()) {
				try {
					openConnection();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Error, client must be logged off.");
			}
		} else if (message.equals("#gethost")) {
			System.out.println("The host is " + getHost());
		} else if (message.equals("#getport")) {
			System.out.println("The port is " + getPort());
		} else {
			System.out.println("That is not a valid command.");
		}
	} else {
	    try
	    {
	      sendToServer(message);
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }	
	}
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  //**** Changed for E49 AAR
  @Override
  protected void connectionException(Exception exception){
	  System.out.println("The server has shut down, and quitting");
      quit();
  }
  
}
//End of ChatClient class
