package pl.marconzet.spotset.data.api

data class ErrorResponse(val error: Error)
data class Error(val status: Int, val message: String)
