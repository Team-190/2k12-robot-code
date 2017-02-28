/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team190.camera;

import edu.wpi.first.team190.commands.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Greg
 * Synchronization needs to be evaluated an possibly implemented.
 */
public class CRIOCamera extends Thread implements ICamera {
    //TO-DO make get and Set instead of public
    public AxisCamera m_camera;          // the axis m_camera object (connected to the switch)
    private CriteriaCollection m_cc;      // the criteria for doing the particle filter operation
    private boolean keepRunning = true;
    private double m_timeStamp = 0;
    private Rectangle rect = null;
    private double turretAngle = 0;
    public CRIOCamera() {
        
        m_camera = AxisCamera.getInstance();  // get an instance ofthe m_camera
        m_cc = new CriteriaCollection();      // create the criteria for the particle filter
        m_cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        m_cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
    }
    
    
    private double minAreaPercent = .80;
    private double proportion = 1.5;
    private double sizeFilterPercentage = 0.4;
    
    public void run(){
        
        while (keepRunning) {
            SmartDashboard.putString("Camera Resolution", Integer.toString(m_camera.getResolution().width) + ", " + Integer.toString(m_camera.getResolution().height));
            Thread.yield();
            try {
                ColorImage image = null;
                try{
                    turretAngle = CommandBase.turret.getCurrentAngle();
                    image = m_camera.getImage();
                    m_timeStamp=Timer.getFPGATimestamp();
                } catch(AxisCameraException e){
                    continue;//go back to beginning of while and look for image again
                }
                
                
                Thread.yield();
                BinaryImage thresholdImage = image.thresholdRGB(0, 47, 0, 45, 17, 255);   // keep only blue objects
                //BinaryImage thresholdImage = image.thresholdRGB(24, 255, 0, 45, 0, 45);   // keep only red objects
                Thread.yield();
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 1);  // remove small artifacts
                Thread.yield();
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                Thread.yield();
                BinaryImage filteredImage = convexHullImage.particleFilter(m_cc);           // find filled in rectangles
                
                
                /*Thread.yield();
                BinaryImage filteredImage = image.thresholdRGB(0, 47, 0, 45, 17, 255);   // keep only red objects
                //BinaryImage thresholdImage = image.thresholdRGB(24, 255, 0, 45, 0, 45);   // keep only red objects
                Thread.yield();
                filteredImage = filteredImage.removeSmallObjects(false, 1);  // remove small artifacts
                Thread.yield();
                filteredImage = filteredImage.convexHull(false);          // fill in occluded rectangles
                Thread.yield();
                filteredImage = filteredImage.particleFilter(m_cc);           // find filled in rectangles
                */
                
                //TODO: Display the filtered image to the smart dashboard
                
                
                ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of results
                if (reports.length==0){
                    rect = null;
                }
                else {
                    ParticleAnalysisReport topRect = null;//reports[0];
                    //grab the top and bottom rectangles out of the array
                    for (int i = 0; i < reports.length; i++) {                                // print results
                        ParticleAnalysisReport r = reports[i];
                        
                        //side of target filter, independnt of resolution
                        if(r.boundingRectWidth < r.boundingRectHeight){
                            //System.out.println("Tall skinny");
                            continue;
                        }
                        
                        //Window filter, independant of resolution
                        if (r.boundingRectWidth > (proportion * r.boundingRectHeight))
                        {
                            //System.out.println("Wide Object; Window");
                            continue;
                        }
                        
                        //Area Filter, DEPENDANT of resolution
                        if (r.particleToImagePercent < sizeFilterPercentage)
                        {
                            //System.out.println("Too Small: " + r.particleArea);
                            continue;
                        }
                        
                        double areaPercent = r.particleArea / (r.boundingRectWidth * r.boundingRectHeight);
                        //odd object filter, independant of resolution
                        if(areaPercent < minAreaPercent){
                            //System.out.println("Not Rectangular Enough: " + areaPercent);
                            continue;
                        }
                        
                        //the top of this one is lower than the top of the one we have 'stored'
                        if(topRect == null){
                            topRect = r;
                        }else if(r.boundingRectTop < topRect.boundingRectTop)
                        {
                            topRect = r;
                        }
                    }

                    if(topRect != null){
                        //check to make sure center of mass is the corerct variable here (its not, that is off the particle not the bouding rect
                        //rect = new Rectangle(topRect.center_mass_x, topRect.center_mass_y, topRect.boundingRectWidth, topRect.boundingRectHeight);
                        rect = new Rectangle((topRect.boundingRectLeft + (topRect.boundingRectWidth / 2)), (topRect.boundingRectTop + (topRect.boundingRectHeight / 2)), topRect.boundingRectWidth, topRect.boundingRectHeight);
                        
                    }else{
                        rect = null; //no rects with width > height
                    }
                }
                Thread.yield();

                Camera.getInstance().imageUpdated(this);

                Thread.yield();
                
                /**
                * all images in Java must be freed after they are used since they are allocated out
                * of C data structures. Not calling free() will cause the memory to accumulate over
                * each pass of this loop.
                */
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
    public Rectangle getRectangle() {
        return rect;
    }
    
    public double getTimestamp() {
        return m_timeStamp;
    }
    
    /**
     * Starts the camera thread
     */
    public void start(){ 
        keepRunning = true;
        super.start();
    }
    
    /**
     * Stops the camera thread
     */
    public void stop() {
        keepRunning = false;
    }

    public double getTurretAngle() {
        return turretAngle;
    }
    
    public void setResolution(int value)
    {
        switch(value)
        {
            case 0: m_camera.writeResolution(AxisCamera.ResolutionT.k640x480);
                    break;
            case 1: m_camera.writeResolution(AxisCamera.ResolutionT.k640x360);
                    break;
            case 2: m_camera.writeResolution(AxisCamera.ResolutionT.k320x240);
                    break;
            case 3: m_camera.writeResolution(AxisCamera.ResolutionT.k160x120);
                    break;
        }
    }
    public AxisCamera.ResolutionT getResolution()
    {
        return m_camera.getResolution();
    }
}
