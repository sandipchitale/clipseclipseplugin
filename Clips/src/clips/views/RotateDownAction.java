package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

/**
 * This command rotates the clips down.
 *
 * @author Sandip V. Chitale
 *
 */
public class RotateDownAction implements IViewActionDelegate {

    public void init(IViewPart view) {
    }

    public void run(IAction action) {
        ClipsModel.getINSTANCE().rotateDown();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(ClipsModel.getINSTANCE().get().length > 1);
    }

}
