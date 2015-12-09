package wwview;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;

import cnuphys.bCNU.format.DoubleFormat;
import cnuphys.bCNU.graphics.toolbar.BaseToolBar;
import cnuphys.bCNU.view.BaseView;
import component.ControlPanel;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;

public class WWView extends BaseView
	implements MouseListener, MouseMotionListener {

    protected static final int defaultFBWidth = 250;

    // world wind window
    protected WorldWindow _wwWindow;

    // the control panel
    protected ControlPanel _controlPanel;

    // feedback strings
    private Vector<String> _feedbackStrings = new Vector<String>(25, 5);
    
    //container used to deal with toolbar
    protected WWViewContainer _container;
    
    //toolbar
    protected BaseToolBar _baseToolBar;
    
    //the main component (the map)
    protected JComponent _mainComponent;
    
    //the default eye position
    protected Position _defaultEye;

    /**
     * Constructor
     * 
     * @param keyVals an optional variable length list of propeties in
     *            type-value pairs. For example, PropertySupport.TITLE,
     *            "my application", PropertySupport.MAXIMIZABE, true, etc.
     */
    public WWView(Object... keyVals) {
	super(keyVals);
	setLayout(new BorderLayout(2, 2));
	_controlPanel = createControlPanel();
	_container = new WWViewContainer(this);
	
	//the toolbar
	_baseToolBar = new BaseToolBar(_container, 
		BaseToolBar.NODRAWING & 
		~BaseToolBar.MAGNIFYBUTTON &
		~BaseToolBar.CONTROLPANELBUTTON &
		~BaseToolBar.CENTERBUTTON &
		~BaseToolBar.PANBUTTON);
	add(_baseToolBar, BorderLayout.NORTH);
    }

    /**
     * final preparation
     * @param wwwindow the WorldWindow
     * @param comp the central component (may be the same as the world window)
     * @param eyeLat the eye latitude
     * @param eyeLon the eye longitude
     * @param eyeAlt the eye altitude (m)
     */
    protected void prepare(WorldWindow wwwindow, JComponent comp,
	    Angle eyeLat, Angle eyeLon, double eyeAlt) {
	_wwWindow = wwwindow;
	_mainComponent = comp;
	_wwWindow.getInputHandler().addMouseListener(this);
	_wwWindow.getInputHandler().addMouseMotionListener(this);

	//show the whole earth
	_defaultEye = new Position(eyeLat, eyeLon, eyeAlt);
	getWWView().setEyePosition(_defaultEye);

	add(comp, BorderLayout.CENTER);
    }

    // create the control panel
    protected ControlPanel createControlPanel() {
	ControlPanel cp = new ControlPanel(defaultFBWidth);
	add(cp, BorderLayout.EAST);
	return cp;
    }

    /**
     * Get the world wind world window
     * 
     * @return the world wind world window
     */
    public WorldWindow getWorldWindow() {
	return _wwWindow;
    }

    /**
     * Obtain the world wind view
     * 
     * @return the world wind view
     */
    public View getWWView() {
	return ((_wwWindow == null) ? null : _wwWindow.getView());
    }

    /**
     * Get the world wind globe.
     * 
     * @return the world wind globe.
     */
    public Globe getGlobe() {
	View view = getWWView();
	return ((view == null) ? null : view.getGlobe());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
   }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	
	Position position = _wwWindow.getCurrentPosition();
	if (!(e.isControlDown())) {
	    _feedbackStrings.clear();
	    commonFeedback(e, position);
	    customFeedback(_feedbackStrings, e, position);
	    _controlPanel.getFeedbackPane().updateFeedback(_feedbackStrings);
	}
    }

    //common feedback
    private void commonFeedback(MouseEvent e, Position position) {

//	fbString("cyan", 
//		"mouse (" + e.getX() + ", " + e.getY() + ")", 
//		_feedbackStrings);
	
	if (position == null) {
	    setToolBarText(null);
	    fbString("red", "not on globe", _feedbackStrings);
	}
	else {
	    StringBuffer sb = new StringBuffer(128);
	    String slat = position.getLatitude().toDecimalDegreesString(2);
	    String slon = position.getLongitude().toDecimalDegreesString(2);
	    double elev = position.getElevation();

	    sb.append("(" + slat + ", " + slon + ") " + " elev: "
		    + distanceString(elev, 1));

	    setToolBarText(sb.toString());
	    fbString("cyan", positionString(position, 3), _feedbackStrings);
	}
    }

    //custom view specific feedback
    protected void customFeedback(List<String> fbs, MouseEvent e, Position position) {

    }

    // convenience method to create a feedback string with color
    protected void fbString(String color, String str, List<String> fbstrs) {
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
	
	Position eyePosition = getWWView().getEyePosition();
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
    
    /**
     * Get the main component (the map)
     * @return the main component
     */
    public JComponent getMainComponent() {
	return _mainComponent;
    }
    
    /**
     * Get the base tool bar
     * @return the toobar
     */
    public BaseToolBar getToolBar() {
	return _baseToolBar;
    }
    
    /**
     * Get the eye position
     * @return the eye position
     */
    public Position getEyePosition() {
	return getWWView().getEyePosition();
    }
    
    /**
     * Set the eye position
     * @param eye the eye position
     */
    public void setEyePosition(Position eye) {
	getWWView().setEyePosition(eye);
	refresh();
    }
    
    /**
     * Redraw the map
     */
    public void refresh() {
	System.err.println("REFRESH");
//	_wwWindow.redraw();
	_wwWindow.redrawNow();
    }
    
    /**
     * Get the default eye position
     * @return the default eye position
     */
    public Position getDefaultEyePosition() {
	return _defaultEye;
    }
    
    /**
     * Set the toolbar text
     * @param s the text
     */
    public void setToolBarText(String s) {
	_baseToolBar.setText((s == null) ? "" : s);
    }

}
