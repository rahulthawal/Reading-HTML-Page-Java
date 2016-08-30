
package grabpage;


import java.net.*;
import java.io.*;

public class GrabPage {
    
  public GrabPage (String textURL) throws IOException {
    Socket socket = null;
    dissect (textURL);
   connect ();
    try {
      grab ();
    } finally {
      socket.close ();
    }
  }

  protected String host, file;
  protected int port;

  protected void dissect (String textURL) throws MalformedURLException {
    URL url = new URL (textURL);
    host = url.getHost ();
    port = url.getPort ();
    if (port == -1)
      port = 80;
    file = url.getFile();
  }

  protected DataInputStream in;
  protected DataOutputStream out;

  protected Socket connect () throws IOException {
    System.out.println ("Connecting to " + host + ":" + port + "..");
    Socket socket = new Socket (host, port);
    System.out.println ("Connected.");

    OutputStream rawOut = socket.getOutputStream ();
    InputStream rawIn = socket.getInputStream ();
    BufferedOutputStream buffOut = new BufferedOutputStream (rawOut);
    out = new DataOutputStream (buffOut);
    in = new DataInputStream (rawIn);

    return socket;
  }

  protected void grab () throws IOException {
    System.out.println ("Sending request..");
    out.writeBytes ("GET " + file + " HTTP/1.0\r\n\r\n");
    out.flush ();
    
    
    System.out.println ("Waiting for response..");
    String input;
    while ((input = in.readLine ()) != null)
      System.out.println (input);
  }

  public static void main (String args[]) throws IOException {
      
    DataInputStream kbd = new DataInputStream (System.in);
    while (true) {
      String textURL;
      System.out.print ("Enter a URL: ");
      System.out.flush ();
      if ((textURL = kbd.readLine ()) == null)
        break;

      try {
        new GrabPage (textURL);
      } catch (IOException ex) {
        ex.printStackTrace ();
        continue;
      }

      System.out.println ("- OK -");
    }
    System.out.println ("exit");
  }
}
