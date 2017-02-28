package edu.wpi.first.team190.camera;

import edu.wpi.first.team190.RobotMap;
import edu.wpi.first.team190.camera.ICamera.Rectangle;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.Preferences.*;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
/**
 *
 * @author Mitchell
 */
public class Camera implements NamedSendable{
    //width of m_camera images
    private double IMAGE_START_WIDTH = 320.0;
    private double IMAGE_START_HEIGHT = 240.0;
    
    private double IMAGE_WIDTH = 320.0;
    private double IMAGE_HEIGHT =240.0 ;
    private double IMAGE_CENTER =177.0;//was 335, changed on Practice field 3/31
    static final double IMAGE_CENTER_PERCENTAGE = 0.5546875;//the percentage (from the left)
    
    //field of view (angles)
    
    //doesn't change by the resolution
    static final double CAMERA_FIELD_ANGLE = 45.7;
    
    /*
     * Camera Settings
     * Color: 100
     * Brightness: 14
     * Sharpness: 50
     * Contrast: 70
     * White Balance: Auto
     * Exposure: 0
     * Exposure Control: Hold Current
     * 
     * 
     * 
     * THESE ARE OLD!!!!
     */
    
    
    private Solenoid cameraLight;
    private Camera(){
        cameraLight = new Solenoid(RobotMap.CAMERA_LIGHTS);
        setCameraLights(true);
        SmartDashboard.putInt("PixelsFromTop", 0);
    }
    
    public void setCameraLights(boolean on){
        cameraLight.set(on);
    }
    
    
    //TO-DO make get and set functions instead of public
    public CRIOCamera crioCamera = new CRIOCamera();
    {
        crioCamera.start();
        IMAGE_START_HEIGHT = crioCamera.getResolution().height;
        IMAGE_START_WIDTH = crioCamera.getResolution().width;
    }
    
    public void setResolution(int value)
    {
        crioCamera.setResolution(value);
        IMAGE_WIDTH = crioCamera.getResolution().width;
        IMAGE_HEIGHT = crioCamera.getResolution().height;
        IMAGE_CENTER = IMAGE_WIDTH*IMAGE_CENTER_PERCENTAGE;
        
    }
    private OffBoardCamera offboardCamera = new OffBoardCamera();
    
    
    private double angle = 0;
    private double distance = Double.NaN;
    private double timestamp = Double.NaN;
    private Rectangle rectangle = null;
    private Rectangle lastRectangle = null;
    private double turretAngle = 0;
    public double pixelOffset=0;
    
    
    void imageUpdated(ICamera camera) {
        Rectangle rect = camera.getRectangle();
        if(rect!=null && rect.width>rect.height){
            double top = rect.y-rect.height/2;
            //neg is left; right is pos
            pixelOffset = rect.x - IMAGE_CENTER;
            
            SmartDashboard.putDouble("Pixel Offset: ", pixelOffset);
            
            double angle = (pixelOffset)*((CAMERA_FIELD_ANGLE)/IMAGE_WIDTH);//calculate the offset angle
            //distance is in feet
            double dist = 14.500254659770697 //calculate the 
                    + 0.09609918699725427 * rect.y
                    - 0.00042687336191977624 * (rect.y * rect.y)
                    + 0.0000004465176666916765 * (rect.y * rect.y * rect.y);

            this.angle = angle;//angle offset to turn the turret
            this.distance = dist;
            
            //System.out.println("Rectangle top: " + (rect.y-rect.height/2) +",   x: "+rect.x);
            //System.out.println("Distance: " + dist + " Expected RPM: " + Shooter.distanceToRPM(dist));
        }
        else{
            this.angle = Double.NaN;
            this.distance = Double.NaN;
        }
        
        this.lastRectangle = this.rectangle;
        this.rectangle = rect;
        timestamp = camera.getTimestamp();
        
        turretAngle = camera.getTurretAngle();
        
        
        
        getTable().putString("Current", camera.getClass().getName());
        getTable().putNumber("Timestamp", getTimestamp());
        
        try {
            getTable().putNumber("Offset", getAngleOffset());
            getTable().putNumber("Distance", getDistance());
            SmartDashboard.putInt("PixelsFromTop", rect.y);
        } catch (NoTargetException ex) {
            SmartDashboard.putInt("PixelsFromTop", -1);
            getTable().putNumber("Offset", Double.NaN);
            getTable().putNumber("Distance", Double.NaN);
        }
        
        if(rect!=null){
            cameraTrackTable.putNumber("x", (int)(rect.x * (IMAGE_START_WIDTH/IMAGE_WIDTH)));
            cameraTrackTable.putNumber("y", (int) (rect.y * (IMAGE_START_HEIGHT/IMAGE_HEIGHT)));
            cameraTrackTable.putNumber("width", (int)(rect.width * (IMAGE_START_WIDTH/IMAGE_WIDTH)));
            cameraTrackTable.putNumber("height", (int) (rect.height * (IMAGE_START_HEIGHT/IMAGE_HEIGHT)));
        }
        else{
            cameraTrackTable.putNumber("x", -1);
            cameraTrackTable.putNumber("y", -1);
            cameraTrackTable.putNumber("width", 0);
            cameraTrackTable.putNumber("height", 0);
        }
        /*
        try {
            System.out.println("TurretAngle: " + this.getTurretAngle() + ", AngleOffset: " + this.getAngleOffset() +
                    " , Angle: "+ this.getTargetAngle());
        } catch (NoTargetException ex) {
            System.out.println("No target");
        }*/
    }
    // Returns the distance from the camera
    public double getDistance() throws NoTargetException {
        if(rectangle==null)
            throw new NoTargetException();
        return distance;
    }
    // Returns the angle offset to the center of the target

    public double getAngleOffset() throws  NoTargetException {
        if(rectangle==null)
            throw new NoTargetException();
        return angle;
    }

    public boolean seesTarget(){
        return rectangle != null;
    }
    
    public double getTurretAngle(){
        return turretAngle;
    }
    
    public double getTargetAngle() throws NoTargetException{
        return getTurretAngle()+getAngleOffset();
    }
    
    public boolean isStationary() throws NoTargetException{
        if(this.rectangle == null || this.lastRectangle == null){
            throw new NoTargetException();
        }
        
        return Math.abs(lastRectangle.x - rectangle.x) < 3;
    }
    
    public Rectangle getRectangle(){
        return rectangle;
    }

    // Returns the timestamp of when the image processed was taken
    public double getTimestamp() {
        return timestamp;
    }
    
    private static Camera INSTANCE = null;
    public static Camera getInstance(){
        if(INSTANCE==null)
            INSTANCE = new Camera();
        return INSTANCE;
    }

    //Smart Dashboard
    public String getName() {
        return "Camera";
    }

    public int getLastYPix(){
        return SmartDashboard.getInt("PixelsFromTop", -1);
    }

    private ITable table = null;
   
    private NetworkTable cameraTrackTable = NetworkTable.getTable("cameraTrack");

    public void initTable(ITable table) {
        this.table=table;
    }

    public ITable getTable() {
        return table;
    }

    public String getSmartDashboardType() {
        
        return "Camera";
    }
}
