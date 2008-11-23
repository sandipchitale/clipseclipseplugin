package clips.views;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

public class RemoveAction implements IViewActionDelegate {

	private ClipsView view;

	public void init(IViewPart view) {
		this.view = (ClipsView) view;
	}

	public void run(IAction action) {
		TreeViewer viewer = view.getViewer();
		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (ITreeSelection) selection;
			Iterator iterator = structuredSelection.iterator();
			List<String> clipsToRemove = new LinkedList<String>();
			while (iterator.hasNext()) {
				Object item = iterator.next();
				if (item instanceof String) {
					clipsToRemove.add((String) item);
				}
			}
			if (clipsToRemove.size() > 0) {
				ClipsModel.getINSTANCE().remove(clipsToRemove);
			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
