package wwview;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gov.nasa.worldwind.geom.Position;

public class ViewMouseHandler implements MouseListener, MouseMotionListener {

    private WWView _view;
    
    public ViewMouseHandler(WWView view) {
	_view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	// if we were rubber banding lets consume the event
	if (_view.isBoxZoomActive()) {
	    e.consume();
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
	// if we were rubber banding lets consume the event
	if (_view.isBoxZoomActive()) {
	    _view.getToolBar().getBoxZoomButton().mousePressed(e);
	    e.consume();
	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	// if we were rubber banding lets consume the event
	if (_view.isBoxZoomActive()) {
	    e.consume();
	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

	// if we were rubber banding lets consume the event
	if (_view.isBoxZoomActive()) {
	    e.consume();
	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
	
	Position position = _view.getWorldWindow().getCurrentPosition();
	
	//do not change feedback if control is down
	if (!(e.isControlDown())) {
	    _view.getViewFeedback().handleFeedback(_view.getFeedbackStringCollection(), e, position);
	}
    }

}
