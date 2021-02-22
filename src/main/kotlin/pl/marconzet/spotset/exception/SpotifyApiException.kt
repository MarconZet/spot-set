package pl.marconzet.spotset.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.marconzet.spotset.webapi.api.ErrorResponse

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
class SpotifyApiException(
    error: ErrorResponse
) : RuntimeException("Spotify replayed with ${error.error.status}. Message: ${error.error.message}")