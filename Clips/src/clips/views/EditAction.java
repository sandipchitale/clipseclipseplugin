package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import clips.model.ClipsModel;

public class EditAction implements IViewActionDelegate {

    private ClipsView view;

    public void init(IViewPart view) {
        this.view = (ClipsView) view;
    }

    private static class ClipDialog extends Dialog {

        private String clipText;
        private Text text;

        private ClipDialog(Shell shell, String clipText) {
            super(shell);
            setShellStyle(SWT.CLOSE | SWT.BORDER | SWT.TITLE | SWT.RESIZE);
            shell.setText("Edit Clip");
            if (clipText == null) {
                this.clipText = "";
            } else {
                this.clipText = clipText;
            }
        }
        
        @Override
        protected Control createDialogArea(Composite parent) {
            Composite composite = (Composite) super.createDialogArea(parent);
            text = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
            text.setText(clipText);
            GridData layoutData = new GridData(GridData.FILL_BOTH);
            layoutData.widthHint = 350;
            layoutData.heightHint = 200;
            text.setLayoutData(layoutData);
            clipText = null;
            return composite;
        }
        
        @Override
        protected void okPressed() {
            clipText = text.getText();
            super.okPressed();
        }
        
        String getText() {
            return clipText;
        }
        
    }
    
    public void run(IAction action) {
        TreeViewer viewer = view.getViewer();
        ISelection selection = viewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (ITreeSelection) selection;
            Object firstElement = structuredSelection.getFirstElement();
            if (firstElement instanceof String) {
                ClipDialog dialog = new ClipDialog(view.getViewSite().getShell(), (String) firstElement);
                if (dialog.open() == Window.OK) {
                    Tree tree = viewer.getTree();
                    TreeItem item = tree.getSelection()[0];
                    int indexOf = tree.indexOf(item);
                    ClipsModel.getINSTANCE().replace(indexOf, dialog.getText());
                }                
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(selection instanceof IStructuredSelection &&
                ((IStructuredSelection)selection).size() == 1);
    }

}
