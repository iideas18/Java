import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketThread2 implements Runnable {

    private Socket socket;

    SocketThread2(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String str = br.readLine();
                System.out.println(str);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}