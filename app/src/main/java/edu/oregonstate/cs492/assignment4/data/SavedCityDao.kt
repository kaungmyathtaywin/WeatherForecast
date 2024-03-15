package edu.oregonstate.cs492.assignment4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: SavedCity)

    @Delete
    suspend fun delete(city: SavedCity)

    @Query("SELECT * FROM SavedCity")
    fun getAllCities(): Flow<List<SavedCity>>
}