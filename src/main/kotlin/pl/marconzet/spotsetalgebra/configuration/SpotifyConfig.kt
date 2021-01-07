package pl.marconzet.spotsetalgebra.configuration

import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyConfig {

//    @Bean
//    @RequestScope
//    fun spotify(authorizedClientService: OAuth2AuthorizedClientService): Spotify {
//        return try {
//            val authentication = SecurityContextHolder.getContext().authentication
//            val token: OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
//            val client = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
//                token.authorizedClientRegistrationId,
//                token.name
//            )
//            val accessToken = client.accessToken.tokenValue
//            Spotify(accessToken)
//        } catch (e: Throwable){
//            Spotify(null)
//        }
//    }

}