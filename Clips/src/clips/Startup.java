package clips;

import org.eclipse.ui.IStartup;

import clips.model.ClipsModel;

public class Startup implements IStartup {

	public void earlyStartup() {
		ClipsModel.getINSTANCE();
	}

}
