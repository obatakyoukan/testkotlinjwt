package com.example.testkotlinjwt.config

import com.example.testkotlinjwt.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Value("\${security.secret-key:secret}")
    private var secretKey : String = "secret"

    override fun configure(httpSecurity: HttpSecurity){
        httpSecurity
            .authorizeRequests()
                .mvcMatchers("/hello")
                    .permitAll()
                .anyRequest()
                    .authenticated()
    }

    /*
    override fun configure(web: WebSecurity?) {
        //super.configure(web)
    }
    */

    override fun configure(auth: AuthenticationManagerBuilder?) {
        //super.configure(auth)
    }

}