import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

public class Camera {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 CanvasFrame frame = new CanvasFrame("Camera");
		 FrameGrabber grabber = new OpenCVFrameGrabber(-1);
		 grabber.start();
		
		
		while(true){
			IplImage cameraImage = grabber.grab();
			
			CvMat redChannel,blueChannel,greenChannel, tempChannel,finalChan;
			int height  = cameraImage.height();
	        int width = cameraImage.width();
	        
	        //Split into channels
			redChannel = cvCreateMat(height,width,CV_8UC1);
	        blueChannel = cvCreateMat(height,width,CV_8UC1);
	        greenChannel = cvCreateMat(height,width,CV_8UC1);
	        cvSplit(cameraImage, blueChannel, greenChannel, redChannel,null);
	        tempChannel = cvCreateMat(height,width,CV_8UC1);
	        
	        //Extract Red from image
	        IplImage binaryRed;
        	cvAddWeighted(blueChannel, .5, greenChannel, .5, 0, tempChannel);
            finalChan = cvCreateMat(height,width,CV_8UC1);
            cvSub(redChannel,tempChannel,finalChan,null);
     	   	binaryRed = cvCreateImage(cameraImage.cvSize(), IPL_DEPTH_8U, 1);
     	   	cvConvert(finalChan, binaryRed);
			cvThreshold(binaryRed, binaryRed, 30, 255,CV_THRESH_BINARY);
			long time  = System.currentTimeMillis();
			//Start Finding Contours
			CvMemStorage storage = CvMemStorage.create();
			CvSeq contour = new CvSeq(null);
			cvFindContours(binaryRed, storage, contour,  Loader.sizeof(CvContour.class),
                    CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
			
			while (contour != null && !contour.isNull()) {
				//Contours with area > 100
                if (cvContourArea(contour, CV_WHOLE_SEQ, 0) > 100) {
                    CvSeq points = cvApproxPoly(contour, Loader.sizeof(CvContour.class),
                            storage, CV_POLY_APPROX_DP, cvContourPerimeter(contour)*0.05, 0);
                    
                    //If it has 4-8 sides
                    if (points.total() ==4 ){
                    JavacvLine[] edges = PolyLine.getEdges(points);
                    for (int i = 0; i < edges.length; i++)
                    {
                       double angle = Math.abs(
                          edges[(i + 1) % edges.length].GetExteriorAngleDegree(edges[i]));
                       if (angle < 80 || angle > 100) break;
                       else {
                    	   
                    	   cvDrawContours(cameraImage, points, CvScalar.BLUE, CvScalar.BLUE, -1, 1, CV_AA);
                       }        
                    }	
                    
                  }
                }
                contour = contour.h_next();
            }
			System.out.println("Time: " + (System.currentTimeMillis()-time));
			frame.showImage(cameraImage);
			cvClearMemStorage(storage);
			cvReleaseMat(redChannel);
		    cvReleaseMat(blueChannel);
		    cvReleaseMat(greenChannel);
		    cvReleaseMat(tempChannel);
			cvReleaseImage(binaryRed);
			cvReleaseMat(finalChan);
			
			
		}
	}
	
	

	
	

	

}
