package dev.yunas.domain.exception


sealed class CityFinderException : Throwable() {
    data object NoInternetException : CityFinderException()
    data object UnexpectedException : CityFinderException()
}
