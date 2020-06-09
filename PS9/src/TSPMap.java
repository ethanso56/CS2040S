import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Travelling Salesman Map
 * Problem Set 9
 * @author gilbert
 * 
 */
public class TSPMap {
	
	/**
	 * class Point
	 * Encapsulates a 2d-point.
	 * It also contains a next field that
	 * links to the next point.
	 */
	public static class Point {
		// The x and y coordinates of the point
		private double x = 0;
		private double y = 0;
		
		// The next point to visit on the TSP traversal
		private int next = -1;
		
		/**
		 * Constructor
		 * @param newx
		 * @param newy
		 */
		Point(double newx, double newy){
			x = newx;
			y = newy;
		}
		
		/**
		 * getX
		 * @return
		 */
		public double getX(){
			return x;
		}
		
		/**
		 * getY
		 * @return
		 */
		public double getY(){
			return y;
		}
		
		/**
		 * getLink
		 * @return
		 */
		public int getLink(){
			return next;
		}
		
		/**
		 * distance
		 * @param other
		 * @return the Euclidean distance between two points
		 */
		public double distance(Point other){
			double xDist = Math.abs(x - other.x);
			double yDist = Math.abs(y - other.y);
			return Math.sqrt(xDist*xDist + yDist*yDist);
		}
		
		/**
		 * hashcode
		 * @return a hashcode for this point
		 */
		@Override
		public int hashCode(){
			long hash = (Double.doubleToLongBits(x) ^ Double.doubleToLongBits(y));
			return (int) (hash ^ (hash >>> 32));
		}
		
		/**
		 * equals
		 * Two points are equal if they have the same x and y coordinates.
		 */
		@Override
		public boolean equals(Object other){
			if (other == null) return false;
			if (!(other instanceof Point)) return false;
			
			Point o = (Point)other;
			if (x != o.x) return false;
			if (y != o.y) return false;
			return true;
		}
		
		/**
		 * toString
		 * Returns a textual representation of the point.
		 */
		@Override
		public String toString(){
			String output = "";
			output = '(' + String.valueOf(x) + ',' + String.valueOf(y) + ')';
			return output;
		}
	}
	
	/**
	 * Private variables for the map 
	 */
	
	// Array of points
	private ArrayList<Point> m_points = new ArrayList<Point>();
	
	// Max coordinate (implicitly min = 0), default value=100
	private double m_max = 100;
	
	// Initialize drawing canvas?
	private boolean m_initCanvas = false;
	
	/**
	 * Constructor
	 * @param max
	 * Minimal constructor that sets the max coordinates
	 */
	TSPMap(double max){
		m_max = max;
	}

	public ArrayList<Point> getM_points() {
		return m_points;
	}

	/**
	 * Constructor
	 * @param fileName
	 * Reads points from the specified filename
	 */
	TSPMap(String fileName){
		readPoints(fileName);
	}

	/**
	 * addPoint
	 * @param x
	 * @param y
	 * Adds a new point.
	 */
	private void addPoint(double x, double y){
		m_points.add(new Point(x,y));
	}
	
	/**
	 * readPoints
	 * Reads the points from a file.
	 */
	private void readPoints(String file){
		try{
			FileReader f = new FileReader(file);
			BufferedReader b = new BufferedReader(f);
			
			// Read max
			String line = b.readLine();
			line.trim();
			m_max = Double.valueOf(line);
			if (m_max <= 0) throw new Exception("Invalid file format.");
			
			// Read number of points
			line = b.readLine();
			line.trim();
			int numPoints = Integer.valueOf(line);
			if (numPoints <= 0) throw new Exception("Invalid file format.");
			
			// Read in all the points
			for (int i=0; i<numPoints; i++){
				line = b.readLine();
				line.trim();
				int comma = line.indexOf(',');
				String strX = line.substring(0, comma);
				String strY = line.substring(comma+1);
				double x = Double.valueOf(strX);
				double y = Double.valueOf(strY);
				addPoint(x,y);
			}	
			b.close();
			f.close();
		}
		catch(Exception e)
		{
			System.out.println("Error reading file: "+ e);
		}
		
		// Draw the graph
		redraw();
	}
	
	/**
	 * initDraw
	 * Initialize the drawing canvas.
	 */
	private void initDraw(){
		// Only do this once
		if (m_initCanvas == false){
			m_initCanvas = true;
			StdDraw.setCanvasSize(500, 500);
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setScale(0, m_max);
		}
	}
	
