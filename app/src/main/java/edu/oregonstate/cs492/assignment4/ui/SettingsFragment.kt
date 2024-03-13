package edu.oregonstate.cs492.assignment4.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.SavedCity

/**
 * This fragment represents a settings screen for the app.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel: SavedCityViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val cityName: Preference? = findPreference(getString(R.string.pref_city_key))

        cityName?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val timeStamp = System.currentTimeMillis()

            if (newValue is String) {
                val newCity = SavedCity(newValue, timeStamp)
                viewModel.addNewCity(newCity)
                true
            } else {
                false
            }
        }
    }


}