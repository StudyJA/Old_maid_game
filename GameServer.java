
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


public class GameServer {
    public static final int PORT = 8888;

    public static void main(String[] args) {
        new GameServer();
        ServerSocket s_socket = null;
        List<PrintWriter> writerList = new ArrayList<>();
        OldMaidGameApp game = new OldMaidGameApp();

        // Network
        try {
            game.gameSetting();
            // 1. Create new server socket
            s_socket = new ServerSocket();

            // 2. Bind address and port
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            s_socket.bind(new InetSocketAddress(hostAddress, PORT));
            consoleLog("Waiting - "+ hostAddress + ":" + PORT);
            // 3. Waiting players connect
            /*
            int count = 1;
            while (true) {
                if (count < game.totalPlayer) {
                    Socket c_socket = s_socket.accept();
                    new GameProcessThread(c_socket, writerList, game).start();
                    count += 1;
                }
                else {
                    if(game.playerList.size() == game.totalPlayer)
                        break;
                }

            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                game.run();
                if( s_socket != null && !s_socket.isClosed()) {
                    s_socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}
