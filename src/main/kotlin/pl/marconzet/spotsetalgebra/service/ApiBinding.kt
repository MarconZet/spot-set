package pl.marconzet.spotsetalgebra.service

import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.client.RestTemplate

class ApiBinding(private val accessToken: String?) {
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