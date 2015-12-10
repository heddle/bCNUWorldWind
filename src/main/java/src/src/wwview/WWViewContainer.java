package wwview;

import java.awt.Component;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D.Double;

import cnuphys.bCNU.feedback.FeedbackPane;
import cnuphys.bCNU.graphics.container.ContainerAdapter;
import cnuphys.bCNU.graphics.toolbar.BaseToolBar;
import cnuphys.bCNU.graphics.toolbar.ToolBarToggleButton;
import cnuphys.bCNU.graphics.world.WorldPolygon;
import cnuphys.bCNU.view.BaseView;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

public class WWViewContainer extends ContainerAdapter {

    //the parent view
    protected WWView _view;
    
    //the current eye position
    protected Position _currentEye;
    
    public WWViewContainer(WWView view) {
	_view = view;
    }
    
    @Override
    public Component getComponent() {
	return _view.getMainComponent();
    }
    
    @Override
    public void localToWorld(Point pp, Double wp) {
    }

    @Override
    public void worldToLocal(Point pp, Double wp) {
    }

    @Override
    public void worldToLocal(Rectangle r, java.awt.geom.Rectangle2D.Double wr) {
    }

    @Override
    public void localToWorld(Rectangle r, java.awt.geom.Rectangle2D.Double wr) {
    }

    @Override
    public void worldToLocal(Polygon polygon, WorldPolygon worldPolygon) {
    }

    @Override
    public void localToWorld(Polygon polygon, WorldPolygon worldPolygon) {
    }

    @Override
    public void worldToLocal(Point pp, double wx, double wy) {
    }

    @Override
    public void pan(int dh, int dv) {
    }
    

    @Override
    public void recenter(Point pp) {
	System.err.println("recenter");
    }

    @Override
    public void prepareToZoom() {
    }

    @Override
    public void restoreDefaultWorld() {
	_view.setEyePosition(_view.getDefaultEyePosition());
    }

    @Override
    public void scale(double scaleFactor) {
	_currentEye = _view.getEyePosition();
	double alt = _currentEye.getAltitude()*scaleFactor;
	Position eye = new Position(_currentEye.latitude, _currentEye.longitude, alt);
	_view.setEyePosition(eye);
    }

    @Override
    public void undoLastZoom() {
	if (_currentEye != null) {
	    Position temp = _view.getEyePosition();
	    _view.setEyePosition(_currentEye);
	    _currentEye = temp;
	}
    }
    
    @Override
    public void rubberBanded(Rectangle b) {
	_currentEye = _view.getEyePosition();
	Position p1 = _view.computePositionFromScreenPoint(b.x, b.y);
	Position p2 = _view.computePositionFromScreenPoint(b.x+b.width, b.y+b.height);
	
	Angle minLat;
	Angle minLon;
	Angle maxLat;
	Angle maxLon;
	
	if (p1.latitude.degrees < p1.latitude.degrees) {
	    minLat = p1.latitude;
	    maxLat = p2.latitude;
	}
	else {
	    minLat = p2.latitude;
	    maxLat = p1.latitude;
	}
	
	if (p1.longitude.degrees < p1.longitude.degrees) {
	    minLon = p1.longitude;
	    maxLon = p2.longitude;
	}
	else {
	    minLon = p2.longitude;
	    maxLon = p1.longitude;
	}
	
	_view.zoomToSector(minLat, minLon, maxLat, maxLon);
	refresh();
   }

    @Override
    public BaseToolBar getToolBar() {
	return _view.getToolBar();
    }

    @Override
    public void setToolBar(BaseToolBar toolBar) {
    }

    @Override
    public ToolBarToggleButton getActiveButton() {
	return null;
    }
    
    @Override
    public void locationUpdate(MouseEvent mouseEvent, boolean dragging) {
    }

    @Override
    public BaseView getView() {
	return _view;
    }
    
    @Override
    public FeedbackPane getFeedbackPane() {
	return null;
    }
    
    @Override
    public void refresh() {
	_view.refresh();
    }


    /**
     * The active toolbar button changed.
     * 
     * @param activeButton the new active button.
     */
    @Override
    public void activeToolBarButtonChanged(ToolBarToggleButton activeButton) {
	_view.activeToolBarButtonChanged(activeButton);
    }


}
