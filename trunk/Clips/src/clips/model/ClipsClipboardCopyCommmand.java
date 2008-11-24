package clips.model;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


public class ClipsClipboardCopyCommmand implements IHandler {

	public void addHandlerListener(IHandlerListener handlerListener) {}

	public void dispose() {}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return null;
		}
		Clipboard clipboard = new Clipboard(activeWorkbenchWindow.getShell().getDisplay());
		Object contents = clipboard.getContents(TextTransfer.getInstance());
		if (contents instanceof String) {
			ClipsModel.getINSTANCE().add((String) contents);
		}
		return null;
	}

	public boolean isEnabled() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null) {
			return false;
		}
		Clipboard clipboard = new Clipboard(activeWorkbenchWindow.getShell().getDisplay());
		Object contents = clipboard.getContents(TextTransfer.getInstance());
		if (contents instanceof String) {
			return true;
		}
		return false;
	}

	public boolean isHandled() {
		return isEnabled();
	}

	public void removeHandlerListener(IHandlerListener handlerListener) {}

}
