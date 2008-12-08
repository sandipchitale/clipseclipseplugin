package clips.views;

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

/**
 * This action pastes the clip selected in Clips view into the active IDE text
 * editor.
 * 
 * @author Sandip V. Chitale
 * 
 */
public class PasteAction implements IViewActionDelegate {

    private ClipsView view;
    
    private int candidateInsertIndex = 0;
    private long lastPasteTimeInMillis = -1L;

    public void init(IViewPart view) {
        this.view = (ClipsView) view;
    }

    @SuppressWarnings("unchecked")
    public void run(IAction action) {
        ISelectionProvider selectionProvider = view.getViewSite()
                .getSelectionProvider();
        if (selectionProvider != null) {
            ISelection selection = selectionProvider.getSelection();
            String textToInsert = null;
            if (selection instanceof IStructuredSelection
                    && ((IStructuredSelection) selection).size() > 0) {
                IStructuredSelection structuredSelection = (IStructuredSelection) selection;
                StringBuilder stringBuilder = new StringBuilder();
                Iterator iterator = structuredSelection.iterator();
                while (iterator.hasNext()) {
                    stringBuilder.append((String) iterator.next());
                }
                textToInsert = stringBuilder.toString();
            } else {
            	TreeItem[] items = view.getViewer().getTree().getItems();
                if (items.length == 0) {
                    return;
                }
            	String[] clips = new String[items.length];
            	for (int i = 0; i < items.length; i++) {
            		clips[i] = items[i].getText();
            	}
                long currentTimeMillis = System.currentTimeMillis();
                if ((lastPasteTimeInMillis == -1)
                        || (currentTimeMillis - lastPasteTimeInMillis) > 1000L) {
                    candidateInsertIndex = 0;
                } else {
                    candidateInsertIndex++;
                }
                lastPasteTimeInMillis = currentTimeMillis;
                textToInsert = clips[candidateInsertIndex % clips.length];
            }
            if (textToInsert != null) {
                view.insertSelection(textToInsert);
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(false);
		if ((ClipsModel.getINSTANCE().get().length > 0)
				|| (selection instanceof IStructuredSelection && ((IStructuredSelection) selection)
						.size() > 0)) {
			action.setEnabled(true);
			return;
		}
    }

}
