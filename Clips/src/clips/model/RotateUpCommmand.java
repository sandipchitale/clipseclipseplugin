package clips.model;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

/**
 * This command rotates the clips up.
 *
 * @author Sandip V. Chitale
 *
 */
public class RotateUpCommmand implements IHandler {

    public void addHandlerListener(IHandlerListener handlerListener) {
    }

    public void dispose() {
    }

    public Object execute(ExecutionEvent event) throws ExecutionException {
        ClipsModel.getINSTANCE().rotateUp();
        return null;
    }

    public boolean isEnabled() {
        return ClipsModel.getINSTANCE().get().length > 1;
    }

    public boolean isHandled() {
        return isEnabled();
    }

    public void removeHandlerListener(IHandlerListener handlerListener) {
    }

}
