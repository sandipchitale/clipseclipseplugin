package clips.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class EditAction implements IViewActionDelegate {

    private ClipsView view;

    public void init(IViewPart view) {
        this.view = (ClipsView) view;
    }

    public void run(IAction action) {
        TreeViewer viewer = view.getViewer();
        ISelection selection = viewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (ITreeSelection) selection;
            Object firstElement = structuredSelection.getFirstElement();
            if (firstElement instanceof String) {
                InputDialog inputDialog = new InputDialog(view.getViewSite().getShell()
                        ,"Edit clip"
                        ,"Edit clips:"
                        ,(String) firstElement
                        ,null) {
                    private Text text;
                    
                    @Override
                    protected Control createDialogArea(Composite parent) {
                        Composite composite = new Composite(parent, SWT.NONE);
                        GridLayout layout = new GridLayout();
                        layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
                        layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
                        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
                        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
                        composite.setLayout(layout);
                        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
                        text = new Text(composite, SWT.MULTI | SWT.BORDER);
                        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
                        layoutData.widthHint = 400;
                        layoutData.heightHint = 300;
                        text.setLayoutData(layoutData);
                        applyDialogFont(composite);
                        return composite;
                    }
                    
                    @Override
                    public String getValue() {
                        return text.getText();
                    }
                    
                };
                
                if (inputDialog.open() == Window.OK) {
                    
                }
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

}
