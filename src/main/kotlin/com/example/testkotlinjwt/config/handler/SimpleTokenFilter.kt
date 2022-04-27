package com.example.testkotlinjwt.config.handler

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.example.testkotlinjwt.entity.User
import com.example.testkotlinjwt.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.util.Optional
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class SimpleTokenFilter : GenericFilterBean {
    private var userRepository: UserRepository
    private var algorithm : Algorithm

    constructor(userRepository: UserRepository,secretKey : String){
        this.userRepository = userRepository
        this.algorithm = Algorithm.HMAC512(secretKey)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?)
    {
        var token : String? = resolveToken(request)
        if( token == null ){
            chain!!.doFilter(request,response)
            return
        }

        try{
            authentication(verifyToken(token))
        }catch ( e : JWTVerificationException ){
            //log verify error
            SecurityContextHolder.clearContext()
            ( response as HttpServletResponse ).sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.reasonPhrase )
        }
        chain!!.doFilter(request,response)
    }

    private fun resolveToken(request : ServletRequest?) : String?
    {
        if( request == null){
            logger.info("request is null in func resolveToken")
            return null
        }


        var token : String? =
            (request as HttpServletRequest)
                .getHeader("Authorization")

        logger.info("get token")
        logger.info(token)

        if( token == null || !token.startsWith("Bearer ")){
            return null
        }
        return token.substring(7)
    }

    private fun verifyToken(token : String) : DecodedJWT{
        var verifier : JWTVerifier = JWT.require(algorithm).build()
        return verifier.verify(token)
    }
    private fun authentication(jwt : DecodedJWT){
        var userId : Long = jwt.subject as Long
        var user : Optional<User> = userRepository.findById(userId)
        if( !user.isPresent() ){
            return
        }
        var simpleLoginUser : SimpleLoginUser = SimpleLoginUser(user.get())
        SecurityContextHolder.getContext()
            .authentication = UsernamePasswordAuthenticationToken(
                simpleLoginUser,
                null,
                simpleLoginUser.authorities
            )

    }


}