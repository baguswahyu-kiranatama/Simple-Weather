package studio.koerniax.simpleweatherapps.data.network

/**
 * Created by KOERNIAX at 16/03/22
 */
sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val message: String? = null) : ResultWrapper<Nothing>()
}
