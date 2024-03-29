package clips.model;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * This command copies the text selected in the active IDE text editor to
 * the Clips model.
 * 
 * @author Sandip V. Chitale
 *
 */
public class ClipsCopyCommmand implements IHandler {

    public void addHandlerListener(IHandlerListener handlerListener) {
    }

    public void dispose() {
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
            return null;
        }
        IEditorPart activeEditor = activeWorkbenchWindow.getActivePage()
                .getActiveEditor();
        if (activeEditor instanceof ITextEditor) {
        	ITextEditor editor = (ITextEditor) activeEditor;
            Object adapter = (Control) editor.getAdapter(Control.class);
            if (adapter instanceof Control) {
                Control control = (Control) adapter;
                if (control instanceof StyledText) {
                    ISelection selection = activeEditor.getEditorSite()
                            .getSelectionProvider().getSelection();
                    if (selection instanceof ITextSelection) {
                        ITextSelection textSelection = (ITextSelection) selection;
                        String text = textSelection.getText();
                        if (text.length() > 0) {
                            ClipsModel.getINSTANCE().add(text);
                            return null;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isEnabled() {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
            return false;
        }
        IEditorPart activeEditor = activeWorkbenchWindow.getActivePage()
                .getActiveEditor();
        if (activeEditor instanceof ITextEditor) {
        	ITextEditor editor = (ITextEditor) activeEditor;
            Object adapter = (Control) editor.getAdapter(Control.class);
            if (adapter instanceof Control) {
                return true;
            }
        }
        return false;
    }

    public boolean isHandled() {
        return isEnabled();
    }

    public void removeHandlerListener(IHandlerListener handlerListener) {
    }

}
