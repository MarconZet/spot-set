package pl.marconzet.spotset.security


import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${spring.profiles.active:}")
    lateinit var activeProfile: String

    override fun configure(http: HttpSecurity?) {
        http {
            logout {
                logoutSuccessUrl = "/"
                logoutUrl = "/logout"
            }
            authorizeRequests {
                authorize("/workspace/**", hasRole("USER"))
                authorize(anyRequest, permitAll)
            }
            oauth2Login {
                defaultSuccessUrl("/workspace", false)
            }

            if(activeProfile.contains("dev")) {
                csrf {
                    disable()
                }
                headers {
                    frameOptions {
                        disable()
                    }
                }
            }
        }
    }
}