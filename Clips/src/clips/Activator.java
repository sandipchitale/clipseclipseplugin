package clips;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Sandip V. Chitale
 * 
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "Clips";

    public static final String TEXT = "icons/clip.png";

    public static final String AUTO_CLIP_CUT_COPY = "autoClipCutAndCopy";
    public static final String MAX_CLIPS_COUNT = "maxClips";

    private static final boolean defaultAUTO_CLIP_CUT_COPY = false;
    private static final int defaultMAX_CLIPS_COUNT = 64;

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        store.setDefault(AUTO_CLIP_CUT_COPY, defaultAUTO_CLIP_CUT_COPY);
        store.setDefault(MAX_CLIPS_COUNT, defaultMAX_CLIPS_COUNT);
    }

    public boolean isAutoClipCutCopy() {
        return getPreferenceStore().getBoolean(AUTO_CLIP_CUT_COPY);
    }

    public int getMaxClipsCount() {
        return getPreferenceStore().getInt(MAX_CLIPS_COUNT);
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        reg.put(TEXT, createImageDescriptor(TEXT));
    }

    private ImageDescriptor createImageDescriptor(String id) {
        return imageDescriptorFromPlugin(PLUGIN_ID, id);
    }

}
