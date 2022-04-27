package com.example.testkotlinjwt.config

import com.example.testkotlinjwt.config.handler.*
import com.example.testkotlinjwt.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler
import org.springframework.web.filter.GenericFilterBean


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
                .mvcMatchers(HttpMethod.POST,"/add","/post")
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(SimpleAuthenticationEntrpoint())
                    .accessDeniedHandler(SimpleAccesssDeniedHandler())
            .and()
            .formLogin()
                .loginProcessingUrl("/login").permitAll()
                    .usernameParameter("email")
                    .passwordParameter("password")
                        .successHandler(authenticationsSuccessHandler())
                        .failureHandler(SimpleAuthenticationFailureHandler())
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(HttpStatusReturningLogoutSuccessHandler() )
            .and()
                .csrf()
                    .disable()
                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter::class.java )
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    /*
    override fun configure(web: WebSecurity?) {
        //super.configure(web)
    }
    */

    override fun configure(auth: AuthenticationManagerBuilder?) {
        //super.configure(auth)
        //println("AUTH CONFIGURE")
        auth!!.eraseCredentials(false)
            .userDetailsService( simpleUserDetailService() )
            .passwordEncoder(passwordEncoder())
    }

    fun simpleUserDetailService() : UserDetailsService {
        return SimpleUserDetailService(userRepository)
    }

    fun authenticationsSuccessHandler() : AuthenticationSuccessHandler{
        return SimpleAuthenticationSuccessHandler(secretKey)
    }

    fun tokenFilter() : GenericFilterBean{
        return SimpleTokenFilter(userRepository,secretKey)
    }

    fun passwordEncoder() : PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

}