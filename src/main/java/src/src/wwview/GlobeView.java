package wwview;

import cnuphys.bCNU.util.PropertySupport;
import gov.nasa.worldwind.geom.Angle;
import wwcomponent.GlobePanel;

public class GlobeView extends WWView {

    //world wind flat panel
    private GlobePanel _wwGlobePanel;
    
    // reserved view type for log view
    public static final int GLOBEVIEWTYPE = 482641;

    public GlobeView() {
	super(PropertySupport.TITLE, "Globe View", PropertySupport.ICONIFIABLE,
		true, PropertySupport.MAXIMIZABLE, true,
		PropertySupport.CLOSABLE, true, PropertySupport.RESIZABLE, true,
		PropertySupport.WIDTH, 800, PropertySupport.HEIGHT, 600,
		PropertySupport.VISIBLE, false, PropertySupport.VIEWTYPE,
		GLOBEVIEWTYPE);
	
	_wwGlobePanel = new GlobePanel();
	_wwWindow = _wwGlobePanel;
	 prepare(_wwGlobePanel, _wwGlobePanel, Angle.ZERO, Angle.ZERO, 11520000);
   }

}
