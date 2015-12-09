package demo;

import java.awt.EventQueue;

import cnuphys.bCNU.application.BaseMDIApplication;
import cnuphys.bCNU.log.Log;
import cnuphys.bCNU.util.Environment;
import cnuphys.bCNU.util.FileUtilities;
import cnuphys.bCNU.util.PropertySupport;
import cnuphys.bCNU.view.LogView;
import cnuphys.bCNU.view.ViewManager;
import wwview.FlatView;
import wwview.GlobeView;

public class DemoApp extends BaseMDIApplication {

    // the singleton
    private static DemoApp instance;

    /**
     * Constructor (private--used to create singleton)
     * 
     * @param keyVals an optional variable length list of attributes in
     *            type-value pairs. For example, AttributeType.NAME,
     *            "my application", AttributeType.CENTER, true, etc.
     */
    private DemoApp(Object... keyVals) {
	super(keyVals);
    }

    /**
     * Public access to the singleton.
     * 
     * @return the singleton (the main application frame.)(
     */
    public static DemoApp getInstance() {
	if (instance == null) {
	    instance = new DemoApp(PropertySupport.TITLE,
		    "Demo Application World Wind on bCNU",
		    PropertySupport.FRACTION, 0.8);

	    instance.addInitialViews();
	}
	return instance;
    }

    /**
     * Add the initial views to the desktop.
     */
    private void addInitialViews() {

	// add logview
	LogView logView = new LogView();
	logView.setVisible(false);
	ViewManager.getInstance().getViewMenu().addSeparator();
	
	//add a globe view
	GlobeView globeView = new GlobeView();
	globeView.setVisible(true);
	
	//add a flat view
	FlatView flatView = new FlatView();
	flatView.setVisible(true);

	// log some environment info
	Log.getInstance().config(Environment.getInstance().toString());

    }

    /**
     * Main program used for testing only.
     * <p>
     * Command line arguments:</br>
     * -p [dir] dir is the optional default directory for the file manager
     * 
     * @param arg the command line arguments.
     */
    public static void main(String[] arg) {

	if ((arg != null) && (arg.length > 0)) {
	    int len = arg.length;
	    int lm1 = len - 1;
	    boolean done = false;
	    int i = 0;
	    while (!done) {
		if (arg[i].equalsIgnoreCase("-p")) {
		    if (i < lm1) {
			i++;
			FileUtilities.setDefaultDir(arg[i]);
		    }
		}
		i++;
		done = (i >= lm1);
	    }
	}

	final DemoApp frame = getInstance();

	// now make the frame visible, in the AWT thread
	EventQueue.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		frame.setVisible(true);
	    }

	});
	Log.getInstance().error("DemoApp is ready.");
    }
}