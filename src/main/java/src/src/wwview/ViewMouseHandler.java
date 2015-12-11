package wwview;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gov.nasa.worldwind.geom.Position;

public class ViewMouseHandler implements MouseListener, MouseMotionListener {

	//the parent view
	private WWView _view;
	
	//the current position
	private Position _currentPosition;
	
	//the press position
	private Position _pressPosition;
	
	//the release position
	private Position _releasePosition;
	
	//the current drag position
	private Position _dragPosition;


	public ViewMouseHandler(WWView view) {
		_view = view;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if we were rubber banding lets consume the event
		if (_view.isBoxZoomActive()) {
			e.consume();
		}
		
		//creating a rectangular grid
		else if (_view.isRectGridActive()) {
			System.err.println("CLICK with RectGrid Active");
			e.consume();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		_pressPosition = _view.getWorldWindow().getCurrentPosition();
		_releasePosition = null;
		_currentPosition = null;
		
		// if we were rubber banding lets consume the event
		if (_view.isBoxZoomActive()) {
			_view.getToolBar().getBoxZoomButton().mousePressed(e);
			e.consume();
		}
		
		//creating a rectangular grid
		else if (_view.isRectGridActive()) {
			System.err.println("PRESS with RectGrid Active");
			e.consume();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		_releasePosition = _view.getWorldWindow().getCurrentPosition();
		
		// if we were rubber banding lets consume the event
		if (_view.isBoxZoomActive()) {
			e.consume();
		}
		
		//creating a rectangular grid
		else if (_view.isRectGridActive()) {
			System.err.println("RELEASE with RectGrid Active");
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

		_dragPosition = _view.getWorldWindow().getCurrentPosition();
		
		// if we were rubber banding lets consume the event
		if (_view.isBoxZoomActive()) {
			e.consume();
		}
		
		//creating a rectangular grid
		else if (_view.isRectGridActive()) {
			System.err.println("DRAG with RectGrid Active");
			e.consume();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		_currentPosition = _view.getWorldWindow().getCurrentPosition();

		// do not change feedback if control is down
		if (!(e.isControlDown())) {
			_view.getViewFeedback().handleFeedback(
					_view.getFeedbackStringCollection(), e, _currentPosition);
		}
		
	}

}
