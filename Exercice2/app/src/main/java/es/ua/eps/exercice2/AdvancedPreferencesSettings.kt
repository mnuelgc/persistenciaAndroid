package es.ua.eps.exercice2

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class AdvancedPreferencesSettings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.adv_preferences, rootKey)
    }
}