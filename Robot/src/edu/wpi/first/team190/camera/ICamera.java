package edu.wpi.first.team190.camera;

/**
 *
 * @author alex
 */
public interface ICamera {
    public Rectangle getRectangle();
    
    // Returns the timestamp of when the image processed was taken
    public double getTimestamp();
    
    //Starts the camera processing
    public void start();
    
    //Stop the camera processing
    public void stop();

    public double getTurretAngle();
    
    public class Rectangle{
        public int x;//center of mass
        public int y;//center of mass
        public int width;
        public int height;
        public Rectangle(int x, int y, int width, int height){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public String toString() {
            return "Rectangle{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + '}';
        }
    }
}
