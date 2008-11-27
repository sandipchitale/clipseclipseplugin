package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

/**
 * This action removes all clips.
 *
 * @author Sandip V. Chitale
 *
 */
public class RemoveAllAction implements IViewActionDelegate {

    public void init(IViewPart view) {
    }

    public void run(IAction action) {
        ClipsModel.getINSTANCE().removeAll();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(ClipsModel.getINSTANCE().get().length > 0);
    }

}
