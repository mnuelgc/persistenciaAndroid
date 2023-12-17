package es.ua.eps.exercice2

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class BasicPreferencesSettings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.basic_preferences, rootKey)
    }
}