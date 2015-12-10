package wwview;

import java.awt.event.MouseEvent;
import java.util.List;

import cnuphys.bCNU.format.DoubleFormat;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Vec4;

public class ViewFeedback {

    //the owner view
    private WWView _view;
    
    public ViewFeedback(WWView view) {
	_view = view;
    }
    
    /**
     * Handle the feedback
     * @param fbs the list of strings
     * @param e the causal mouse event
     * @param position the current mouse (world wind) position
     */
    public void handleFeedback(List<String> fbs, MouseEvent e, Position position) {
	fbs.clear();
	    commonFeedback(fbs, e, position);
	    _view.customFeedback(fbs, e, position);
	    _view.getControlPanel().getFeedbackPane().updateFeedback(fbs);
    }
    
    /**
     * Feedback common to all views
     * @param fbs the list of strings
     * @param e the causal mouse event
     * @param position the current mouse (world wind) position
    */
    protected void commonFeedback(List<String> fbs, MouseEvent e, Position position) {

	fbString("cyan", 
		"mouse (" + e.getX() + ", " + e.getY() + ")", 
		fbs);
	
	if (position == null) {
	    _view.setToolBarText(null);
	    fbString("red", "not on globe", fbs);
	    
	}
	else {
//	    StringBuffer sb2 = new StringBuffer(128);
//	    String slat2 = position.getLatitude().toDecimalDegreesString(2);
//	    String slon2 = position.getLongitude().toDecimalDegreesString(2);
//	    double elev2 = position.getElevation();
//
//	    sb2.append("(" + slat2 + ", " + slon2 + ") " + " elev: "
//		    + distanceString(elev2, 1));
//	    fbString("yellow", sb2.toString(), fbs);

	    
	    StringBuffer sb = new StringBuffer(128);
	    String slat = position.getLatitude().toDecimalDegreesString(2);
	    String slon = position.getLongitude().toDecimalDegreesString(2);
	    double elev = position.getElevation();

	    sb.append("(" + slat + ", " + slon + ") " + " elev: "
		    + distanceString(elev, 1));

	    _view.setToolBarText(sb.toString());
	    fbString("cyan", positionString(position, 3), fbs);
	}
    }
    
    // convenience method to create a feedback string with color
    public static void fbString(String color, String str, List<String> fbstrs) {
	fbstrs.add("$" + color + "$" + str);
    }
    
    //get the position string
    protected String positionString(Position position, int numDec) {
	if (position == null) {
	    return "";
	}
	StringBuffer sb = new StringBuffer(128);
	String slat = position.getLatitude().toDecimalDegreesString(numDec);
	String slon = position.getLongitude().toDecimalDegreesString(numDec);
	double elev = position.getElevation();
	

	sb.append("(" + slat + ", " + slon + ") " +
		" elev: " + distanceString(elev, 1) );
	
	Position eyePosition = _view.getWWView().getEyePosition();
	slat = eyePosition.getLatitude().toDecimalDegreesString(numDec);
	slon = eyePosition.getLongitude().toDecimalDegreesString(numDec);
	double alt = eyePosition.getAltitude();
	sb.append("\neye: (" + slat + ", " + slon + ") " +
		" alt: " + distanceString(alt, 1));
	
	return sb.toString();
    }

    /**
     * Get a distance string
     * @param dist the distance in meters
     * @param numDec the number of decimal points
     * @return a distance string that might be in km or m
     */
    protected String distanceString(double dist, int numDec) {
	if (dist > 9999.99) {
	    return DoubleFormat.doubleFormat(dist/1000, numDec+2) + " km";
	}
	else {
	    return DoubleFormat.doubleFormat(dist, numDec) + " m";
	}
    }
    

    
}
