package pl.marconzet.spotset.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import pl.marconzet.spotset.data.model.SsUser

class SpotifyOAuth2User(
    val ssUser: SsUser,
    private val attributes: MutableMap<String, Any>,
    private val authorities: MutableCollection<out GrantedAuthority>
) : OAuth2User {

    override fun getName() = ssUser.id.toString()

    override fun getAttributes() = attributes

    override fun getAuthorities() = authorities


}