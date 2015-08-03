
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.text.ParseException;



/**
 *
 * A complete Java class that demonstrates how to use the Socket
 * class, specifically how to open a socket, write to the socket,
 * and read from the socket.
 *
 * @author alvin alexander, devdaily.com.
 *
 */
public class SimpleSocketClient
{

  // call our constructor to start the program
  public static void main(String[] args)
  {
    new SimpleSocketClient();
  }
  
  public SimpleSocketClient()
  {
    String testServerName = "127.0.0.1";
    int port = 10002;
    Socket socket=null;
    try
    {
    	
      // open a socket
       socket = openSocket(testServerName, port);
       BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
      String msg="00670800822000010000000004000000000000000426003816000164101000000002001";
      String result = writeToAndReadFromSocket(socket, msg);
      System.out.println(result);
      socket.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }finally{
    	try{
    		socket.close();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    }
  }
  
  private String writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception
  {
    try 
    {
      // write text to the socket
//      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//      bufferedWriter.write(writeTo);
//      bufferedWriter.flush();
      socket.getOutputStream().write(writeTo.getBytes());
      socket.getOutputStream().flush();
      byte[] lenbuf = new byte[4];
      socket.getInputStream().read(lenbuf);
      int size = Integer.parseInt(new String(lenbuf));
      System.out.println(size);
//		int size = ((lenbuf[0] & 0xff) << 8) | (lenbuf[1] & 0xff);
//		System.out.println(size);
		byte[] buf = new byte[size];
////		//We're not expecting ETX in this case
		if (socket.getInputStream().read(buf) == size) {
			System.out.println((new String(lenbuf))+(new String(buf)));
		} 
		else
			System.out.println("chem");
      return null;
      // read text from the socket
//      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//      StringBuilder sb = new StringBuilder();
//      String str;
//      int nRead=0;
//      char[] cbuf=new char[9999];
//      char[] msgLength=new char[4];
//      nRead+=bufferedReader.read(cbuf,nRead,4);
//      System.arraycopy(cbuf, 0, msgLength, 0, 4);
//      int nToRead=Integer.valueOf(new String(msgLength));
//      char[] msgChar=new char[nToRead];
//      while (nRead<nToRead) 
//      {
//    	  nRead+=bufferedReader.read(cbuf,nRead,nToRead-nRead);
//      }
//      System.arraycopy(cbuf, 0, msgChar, 0, nToRead);
//      str=new String(msgChar);
//      // close the reader, and return the results as a String
//      bufferedReader.close();
//      return str;
//      //return sb.toString();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
      throw e;
    }
  }
  
  /**
   * Open a socket connection to the given server on the given port.
   * This method currently sets the socket timeout value to 10 seconds.
   * (A second version of this method could allow the user to specify this timeout.)
   */
  private Socket openSocket(String server, int port) throws Exception
  {
    Socket socket;
    
    // create a socket with a timeout
    try
    {
      InetAddress inteAddress = InetAddress.getByName(server);
      SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);
  
      // create a socket
      socket = new Socket();
  
      // this method will block no more than timeout ms.
      int timeoutInMs = 10*1000;   // 10 seconds
      socket.connect(socketAddress, timeoutInMs);
      socket.setSoTimeout(timeoutInMs);
      //socket.setKeepAlive(true);
      return socket;
    } 
    catch (SocketTimeoutException ste) 
    {
      System.err.println("Timed out waiting for the socket.");
      ste.printStackTrace();
      throw ste;
    }
  }

}