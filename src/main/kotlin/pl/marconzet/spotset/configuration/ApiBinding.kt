package pl.marconzet.spotset.configuration

import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate
import pl.marconzet.spotset.logger

class ApiBinding(private val accessToken: String?) {
    val restTemplate: RestTemplate = RestTemplate().apply {
        interceptors.add(interceptor())
        errorHandler = RestTemplateErrorHandler()
        
    }

    private fun interceptor(): ClientHttpRequestInterceptor {
        return accessToken?.let {
            ClientHttpRequestInterceptor { request, body, execution ->
                request.headers.add("Authorization", "Bearer $it")
                execution.execute(request, body)
            }
        } ?: ClientHttpRequestInterceptor { _, _, _ ->
            throw IllegalStateException()
        }
    }
}