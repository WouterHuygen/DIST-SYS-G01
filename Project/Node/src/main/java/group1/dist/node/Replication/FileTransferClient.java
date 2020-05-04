package group1.dist.node.Replication;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;


public class FileTransferClient {

    public static void ClientRun(String ip, String fileName) throws Exception {
        System.out.println("Client startup!");
        System.out.println(ip);
        System.out.println(InetAddress.getByName(ip));
        //Initialize socket
        Socket socket = new Socket(InetAddress.getByName(ip), 5000);
        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("/home/pi/files/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;

        while((bytesRead=is.read(contents))!=-1)
            bos.write(contents, 0, bytesRead);

        bos.flush();
        socket.close();

        System.out.println("File saved successfully!");
    }
}