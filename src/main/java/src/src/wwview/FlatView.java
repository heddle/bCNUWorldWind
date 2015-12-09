package wwview;

import cnuphys.bCNU.util.PropertySupport;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
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

}
