/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.smartdashboard;

import edu.wpi.first.smartdashboard.camera.WPICameraExtension;
import edu.wpi.first.wpijavacv.WPIColor;
import edu.wpi.first.wpijavacv.WPIColorImage;
import edu.wpi.first.wpijavacv.WPIImage;
import edu.wpi.first.wpijavacv.WPIPoint;
import edu.wpi.first.wpilibj.networking.NetworkTable;
import java.util.NoSuchElementException;

/**
 *
 * @author Mitchell
 */
public class CameraDisplay extends Team190CameraExtension {
    public static final String NAME = "Camera Track Display";
    private NetworkTable table = NetworkTable.getTable("cameraTrack");
    static final int IMAGE_CENTER = 340/2;
    
    public WPIImage processImage(WPIColorImage rawImage) {
        try{
            int x = table.getInt("x")/2;
            int y = table.getInt("y")/2;
            int width = table.getInt("width")/2;
            int height = table.getInt("height")/2;
            rawImage.drawLine(new WPIPoint(IMAGE_CENTER, 0), new WPIPoint(IMAGE_CENTER, rawImage.getHeight()), WPIColor.CYAN, 2);
            rawImage.drawLine(new WPIPoint(x-width/2, y-height/2), new WPIPoint(x+width/2, y+height/2), WPIColor.GREEN, 1);
            rawImage.drawLine(new WPIPoint(x-width/2, y+height/2), new WPIPoint(x+width/2, y-height/2), WPIColor.GREEN, 1);
            rawImage.drawRect(x-width/2, y-height/2, width, height, WPIColor.GREEN, 4);
        } catch(NoSuchElementException e){
            
        }
        return rawImage;
    }
}
