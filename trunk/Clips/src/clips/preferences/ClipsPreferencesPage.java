package clips.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import clips.Activator;

public class ClipsPreferencesPage extends FieldEditorPreferencePage implements
        IWorkbenchPreferencePage {

    public ClipsPreferencesPage() {
        super(FieldEditorPreferencePage.GRID);
    }

    public void init(IWorkbench workbench) {
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }
    
    @Override
    protected void createFieldEditors() {
        BooleanFieldEditor booleanFieldEditor = new BooleanFieldEditor(
                Activator.AUTO_CLIP_CUT_COPY, "AutoClip Cut and Copy",
                getFieldEditorParent());
        addField(booleanFieldEditor);

        IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(
                Activator.MAX_CLIPS_COUNT, "Maximum number clips:",
                getFieldEditorParent());
        addField(integerFieldEditor);
    }

}
