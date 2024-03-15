package edu.oregonstate.cs492.assignment4.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.SavedCity

class AddCityDialogFragment : DialogFragment() {
    private lateinit var cityET: EditText
    private lateinit var prefs: SharedPreferences
    private val savedCityViewModel: SavedCityViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_add_city, null)
            cityET = view.findViewById(R.id.et_city_name)
            prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

            builder.setView(view)
                .setPositiveButton(R.string.add_new_city) { dialog, id ->
                    val cityName = cityET.text.toString()
                    val timeStamp = System.currentTimeMillis()
                    val newCity = SavedCity(cityName, timeStamp)
                    savedCityViewModel.addNewCity(newCity)

                    val editor = prefs.edit()
                    editor.putString(getString(R.string.pref_city_key), cityName)
                    editor.apply()

                    findNavController().navigate(R.id.current_weather)
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}