package clips.views;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditor;

import clips.model.ClipsModel;

/**
 * This view shows the current set of clips. Several actions are available on
 * the view toolbar.
 *
 * @author Sandip V. Chitale
 *
 */
public class ClipsView extends ViewPart {
    private TreeViewer viewer;

    private ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (viewer != null) {
                viewer.refresh();
                viewer.setSelection(null);
            }
        }
    };

    public ClipsView() {
    }

    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);
        ClipsModel.getINSTANCE().addChangeListener(changeListener);
    }

    @Override
    public void dispose() {
        ClipsModel.getINSTANCE().removeChangeListener(changeListener);
        super.dispose();
    }

    @Override
    public void createPartControl(Composite parent) {
    	FilteredTree filteredTree = new FilteredTree(parent, SWT.MULTI, new PatternFilter()) {
    		@Override
    		protected void updateToolbar(boolean visible) {
    			ClipsView.this.getViewer().setSelection(ClipsView.this.getViewer().getSelection());
    			super.updateToolbar(visible);
    		}
    	};
//        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer = filteredTree.getViewer();
        viewer.setContentProvider(ClipsModel.getINSTANCE());
        viewer.setLabelProvider(new ClipLabelProvider());
        viewer.setInput(getViewSite());
        getSite().setSelectionProvider(viewer);

        MenuManager menuMgr = new MenuManager("#PopupMenu");
        menuMgr.setRemoveAllWhenShown(false);
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu("popup", menuMgr, viewer);

        viewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                ISelection selection = event.getSelection();
                String selectionToInsert = null;
                if (selection instanceof ITreeSelection
                        && ((ITreeSelection) selection).size() > 0) {
                    ITreeSelection treeSelection = (ITreeSelection) selection;
                    selectionToInsert = (String) treeSelection
                            .getFirstElement();
                } else {
                    selectionToInsert = ClipsModel.getINSTANCE().peek();
                }
                if (selectionToInsert != null) {
                    insertSelection(selectionToInsert);
                }
            }
        });
    }

    void insertSelection(String text) {
        IEditorPart activeEditor = getViewSite().getWorkbenchWindow()
                .getActivePage().getActiveEditor();
        if (activeEditor instanceof ITextEditor) {
        	ITextEditor editor = (ITextEditor) activeEditor;
            Object adapter = (Control) editor.getAdapter(Control.class);
            if (adapter instanceof Control) {
                Control control = (Control) adapter;
                if (control instanceof StyledText) {
                    StyledText styledText = (StyledText) control;
                    if (!styledText.getEditable()) {
                        getViewSite().getShell().getDisplay().beep();
                        return;
                    }
                    int caretOffset = styledText.getCaretOffset();
                    styledText.insert(text);
                    styledText.setSelection(caretOffset + text.length(),
                            caretOffset);
                }
            }
        }
    }

    TreeViewer getViewer() {
        return viewer;
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

}
