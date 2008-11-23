package clips.model;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;

public class ClipsPasteCommmand implements IHandler {

	public void addHandlerListener(IHandlerListener handlerListener) {}

	public void dispose() {}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		String textToInsert = ClipsModel.getINSTANCE().peek();
		if (textToInsert != null) {
			IEditorPart activeEditor = activeWorkbenchWindow.getActivePage().getActiveEditor();
			if (activeEditor instanceof AbstractDecoratedTextEditor) {
				AbstractDecoratedTextEditor editor = (AbstractDecoratedTextEditor) activeEditor;
				Object adapter = (Control) editor.getAdapter(Control.class);
				if (adapter instanceof Control) {
					Control control = (Control) adapter;
					if (control instanceof StyledText) {
						StyledText styledText = (StyledText) control;
						if (!styledText.getEditable()) {
							activeWorkbenchWindow.getShell().getDisplay().beep();
							return null;
						}
						int caretOffset = styledText.getCaretOffset();
						styledText.insert(textToInsert);
						styledText.setCaretOffset(caretOffset + textToInsert.length());
					}
				}
			}
		}
		return null;
	}

	public boolean isEnabled() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return false;
		}
		IEditorPart activeEditor = activeWorkbenchWindow.getActivePage().getActiveEditor();
		if (activeEditor instanceof AbstractDecoratedTextEditor) {
			AbstractDecoratedTextEditor editor = (AbstractDecoratedTextEditor) activeEditor;
			Object adapter = (Control) editor.getAdapter(Control.class);
			if (adapter instanceof Control) {
				return ClipsModel.getINSTANCE().get().length > 0;
			}
		}
		return false;
	}

	public boolean isHandled() {
		return isEnabled();
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {}

}