	/**
	 * redraw
	 * Updates the drawing canvas
	 */
	public void redraw(){
		// Initialize drawing canvas
		initDraw();
		
		// Clear the canvas
		StdDraw.clear();
		
		
		// If there are any points, draw them
		for (int i=0; i<m_points.size(); i++){
			// Draw a circle for each point
			StdDraw.circle(m_points.get(i).x, m_points.get(i).y, 0.5);
			
			// If there is a link, draw a line
			int j = m_points.get(i).next;
			if (j >= 0){
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(m_points.get(i).x, m_points.get(i).y, m_points.get(j).x, m_points.get(j).y);
				StdDraw.setPenColor(StdDraw.BLUE);
			}
		}
	}
	
	/**
	 * toString
	 * Returns a textual version of the points
	 */
	public String toString(){
		String output = "";
		for (int i=0; i<m_points.size(); i++){
			output += m_points.get(i).toString();
			output += '\n';
		}
		return output;
		
	}
	
	/**
	 * getMax
	 * @return maximum coordinates
	 */
	public double getMax(){
		return m_max;
	}
	
	/**
	 * getMin
	 * @return minimum coordinate
	 * The minimum coordinate is always 0.
	 */
	public double getMin(){
		return 0;
	}
	
	/**
	 * getCount
	 * @return number of points on the map
	 */
	public int getCount(){
		return m_points.size();
	}
	
	/**
	 * getPoint
	 * @param i
	 * @return point i on the map
	 */
	public Point getPoint(int i){
		if (i < 0) throw new IllegalArgumentException("Invalid point number.");
		if (i >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		
		return m_points.get(i);
	}
	
	/**
	 * setLink
	 * @param i
	 * @param j
	 * Creates a link from point i to point j.
	 * The link might represent the parent in an MST, or it
	 * might represent the next step in the TSP tour. 
	 * The link is drawn immediately. 
	 */
	public void setLink(int i, int j){
		setLink(i,j,true);
	}
	
	/**
	 * setLink
	 * @param i
	 * @param j
	 * @param redraw
	 * Creates a link from point i to point j.
	 * The link might represent the parent in an MST, or it
	 * might represent the next step in the TSP tour. 
	 * If (redraw==true), the link is drawn immediately. 
	 * Otherwise, it is not redrawn until redraw() is called.
	 */
	public void setLink(int i, int j, boolean redraw){
		if (i < 0) throw new IllegalArgumentException("Invalid point number.");
		if (i >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		if (j < 0) throw new IllegalArgumentException("Invalid point number.");
		if (j >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		
		m_points.get(i).next = j;	
		if (redraw) redraw();
	}
	
	/**
	 * eraseLink
	 * @param i
	 * Erases the outgoing link from point i.
	 * The link is drawn immediately. 
	 */
	public void eraseLink(int i){
		eraseLink(i, true);
	}
	
	/**
	 * eraseLink
	 * @param i
	 * @param redraw
	 * Erases the outgoing link from point i.
	 * If (redraw==true), the link is drawn immediately. 
	 * Otherwise, it is not redrawn until redraw() is called.
	 */
	public void eraseLink(int i, boolean redraw){
		if (i < 0) throw new IllegalArgumentException("Invalid point number.");
		if (i >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		
		m_points.get(i).next = -1;	
		if (redraw) redraw();
	}
	
	/**
	 * getLink
	 * @param i
	 * @return the current outgoing link from point i
	 */
	public int getLink(int i){
		if (i < 0) throw new IllegalArgumentException("Invalid point number.");
		if (i >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		
		return m_points.get(i).next;	
	}
		
	/**
	 * pointDistance
	 * @param i
	 * @param j
	 * @return the Euclidean distance between points i and j
	 */
	public double pointDistance(int i, int j){
		if (i < 0) throw new IllegalArgumentException("Invalid point number.");
		if (i >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		if (j < 0) throw new IllegalArgumentException("Invalid point number.");
		if (j >= m_points.size()) throw new IllegalArgumentException("Invalid point number.");
		
		return m_points.get(i).distance(m_points.get(j));
	}
	
		
	public static void main(String[] args){
		// Open a new map
		TSPMap map = new TSPMap("tenpoints.txt");
		
		// Set up the links in order
		for (int i=0; i<map.getCount()-1; i++){
			map.setLink(i, i+1, false);
		}
		map.setLink(map.getCount()-1, 0, false);
		
		// Redraw everything
		map.redraw();			
	}
	

	
}
