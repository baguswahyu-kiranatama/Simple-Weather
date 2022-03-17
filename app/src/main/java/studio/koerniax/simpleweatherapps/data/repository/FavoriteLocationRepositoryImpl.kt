package studio.koerniax.simpleweatherapps.data.repository

import kotlinx.coroutines.flow.Flow
import studio.koerniax.simpleweatherapps.data.dao.FavoriteLocationDao
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation

/**
 * Created by KOERNIAX at 16/03/22
 */
class FavoriteLocationRepositoryImpl(
    private val dao: FavoriteLocationDao
) : FavoriteLocationRepository {

    override suspend fun fetchAllDataAsync(): Flow<List<FavoriteLocation>> {
        return dao.getAllDataAsync()
    }

    override suspend fun insertData(data: FavoriteLocation): Long {
        return dao.insertData(data)
    }

    override suspend fun deleteDataById(id: Long): Int {
        return dao.deleteDataById(id)
    }

}