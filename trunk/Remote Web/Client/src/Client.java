import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Client
{
    private static final int PORT = 2573;


    public static void main(String[] args)
    {
        Desktop desktop;

        if (!Desktop.isDesktopSupported() || !(desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE))
        {
            JOptionPane.showMessageDialog(null, "Unable to open browser", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ip = null;
        Socket socket;
        while (true)
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
            while (scan.hasNextLine())
            {
                String url = scan.nextLine();
                if (!url.isEmpty())
                {
                    browse(desktop, url);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // silently stop the program if there is an error
            return;
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
