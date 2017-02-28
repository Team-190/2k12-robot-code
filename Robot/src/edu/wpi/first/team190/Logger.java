/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.team190;

import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;

/**
 *
 * @author Greg
 */
public class Logger {

    private static FileConnection file = null;
    private static OutputStream fileWriter = null;

    public static void init(){
        if(file == null){
                try {
                FileConnection fileRead = (FileConnection) Connector.open("file:///ShotLog.txt", Connector.READ);
                file = (FileConnection) Connector.open("file:///ShotLog.txt", Connector.WRITE);

                if(!fileRead.exists()){
                    file.create();
                    fileWriter = file.openOutputStream();
                }else{

                    InputStream fileReader = fileRead.openInputStream();
                    byte[] buffer = new byte[1024];
                    fileWriter = file.openOutputStream();
                    //int totalLength = 0;
                    int length = 1;
                    while(length>0){
                        length = fileReader.read(buffer);
                        System.out.println(length);
                        if(length > 0){
                            fileWriter.write(buffer, 0, length);
                            //totalLength+=length;
                        }
                    }
                    
                    fileReader.close();
                }

                fileWriter.write(("\r\n\r\n"+
                                  "*******************************\r\n" +
                                  "***********NEW MATCH***********\r\n" +
                                  "*******************************\r\n" +
                                  "  You're looking very nice today Greg and Brendan!\r\n").getBytes());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }

    public static void writeRaw(String s){
        try {
            fileWriter.write(s.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void write(String s){
        writeRaw("TimeStamp: " + Timer.getFPGATimestamp() + ", " + s);
    }
}
