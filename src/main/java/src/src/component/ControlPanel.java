package component;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cnuphys.bCNU.feedback.FeedbackPane;

/**
 * A control panel
 * @author heddle
 *
 */
public class ControlPanel extends JPanel {

    //a tabbed pane for controls
    protected JTabbedPane _tabbedPane;
    
    //the feedback pane
    protected FeedbackPane _feedbackPane;
    
    public ControlPanel(int fbWidth) {
	setLayout(new BorderLayout(2, 2));
	
	_feedbackPane = addFeedbackPane(fbWidth);
    }
    
    /**
     * Create the feedback pane
     * @param fbWidth the desired pane width
     * @return the feedback pane
     */
    protected FeedbackPane addFeedbackPane(int fbWidth) {
	FeedbackPane fbpane = new FeedbackPane(fbWidth);
	add(fbpane, BorderLayout.CENTER);
	
	return fbpane;
    }
    
    /**
     * Get the feedback pane
     * @return the feedback pane
     */
    public FeedbackPane getFeedbackPane() {
	return _feedbackPane;
    }
    
}
