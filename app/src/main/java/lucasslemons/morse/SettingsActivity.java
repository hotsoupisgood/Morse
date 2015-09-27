package lucasslemons.morse;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by hairy on 2/9/15.
 */
public class SettingsActivity extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.prefrences);
    }
}