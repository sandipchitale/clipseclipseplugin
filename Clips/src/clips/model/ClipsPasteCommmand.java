package clips.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

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
	
	private int candidateInsertIndex = 0;
	private long lastPasteTimeInMillis = -1L;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		String[] clips = ClipsModel.getINSTANCE().get();
		if (clips.length == 0) {
		    return null;
		}
		
		long currentTimeMillis = System.currentTimeMillis();
		if ((lastPasteTimeInMillis == -1) || (currentTimeMillis - lastPasteTimeInMillis) > 1000L) {
	        candidateInsertIndex = 0;
		} else {
		    candidateInsertIndex++;
		}
		lastPasteTimeInMillis = currentTimeMillis;
		
		String textToInsert = clips[candidateInsertIndex % clips.length];
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
	                    styledText.setSelection(caretOffset + textToInsert.length(), caretOffset);
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
