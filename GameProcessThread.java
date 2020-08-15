import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GameProcessThread extends Thread{
    private String nickname = null;
    private Socket m_socket;
    private OldMaidGameApp game;
    List<PrintWriter> writerList = null;

    public GameProcessThread(Socket _socket, List<PrintWriter> writerList, OldMaidGameApp game) {
        m_socket = _socket;
        this.writerList = writerList;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(m_socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter player = new PrintWriter(new OutputStreamWriter(m_socket.getOutputStream(), StandardCharsets.UTF_8));

            while (true) {
                String request = bufferedReader.readLine();

                if (request == null) {
                    System.out.println("Disconnect from the client");
                    doQuit(player);
                    break;
                }
                else // Process requests
                {
                    /** 받을 수 있는 데이터
                     * 1. 사용자 설정 정보
                     * 2. 사용자 플레이 상황
                     * 3. 클라이언트 시작 종료 상황
                     */
                    String[] tokens = request.split(":");
                    if("join".equals(tokens[0])) {
                        doJoin(tokens[1], player);
                    }
                    else if("message".equals(tokens[0])) {
                        doMessage(tokens[1]);
                    }
                    else if("quit".equals(tokens[0])) {
                        doQuit(player);
                    }
                }
            }
        } catch (IOException e) {
            doMessage(this.nickname + " has broken");
        }
    }

    private void doQuit(PrintWriter player) {
        writerList.remove(player); // Delete
        String data = this.nickname + " has exited";
        broadcast(data);
    }

    private void doMessage(String data) {
        broadcast(this.nickname + ":" + data);
    }

    private void doJoin(String nickname, PrintWriter player) {
        this.nickname = nickname;
        String data = nickname + " has login!";
        broadcast(data);

        // Store into player pool
        writerList.add(player);
        game.p.addPlayer(nickname, writerList.size());
    }

    private void broadcast(String data) {
        synchronized (writerList) {
            for(PrintWriter player : writerList) {
                player.println(data);
                player.flush();
            }
        }
    }
}
