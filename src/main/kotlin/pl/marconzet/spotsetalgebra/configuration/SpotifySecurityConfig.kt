package pl.marconzet.spotsetalgebra.configuration


import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke


@EnableWebSecurity
class SpotifySecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            authorizeRequests {
                authorize("", permitAll)
                authorize("/", permitAll)
                authorize("/callback/**", permitAll)
                authorize("/oauth2/authorization/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {  }
        }
    }
}