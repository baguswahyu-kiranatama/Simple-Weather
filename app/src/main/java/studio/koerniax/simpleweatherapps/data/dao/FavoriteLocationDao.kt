package studio.koerniax.simpleweatherapps.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation

/**
 * Created by KOERNIAX at 16/03/22
 */

@Dao
interface FavoriteLocationDao {

    @Query("SELECT * FROM ${FavoriteLocation.TABLE_NAME}")
    fun getAllDataAsync(): Flow<List<FavoriteLocation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: FavoriteLocation): Long

    @Query("DELETE FROM ${FavoriteLocation.TABLE_NAME} where id = :id")
    suspend fun deleteDataById(id: Long): Int

}