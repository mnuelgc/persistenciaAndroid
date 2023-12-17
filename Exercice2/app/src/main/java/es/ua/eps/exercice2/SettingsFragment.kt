package es.ua.eps.exercice2

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<Preference>("basic_key")?.setOnPreferenceClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.settingFragment, BasicPreferencesSettings())
                ?.addToBackStack(null)
                ?.commit()
            true
        }

        findPreference<Preference>("advanced_key")?.setOnPreferenceClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.settingFragment, AdvancedPreferencesSettings())
                ?.addToBackStack(null)
                ?.commit()
            true
        }
    }
}