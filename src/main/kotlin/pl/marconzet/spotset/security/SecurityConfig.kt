package pl.marconzet.spotset.security


import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
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