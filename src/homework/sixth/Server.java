package homework.sixth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket clientSocket = null;
        Scanner sc = new Scanner(System.in);

        try(ServerSocket serverSocket = new ServerSocket(8000)){
            System.out.println("server started");
            clientSocket = serverSocket.accept();
            System.out.println("client connected:"+ clientSocket.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Thread Reader = new Thread(()-> {
                try{
                    while (true){
                        out.writeUTF(sc.nextLine());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            Reader.setDaemon(true);
            Reader.start();
            while(true){
                String str = in.readUTF();
                if(str.equals("/end")){
                    System.out.println("client disconnected");
                    out.writeUTF("/end");
                    break;
                }else{
                    System.out.println("client: "+str);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                clientSocket.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}