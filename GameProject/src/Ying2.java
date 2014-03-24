import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 */

/**
Ying Yang Game. 
We worked in pairs
Vaibhav Pandya, Justin Babel

 *
 */
public class Ying2 implements Shape {
	
	private Area mainPart;
	double X;
	private double Y;
	private double W,H;
	
	public Ying2(double x, double y, double w, double h)
	{
		X=x;
		Y=y;
		W=w;
		H=h;	
	
		mainPart = new Area(new Ellipse2D.Double(x,y,w,h));
		
		Area halfRect = new Area(new Rectangle2D.Double(x,y,w,h/2));
		Area InnerCircleAdded = new Area(new Ellipse2D.Double(x,y+(h/4),w/2,h/2));
		Area InnerCircleSub = new Area(new Ellipse2D.Double(x+(w/2),y+(h/4),w/2,h/2));
		Area InnerSmallAdd = new Area(new Ellipse2D.Double(x+((w/4)*3),y+(h/2), w/20,h/20));
		Area InnerSmallSub = new Area(new Ellipse2D.Double(x+(w/4),y+(h/2), w/20,h/20));
		Area EdgeAdd = new Area(new Ellipse2D.Double(x-1,y+1,w+1,h+1));		
		
		
		mainPart.subtract(halfRect);		
		mainPart.add(InnerCircleAdded);
		mainPart.subtract(InnerCircleSub);
		mainPart.add(InnerSmallAdd);
		mainPart.subtract(InnerSmallSub);
		mainPart.exclusiveOr(EdgeAdd);	
		
	}
	
	

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(java.awt.geom.Point2D)
	 */
	@Override
	public boolean contains(Point2D arg0) {
		// TODO Auto-generated method stub
		return mainPart.contains(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean contains(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return mainPart.contains(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(double, double)
	 */
	
	//this method calculates if a point is inside the circle
	@Override
	public boolean contains(double x, double y) {
		
		double CX=X+(W/2),CY=Y+(W/2);
		
		if (Math.sqrt(((x-CX)*(x-CX))+(y-CY)*(y-CY))-(W/2)<=0)//distance forumla 
	    {
	        return true;
	    }	    
	    
	    return false;	
		
		
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(double, double, double, double)
	 */
	@Override
	public boolean contains(double x, double y, double w, double h) {
		
	
		return mainPart.contains(x,y,w,h);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return mainPart.getBounds();
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getBounds2D()
	 */
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return mainPart.getBounds2D();
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform arg0) {
		// TODO Auto-generated method stub
		return mainPart.getPathIterator(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform, double)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
		// TODO Auto-generated method stub
		return mainPart.getPathIterator(arg0,arg1);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#intersects(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean intersects(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return mainPart.intersects(arg0);
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#intersects(double, double, double, double)
	 */
	
	//own version of intersect method
	public boolean intersects(double x, double y, double w, double h, double DX1, double DX2, double DY1, double DY2) {
		//translate the origin of circle to center for the sake of distance forumla
		double CX=X+(W/2),CY=Y+(W/2);
        double CX2=x+(w/2),CY2=y+(w/2);
        
		double dx = (CX - CX2) + 1 * (DX1 - DX2);
        double dy = (CY - CY2) + 1 * (DY1 - DY2);				
		
		if(Math.sqrt(dx*dx + dy*dy) < ((W/2)+(w/2)))
			return true;
		
	    return false;        
	    
		
	}



	@Override
	public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
