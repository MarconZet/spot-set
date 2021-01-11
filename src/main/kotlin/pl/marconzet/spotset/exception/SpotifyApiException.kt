package pl.marconzet.spotset.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.marconzet.spotset.data.api.SpotifyError

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
class SpotifyApiException(
    error: SpotifyError
) : RuntimeException("Spotify replayed with ${error.status}. Message: ${error.message}")