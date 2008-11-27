package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

/**
 * This action rotates the clips up.
 *
 * @author Sandip V. Chitale
 *
 */
public class RotateUpAction implements IViewActionDelegate {

    public void init(IViewPart view) {
    }

    public void run(IAction action) {
        ClipsModel.getINSTANCE().rotateUp();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(ClipsModel.getINSTANCE().get().length > 1);
    }

}
