package wwview;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuBar;

import cnuphys.bCNU.util.PropertySupport;
import component.LayersMenu;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.FlatGlobe;
import util.ProjectionSupport;
import wwcomponent.FlatPanel;

public class FlatView extends WWView {
    
    //world wind flat panel
    private FlatPanel _wwFlatPanel;
    
    // reserved view type for log view
    public static final int FLATVIEWTYPE = 482841;

    public FlatView() {
	super(PropertySupport.TITLE, "Flat View", PropertySupport.ICONIFIABLE,
		true, PropertySupport.MAXIMIZABLE, true,
		PropertySupport.CLOSABLE, true, PropertySupport.RESIZABLE, true,
		PropertySupport.WIDTH, 800, PropertySupport.HEIGHT, 300,
		PropertySupport.VISIBLE, false, PropertySupport.VIEWTYPE,
		FLATVIEWTYPE);

	_wwFlatPanel = new FlatPanel();
	_wwWindow =_wwFlatPanel;
	prepare(_wwFlatPanel, _wwFlatPanel, Angle.ZERO, Angle.ZERO, 48400000);
    }

    /**
     * Get the FlatGobe
     * @return the FlatGlobe
     */
    public FlatGlobe getFlatGlobe() {
	return (FlatGlobe)getGlobe();
    }
    
    /**
     * Add menus here
     */
    @Override
    protected void makeMenus() {
	_menuBar = new JMenuBar();
	setJMenuBar(_menuBar);
	
	//layers menu
	_menuBar.add(new LayersMenu(_wwWindow));
	
	//projection menu
	_menuBar.add(ProjectionSupport.getProjectionMenu(this));
    }

    
    /**
     * Custom view specific feedback. Subclasses should override.
     * @param fbs the list of strings
     * @param e the causal mouse event
     * @param position the current mouse (world wind) position
    */
    @Override
    protected void customFeedback(List<String> fbs, MouseEvent e, Position position) {
	String color = "lawn green";
	
	ViewFeedback.fbString(color, "map projection: " + getFlatGlobe().getProjection().getName(), fbs);
    }

}
