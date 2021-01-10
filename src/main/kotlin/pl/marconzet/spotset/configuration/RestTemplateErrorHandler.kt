package pl.marconzet.spotset.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import pl.marconzet.spotset.data.api.Error
import pl.marconzet.spotset.exception.SpotifyApiException

class RestTemplateErrorHandler : ResponseErrorHandler {
    private val mapper = jacksonObjectMapper()
    override fun hasError(response: ClientHttpResponse): Boolean {
        return response.statusCode.isError
    }

    override fun handleError(response: ClientHttpResponse) {
        val error: Error = mapper.readValue(response.body)
        throw SpotifyApiException(error)
    }

}