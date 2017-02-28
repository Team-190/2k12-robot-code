/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.cobra2k12.subsystems;

import com.sun.squawk.microedition.io.FileConnection;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Encoder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 *
 * @author dtjones
 */
public class IndexedEncoder extends Encoder implements AbsoluteAngleSensor {

    private Counter m_indexed;
    private double m_center;
    private int m_indexChannel;
    private int m_count = 1024; //counts per rev
    private String fName;

    public IndexedEncoder(final int aChannel, final int bChannel, final int indexChannel, boolean reverseDirection) {
        super(aChannel, bChannel, indexChannel, reverseDirection);
        m_indexed = new Counter(m_indexSource);
        m_indexed.start();
        start();
        m_indexChannel = indexChannel;
        fName = "file:////IndexedEncoder" + m_indexChannel;
        System.out.println(fName);
        try {
            FileConnection file = (FileConnection) Connector.open(fName, Connector.READ);
            if (file.exists()) {
                DataInputStream inStream = file.openDataInputStream();
                m_center = inStream.readDouble();
                inStream.close();
            } else {
                System.out.println("File does not exist");
                m_center = 0.0;
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot open file to calibrate");
            m_center = 0.0;
        }


    }

    public boolean isIndexed() {
        return (m_indexed.get() > 0);
    }

    public double getRawAngle() {
        return get() * 2 * Math.PI / m_count;
    }

    public double getAngle() {
        if (isIndexed()) {
            return (((getRawAngle() - m_center) % (2 * Math.PI)) + 2*Math.PI) % (2*Math.PI);
        }
        return getRawAngle();
    }

    public void setCountsPerRev(final int count) {
        m_count = count;
    }

    public void calibrate() {
        m_center = getRawAngle();
        try {
            FileConnection file = (FileConnection) Connector.open(fName, Connector.READ_WRITE);
            if (!file.exists())
                file.create();
            DataOutputStream outStream = file.openDataOutputStream();
            outStream.writeDouble(m_center);
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot open file to calibrate");
        }

    }

    public double pidGet() {
        return (getAngle() + 4 * Math.PI) % (2 * Math.PI);
    }
}
