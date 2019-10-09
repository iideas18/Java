import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    ServerThread(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String str = br.readLine();
                for (Socket item : SocketService.socketList) {
                    PrintWriter pw = new PrintWriter(item.getOutputStream());
                    pw.println("用户"+socket.getPort()+"说："+str);
                    pw.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
