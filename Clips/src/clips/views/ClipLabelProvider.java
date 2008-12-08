/**
 * 
 */
package clips.views;

import java.util.regex.Pattern;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import clips.Activator;

public class ClipLabelProvider extends LabelProvider {
    @Override
    public String getText(Object element) {
        if (element instanceof String) {
            String text = (String) element;
            if (text == null) {
                return "";
            }
            int textLength = text.length();
            return text.replaceAll(Pattern.quote("\n"), "\u00B6")
                    .substring(0, Math.min(text.length(), 120))
                    + (textLength < 120 ? "" : "...");
        }

        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        return Activator.getDefault().getImageRegistry()
                .get(Activator.TEXT);
    }
}