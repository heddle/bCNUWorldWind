package wwcomponent;

import java.awt.Component;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLJPanel;
import gov.nasa.worldwind.globes.EarthFlat;

public class FlatPanel extends WorldWindowGLJPanel {

	public FlatPanel() {
		BasicModel bm = new BasicModel();
		EarthFlat earthFlat = new EarthFlat();
		bm.setGlobe(earthFlat);
		setModel(bm);
	}

}
