package wwcomponent;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;

public class GlobePanel extends WorldWindowGLJPanel {

	public GlobePanel() {
		setModel(new BasicModel());
	}
}
