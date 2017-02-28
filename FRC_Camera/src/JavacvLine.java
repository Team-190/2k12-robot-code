import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;


	public class JavacvLine extends Line2D.Float{
		Point2D.Float Direction;
		
		public JavacvLine(CvPoint start, CvPoint end){
			this.setLine(new Point2D.Float(start.x(), start.y()),
					new Point2D.Float(end.x(), end.y()));

			 float dx =	x1 - x2;
	         float dy = y1 - y2;
	         float dist = (float)Math.sqrt(dx * dx + dy * dy);
	         
			Direction= new Point2D.Float(dx / dist, dy / dist);
		}
		
		 public double GetExteriorAngleDegree(JavacvLine otherLine)
	      {
			 Point2D.Float direction1 = Direction;
			 Point2D.Float direction2 = otherLine.Direction;
	         double radianAngle = Math.atan2(direction2.y, direction2.x) - Math.atan2(direction1.y, direction1.x);
	         double degreeAngle = radianAngle * (180.0 / Math.PI);
	         
	         return
	             degreeAngle <= -180.0 ? degreeAngle + 360 :
	             degreeAngle > 180.0 ? degreeAngle - 360 :
	             degreeAngle;
	      }
		
		
	}