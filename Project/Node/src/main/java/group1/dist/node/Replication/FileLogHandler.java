package group1.dist.node.Replication;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class FileLogHandler {
    public FileLogHandler(){
    }

    public String createNewFileLog(String _filePath, String IP){
        String[] split = _filePath.split("/");
        String[] originalFileName = split[split.length - 1].split("\\.");
        String newFileName = originalFileName[0] + ".json";

        FileLogObject fileLogObject = new FileLogObject();
        fileLogObject.setName(originalFileName[0] + ".txt");
        fileLogObject.setDownloadLocation(IP);

        String[] filePathSplit = _filePath.split("\\.");
        String logFilePath = filePathSplit[0] + ".json";

        System.out.println("Path for log: " + logFilePath);
        try{
            File file = new File(logFilePath);
            if(file.createNewFile()){
                System.out.println("Successfully created log file for: " + originalFileName[0]);
                writeFile(fileLogObject, file);
            } else {
                System.out.println("Failed to create log file for: " + originalFileName[0] + "!");
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return logFilePath;
    }

    //Send path of original file, not the log file
    public void updateFileLog(String _filePath, String fileName, String date){
        String[] filePathSplit = _filePath.split("\\.");
        String logFilePath = filePathSplit[0] + ".json";

        FileLogObject logObject = fileToLogObject(_filePath);
        logObject.addFileUpdate(date + ": " + fileName + " is updated.");
        writeFile(logObject, new File(logFilePath));
    }

    public void updateReplicatedLog(String _filePath) throws UnknownHostException {
        String[] filePathSplit = _filePath.split("\\.");
        String logFilePath = filePathSplit[0] + ".json";

        FileLogObject logObject = fileToLogObject(_filePath);
        logObject.addReplicatedToNode("Replicated to: " + InetAddress.getLocalHost().getHostAddress());
        writeFile(logObject, new File(logFilePath));
    }

    private void writeFile(FileLogObject logObject, File _file){
        ObjectMapper obj = new ObjectMapper();

        try{
            String jsonString = obj.writeValueAsString(logObject);

            FileWriter fileWriter = new FileWriter(_file);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write(jsonString);
            printWriter.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //Send path of original file, not the log file
    public FileLogObject fileToLogObject(String _filePath){
        String[] split = _filePath.split("\\.");
        String logFilePath = split[0] + ".json";

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(logFilePath), FileLogObject.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public File getLogFile(String _filePath){
        String[] split = _filePath.split("\\.");
        String logFilePath = split[0] + ".json";

        return new File(logFilePath);
    }

    public boolean logFileExists(String _filePath){
        String[] split = _filePath.split("\\.");
        String logFilePath = split[0] + ".json";

        return new File(logFilePath).exists();
    }
}
