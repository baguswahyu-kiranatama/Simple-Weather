package studio.koerniax.simpleweatherapps.data.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by KOERNIAX at 16/03/22
 */

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.Error(null, throwable.message)
                is HttpException -> ResultWrapper.Error(throwable.code(), throwable.message)
                else -> ResultWrapper.Error(null, "Unknown Error")
            }
        }
    }
}