package homework.sixth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int  PORT = 8190;
    private static final String IP_ADDRESS ="localhost";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Socket socket = null;
        try {
            socket = new Socket(IP_ADDRESS, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            System.out.println("Connected to: "+socket.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Thread Reader  = new Thread(()-> {
                try{
                    while(true){
                        out.writeUTF(sc.nextLine());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            Reader.setDaemon(true);
            Reader.start();
            while (true){
                String str = in.readUTF();
                if(str.equals("/end")){
                    System.out.println("disconnected");
                    out.writeUTF("/end");
                    break;
                }else{
                    System.out.println("Server: "+str);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
