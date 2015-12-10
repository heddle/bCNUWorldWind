package wwview;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import cnuphys.bCNU.graphics.toolbar.BaseToolBar;
import cnuphys.bCNU.graphics.toolbar.ToolBarToggleButton;
import cnuphys.bCNU.view.BaseView;
import component.ControlPanel;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Extent;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.globes.Globe;

public class WWView extends BaseView {

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
    
    //the feed back handler
    protected ViewFeedback _viewFeedback;
    
    //the main menu bar
    protected JMenuBar _menuBar;
    
    //mouse handler
    protected ViewMouseHandler _mouseHandler;
        
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
		BaseToolBar.RECTGRIDBUTTON &
		~BaseToolBar.MAGNIFYBUTTON &
		~BaseToolBar.CONTROLPANELBUTTON &
		~BaseToolBar.CENTERBUTTON &
		~BaseToolBar.PANBUTTON);
	add(_baseToolBar, BorderLayout.NORTH);
		
	//feedback handler
	_viewFeedback = new ViewFeedback(this);
	
	//use overlay window to do rubberbanding
	_baseToolBar.getBoxZoomButton().setXorMode(true);
	
	_mouseHandler = new ViewMouseHandler(this);
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
	_wwWindow.getInputHandler().addMouseListener(_mouseHandler);
	_wwWindow.getInputHandler().addMouseMotionListener(_mouseHandler);

	//show the whole earth
	_defaultEye = new Position(eyeLat, eyeLon, eyeAlt);
	getWWView().setEyePosition(_defaultEye);

	add(comp, BorderLayout.CENTER);
	
	//make the menus
	makeMenus();
    }
    
    /**
     * Add menus here
     */
    protected void makeMenus() {
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

    /**
     * Custom view specific feedback. Subclasses should override.
     * @param fbs the list of strings
     * @param e the causal mouse event
     * @param position the current mouse (world wind) position
    */
    protected void customFeedback(List<String> fbs, MouseEvent e, Position position) {
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
    @Override
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
    
    /**
     * The active toolbar button changed.
     * 
     * @param activeButton the new active button.
     */
    protected void activeToolBarButtonChanged(ToolBarToggleButton activeButton) {
	System.err.println("New active toggle button: " + activeButton.getClass().getName());
    }

    /**
     * Checks whether the box zoom is active
     * @return <code>true</code> if the box zoom button is active
     */
    public boolean isBoxZoomActive() {
	return (_baseToolBar.getActiveButton() == _baseToolBar.getBoxZoomButton());
    }
    
    /**
     * Checks whether the rect grid is active
     * @return <code>true</code> if the rect grid button is active
     */
    public boolean isRectGridActive() {
	return (_baseToolBar.getActiveButton() == _baseToolBar.getRectGridButton());
    }
    
    /**
     * Compute the position from a screen point. Like a local-to-world.
     * @param x the x pixel
     * @param y the y pixel
     * @return the position (lat, lon, elev)
     */
    public Position computePositionFromScreenPoint(int x, int y) {
	return getWWView().computePositionFromScreenPoint(x, y);
    }

    /**
     * Get the control panel
     * @return the control panel
     */
    public ControlPanel getControlPanel() {
	return _controlPanel;
    }
    
    /**
     * Zoom to a given sector
     * @param lat1 the min latitude
     * @param lon1 the min longitude
     * @param lat2 the max latitude
     * @param lon2 the max longitude
     */
    public void zoomToSector(Angle lat1, Angle lon1, Angle lat2, Angle lon2) {
	
	LatLon ll1 = new LatLon(lat1, lon1);
	LatLon ll2 = new LatLon(lat2, lon2);
	Sector sector = Sector.boundingSector(ll1, ll2);
	Globe globe = getGlobe();
	double ve = getWorldWindow().getSceneController().getVerticalExaggeration();

	double[] minAndMaxElevations = globe.getMinAndMaxElevations(sector);
	double minElevation = minAndMaxElevations[0];
//	double maxElevation = Math.max(minAndMaxElevations[1], maxAltitude);
	double maxElevation = minAndMaxElevations[1];

	Extent extent = Sector.computeBoundingCylinder(globe, ve, sector, minElevation, maxElevation);
	if (extent == null) {
	    return;
	}

	Angle fov = getWWView().getFieldOfView();

	LatLon cll =  sector.getCentroid();
	Position centerPos = Position.fromDegrees(cll.latitude.degrees, cll.longitude.degrees);
	
	double zoom = extent.getRadius() / (fov.tanHalfAngle() * fov.cosHalfAngle());

	getWWView().goTo(centerPos, zoom);	
    }
    
    //get the collection of feedback strings
    protected List<String> getFeedbackStringCollection() {
	return _feedbackStrings;
    }
    
    //get the view feedback
    protected ViewFeedback getViewFeedback() {
	return _viewFeedback;
    }
    
}
