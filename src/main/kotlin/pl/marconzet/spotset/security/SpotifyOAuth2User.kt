package pl.marconzet.spotset.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import pl.marconzet.spotset.data.model.User

class SpotifyOAuth2User(
    private val user: User,
    private val attributes: MutableMap<String, Any>,
    private val authorities: MutableCollection<out GrantedAuthority>
) : OAuth2User {

    override fun getName() = user.id?.toString() ?: throw IllegalStateException()

    override fun getAttributes() = attributes

    override fun getAuthorities() = authorities


}