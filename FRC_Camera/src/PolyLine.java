import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;

	public class PolyLine{
		
		public static JavacvLine[] getEdges(CvSeq points){
		JavacvLine[] edges;
	
		int length = points.total();
		edges = new JavacvLine[length];
		
		Pointer lastPointPtr = (Pointer) cvGetSeqElem(points, length-1);
		CvPoint lastPoint = new CvPoint(lastPointPtr);
        for (int i = 0; i < edges.length; i++)
        {
         Pointer currentPointPtr = (Pointer) cvGetSeqElem(points, i);
         CvPoint currentPoint = new CvPoint(currentPointPtr);
           edges[i] = new JavacvLine(lastPoint, currentPoint);
           lastPoint = currentPoint;
        }
        return edges;
		}
	}