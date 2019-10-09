import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketService {
    static List<Socket> socketList = new ArrayList<Socket>();

    public static void main(String args[])throws Exception {
        ServerSocket serverSocket = new ServerSocket(5208);
        System.out.println("聊天室开启");
        while (true) {
            Socket socket= serverSocket.accept();     //从连接请求队列中取出一个连接
            System.out.println("上线通知： 用户" + socket.getPort()+"上线啦！");
            socketList.add(socket);
            new Thread(new ServerThread(socket)).start();
        }
    }
}