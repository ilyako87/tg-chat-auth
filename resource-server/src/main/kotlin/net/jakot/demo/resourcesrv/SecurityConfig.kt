package net.jakot.demo.resourcesrv

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {


    @Bean
    @Throws(Exception::class)
    fun web(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .cors { obj: CorsConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/orders").authenticated()
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { jwt -> jwt.jwt {} }

        return http.build()
    }

}