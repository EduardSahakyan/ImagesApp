package com.example.imagesapp.domain.resource

sealed interface Resource<out T> {

    data class Success<T>(val data: T) : Resource<T>

    data class Error(val error: RootError) : Resource<Nothing>

    data object Loading : Resource<Nothing>

    fun<T, R> Resource<T>.map(mapper: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(mapper(this.data))
            is Error -> Error(this.error)
            Loading -> Loading
        }
    }

}

sealed interface RootError {

    data class ServerError(val message: String) : RootError

    data object NetworkError : RootError

    data object InvalidCredentials : RootError

    data object UnknownImage : RootError
}