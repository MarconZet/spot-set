package pl.marconzet.spotset.security

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import pl.marconzet.spotset.webapi.SpotifyConfig
import pl.marconzet.spotset.data.model.SsUser
import pl.marconzet.spotset.repository.SsUserRepository

@Service
class LoginOAuth2UserService(
    private val ssUserRepository: SsUserRepository,
    private val spotifyConfig: SpotifyConfig
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        if (userRequest?.clientRegistration?.clientName != spotifyConfig.clientName)
            throw IllegalStateException("Only spotify client is handled")

        val oAuth2UserInfo = super.loadUser(userRequest)

        val user = ssUserRepository.getSsUserBySpotifyId(oAuth2UserInfo.name)
            ?: ssUserRepository.save(SsUser(0, oAuth2UserInfo.name, emptyList()))

        return SpotifyOAuth2User(user, oAuth2UserInfo.attributes, oAuth2UserInfo.authorities)
    }
}