package studio.koerniax.simpleweatherapps.data.repository

import kotlinx.coroutines.flow.Flow
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation

/**
 * Created by KOERNIAX at 16/03/22
 */
interface FavoriteLocationRepository {

    suspend fun fetchAllDataAsync(): Flow<List<FavoriteLocation>>

    suspend fun insertData(data: FavoriteLocation): Long

    suspend fun deleteDataById(id: Long): Int

}