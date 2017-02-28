/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Fred
 */
public class CameraProcessing extends Thread{
    AxisCamera m_camera;          // the axis m_camera object (connected to the switch)
    CriteriaCollection m_cc;      // the criteria for doing the particle filter operation
    //width of m_camera images
    final int m_width = 640;
    //field of view (angles)
    final int m_field = 54;
    double m_angle;
    double m_distance;
    private boolean keepRunning=true;
    public CameraProcessing() {
        m_camera = AxisCamera.getInstance();  // get an instance ofthe m_camera
        m_cc = new CriteriaCollection();      // create the criteria for the particle filter
        m_cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        m_cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
    }
   public void run(){
       
       while (keepRunning) {
            try {
                /**
                 * Do the image capture with the m_camera and apply the algorithm described above. This
                 * sample will either get images from the m_camera or from an image file stored in the top
                 * level directory in the flash memory on the cRIO. The file name in this case is "10ft2.jpg"
                 * 
                 */
                ColorImage image = null;
                try{
                image = m_camera.getImage();
                } catch(AxisCameraException e){
                    e.printStackTrace();
                }// comment if using stored images
                // next 2 lines read image from flash on cRIO
                //image =  new RGBImage("/10ft2.jpg");
                BinaryImage thresholdImage = image.thresholdRGB(25, 255, 0, 45, 0, 47);   // keep only red objects
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage filteredImage = convexHullImage.particleFilter(m_cc);           // find filled in rectangles
                
                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                System.out.println("Timestamp: " + Timer.getFPGATimestamp());
                if (reports.length==0){
                    System.out.println("Nothin' found");
                }
                else {ParticleAnalysisReport topRect = reports[0];
                //grab the top and bottom rectangles out of the array
                for (int i = 0; i < reports.length; i++) {                                // print results
                    ParticleAnalysisReport r = reports[i];
                    //the top of this one is lower than the top of the one we have 'stored'
                    if(r.boundingRectTop < topRect.boundingRectTop)
                    {
                        topRect = r;
                    }
                    System.out.println("Particle: " + i + ":  Center of mass x: " + r.center_mass_x);
                }
                //neg is left; right is pos
                    //based on the m_field of view (54 degress)
                    //and the m_width of the image (640 pix)
                double angle = (topRect.center_mass_x - (m_width/2.0))*(((double) m_field)/m_width);
                //distance is in feet
                double dist = 12.1204 + 0.0176756f*topRect.center_mass_y + 5.80852* MathUtils.pow(10, -9) * MathUtils.pow(topRect.center_mass_y, 4);
                SmartDashboard.putDouble("Angle", angle);
                SmartDashboard.putDouble("Distance", dist);
                m_angle=angle;
                m_distance=dist;

                /**
                 * all images in Java must be freed after they are used since they are allocated out
                 * of C data structures. Not calling free() will cause the memory to accumulate over
                 * each pass of this loop.
                 */
                }
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                thresholdImage.free();
                image.free();
                
//            } catch (AxisCameraException ex) {        // this is needed if the m_camera.getImage() is called
//                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }
        }
   }
   public double getAngle(){
       return m_angle;
   }
   public double getDistance(){
       return m_distance;
   }
   
}
