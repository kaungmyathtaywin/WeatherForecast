package edu.oregonstate.cs492.assignment4.data

class SavedCityRepository(
    private val dao: SavedCityDao
) {
    suspend fun insertNewCity(city: SavedCity) = dao.insert(city)

    suspend fun deleteSavedCity(city: SavedCity) = dao.delete(city)
}