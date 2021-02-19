package pl.marconzet.spotset.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import pl.marconzet.spotset.data.api.ErrorResponse
import pl.marconzet.spotset.exception.SpotifyApiException

class RestTemplateErrorHandler : ResponseErrorHandler {
    private val mapper = jacksonObjectMapper()
    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.isError
    }
    override fun handleError(response: ClientHttpResponse) {
        val error = mapper.readValue(response.body, ErrorResponse::class.java)
        throw SpotifyApiException(error)
    }

}