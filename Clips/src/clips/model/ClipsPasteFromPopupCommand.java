package clips.model;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.texteditor.ITextEditor;

import clips.views.ClipLabelProvider;

public class ClipsPasteFromPopupCommand implements IHandler {

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

		String[] clips = ClipsModel.getINSTANCE().get();
		if (clips.length == 0) {
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
					StyledText styledText = (StyledText) control;
                    if (!styledText.getEditable()) {
                        activeWorkbenchWindow.getShell().getDisplay()
                                .beep();
                        return null;
                    }
                    ListDialog listDialog = new ListDialog(activeWorkbenchWindow.getShell());  
                    listDialog.setAddCancelButton(true);  
                    listDialog.setContentProvider(new ArrayContentProvider());  
                    listDialog.setLabelProvider(new ClipLabelProvider());  
                    listDialog.setInput(clips);  
                    listDialog.setTitle("Select a clip to insert");  
                    if (listDialog.open() == Window.OK) {
                    	Object[] results = listDialog.getResult();
                    	if (results.length == 0) {
                    		return null;
                    	}
                    	StringBuilder stringBuilder = new StringBuilder();
                    	for (Object result:results) {
                    		stringBuilder.append(String.valueOf(result));
                    	}
                    	String textToInsert = stringBuilder.toString();
                        int caretOffset = styledText.getCaretOffset();
                        styledText.insert(textToInsert);
                        styledText.setSelection(caretOffset
                                + textToInsert.length(), caretOffset);
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
				return ClipsModel.getINSTANCE().get().length > 0;
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