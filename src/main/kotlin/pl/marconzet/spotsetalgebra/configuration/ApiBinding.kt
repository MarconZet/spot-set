package pl.marconzet.spotsetalgebra.configuration

import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

abstract class ApiBinding(private val accessToken: String?) {
    val restTemplate: RestTemplate = RestTemplate().apply { interceptors.add(interceptor()) }


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