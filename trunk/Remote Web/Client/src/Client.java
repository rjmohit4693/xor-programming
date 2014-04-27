import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;
import javax.swing.JOptionPane;

// -------------------------------------------------------------------------
/**
 * A program intended for pranking. The client should be run on target's computer and will open up webpages at the
 * server's request. Note that exceptions are usually ignored and will cause the program to silently close. It will also
 * close if it receives the "exit" request from the server.
 *
 * @author Steven Roberts
 * @version 1.2.0
 */
public class Client
{
    private static final int    PORT = 2573;
    /**
     * Command to close client
     */
    private static final String EXIT = "exit";


    // ----------------------------------------------------------
    /**
     * Runs the client by getting a valid ip from the user and opening up a socket to receive input.
     *
     * @param args
     *            The command line arguments
     */
    public static void main(String[] args)
    {
        Desktop desktop = getDesktop();

        if (desktop == null) // ensure a webpage can be opened by Java
        {
            JOptionPane.showMessageDialog(null, "Unable to open browser", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ip = null;
        Socket socket;
        while (true) // loop until a valid socket is opened
        {
            ip = JOptionPane.showInputDialog("Enter the server IP", ip);
            if (ip == null)
            {
                return;
            }
            try
            {
                socket = new Socket(ip, PORT);
                break;
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        try (InputStream input = socket.getInputStream(); Scanner scan = new Scanner(input))
        {
            // read socket input
            while (scan.hasNextLine())
            {
                String url = scan.nextLine();
                if (!url.isEmpty())
                {
                    if (url.equalsIgnoreCase(EXIT))
                    {
                        return;
                    }

                    // open the webpage
                    browse(desktop, url);
                }
            }
        }
        catch (Exception e)
        {
            // silently stop the program if there is an error
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                // Ignore
            }
        }
    }


    private static Desktop getDesktop()
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE))
            {
                return desktop;
            }
        }
        return null;
    }


    private static void browse(Desktop desktop, String url)
    {
        try
        {
            desktop.browse(new URI(url));
        }
        catch (Exception e)
        {
            // Do nothing if the webpage fails to open
        }
    }
}
