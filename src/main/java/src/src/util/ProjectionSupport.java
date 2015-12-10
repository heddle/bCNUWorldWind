package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import gov.nasa.worldwind.globes.GeographicProjection;
import gov.nasa.worldwind.globes.projections.ProjectionEquirectangular;
import gov.nasa.worldwind.globes.projections.ProjectionMercator;
import gov.nasa.worldwind.globes.projections.ProjectionModifiedSinusoidal;
import gov.nasa.worldwind.globes.projections.ProjectionPolarEquidistant;
import gov.nasa.worldwind.globes.projections.ProjectionSinusoidal;
import gov.nasa.worldwind.globes.projections.ProjectionTransverseMercator;
import gov.nasa.worldwind.globes.projections.ProjectionUPS;
import gov.nasa.worldwind.globes.projections.ProjectionUTM;
import wwview.FlatView;

public class ProjectionSupport {

    public enum ProjectionType {
	Equirectangular, 
	Mercator,
	ModifiedSinusoidal,
//	PolarEquidistant,
	Sinusoidal;
//	TransverseMercator,
//	UPS,
//	UTM;
	
	public static ProjectionType fromName(String name) {
	    for (ProjectionType pt : values()) {
		if (pt.name().equalsIgnoreCase(name)) {
		    return pt;
		}
	    }
	    return null;
	}
    }
    
    public static GeographicProjection getProjection(ProjectionType type) {
	GeographicProjection proj = null;
	switch(type) {
	case Equirectangular:
	    proj = new ProjectionEquirectangular();
	    break;
	    
	case Mercator:
	    proj = new ProjectionMercator();
	    break;
	    
	case ModifiedSinusoidal:
	    proj = new ProjectionModifiedSinusoidal();
	    break;
	    
//	case PolarEquidistant:
//	    proj = new ProjectionPolarEquidistant();
//	    break;
//	    
	case Sinusoidal:
	    proj = new ProjectionSinusoidal();
	    break;
	    
//	case TransverseMercator:
//	    proj = new ProjectionTransverseMercator();
//	    break;
//	    
//	case UPS:
//	    proj = new ProjectionUPS();
//	    break;
//	    
//	case UTM:
//	    proj = new ProjectionUTM();
//	    break;
//	    
	}
	
	return proj;
    }
    
    /**
     * Get the projection type
     * @param view the view in question
     * @return the projection type
     */
    public static ProjectionType currentProjectionType(FlatView view) {
	ProjectionType type = ProjectionType.Equirectangular;
	
	if ((view == null) || (view.getFlatGlobe() == null) || (view.getFlatGlobe().getProjection() == null)) {
	    return type;
	}
	GeographicProjection proj = view.getFlatGlobe().getProjection();

	if (proj instanceof ProjectionEquirectangular) {
	    type = ProjectionType.Equirectangular;
	}
	else if (proj instanceof ProjectionMercator) {
	    type = ProjectionType.Mercator;
	}
	else if (proj instanceof ProjectionModifiedSinusoidal) {
	    type = ProjectionType.ModifiedSinusoidal;
	}
//	else if (proj instanceof ProjectionPolarEquidistant) {
//	    type = ProjectionType.PolarEquidistant;
//	}
	else if (proj instanceof ProjectionSinusoidal) {
	    type = ProjectionType.Sinusoidal;
	}
//	else if (proj instanceof ProjectionTransverseMercator) {
//	    type = ProjectionType.TransverseMercator;
//	}
//	else if (proj instanceof ProjectionUPS) {
//	    type = ProjectionType.UPS;
//	}
//	else if (proj instanceof ProjectionUTM) {
//	    type = ProjectionType.UTM;
//	}
	
	return type;
    }
    
    /**
     * Get a menu for selecting projection
     * @param fv the flat view that will house the menu
     * @return the projection menu
     */
    public static JMenu getProjectionMenu(final FlatView fv) {
	JMenu menu = new JMenu("Projection");
	ButtonGroup bg = new ButtonGroup();
	ProjectionType ctype = currentProjectionType(fv);
	
	for (ProjectionType tt : ProjectionType.values()) {
	    addMenuItem(fv, menu, tt.name(), bg, tt == ctype);
	}
	return menu;
    }
    
    private static void addMenuItem(final FlatView fv, JMenu menu, String label, ButtonGroup bg, boolean selected) {
	JRadioButtonMenuItem mitem = new JRadioButtonMenuItem(label, selected);
	bg.add(mitem);
	menu.add(mitem);
	
	ActionListener al = new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		String pstr = e.getActionCommand();
		ProjectionType ctype = currentProjectionType(fv);

		if (!ctype.name().equalsIgnoreCase(pstr)) {
		    ProjectionType newType = ProjectionType.fromName(pstr);
		    if (newType != null) {
			GeographicProjection proj = getProjection(newType);
			fv.getFlatGlobe().setProjection(proj);
			fv.refresh();
		    }
		    
		}
	    }

	};
	
	mitem.addActionListener(al);
    }
}
