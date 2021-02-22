package pl.marconzet.spotset.webapi.api

data class ErrorResponse(val error: Error)
data class Error(val status: Int, val message: String)
