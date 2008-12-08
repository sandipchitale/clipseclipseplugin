package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

public class MoveDownAction implements IViewActionDelegate {

    private ClipsView view;

    public void init(IViewPart view) {
        this.view = (ClipsView) view;
    }

    public void run(IAction action) {
        TreeViewer viewer = view.getViewer();
        TreePath treePath = ((ITreeSelection)viewer.getSelection()).getPaths()[0];
        Tree tree = viewer.getTree();
        TreeItem item = tree.getSelection()[0];
        int indexOf = tree.indexOf(item);
        if (ClipsModel.getINSTANCE().moveDown(indexOf)) {
            viewer.setSelection(new TreeSelection(treePath));
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(view.getViewer().getTree().getItemCount() == ClipsModel.getINSTANCE().get().length &&
        		selection instanceof IStructuredSelection &&
                ((IStructuredSelection)selection).size() == 1);
    }
}
