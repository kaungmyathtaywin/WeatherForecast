package edu.oregonstate.cs492.assignment4.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.oregonstate.cs492.assignment4.data.AppDatabase
import edu.oregonstate.cs492.assignment4.data.SavedCity
import edu.oregonstate.cs492.assignment4.data.SavedCityRepository
import kotlinx.coroutines.launch

class SavedCityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SavedCityRepository(
        AppDatabase.getInstance(application).savedCityDao()
    )

    fun addNewCity(city: SavedCity) {
        viewModelScope.launch {
            repository.insertNewCity(city)
        }
    }

    fun removeSavedCity(city: SavedCity) {
        viewModelScope.launch {
            repository.deleteSavedCity(city)
        }
    }
}