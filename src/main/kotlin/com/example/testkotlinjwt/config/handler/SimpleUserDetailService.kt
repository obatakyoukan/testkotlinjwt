package com.example.testkotlinjwt.config.handler

import com.example.testkotlinjwt.entity.User
import com.example.testkotlinjwt.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class SimpleUserDetailService : UserDetailsService {

    private var userRepository : UserRepository

    constructor(userRepository: UserRepository){
        this.userRepository = userRepository
    }

    override fun loadUserByUsername(email: String?): UserDetails? {
        println("loadUserByUsername")
        if( email == null ) {
            return null
        }

        var user : User? = userRepository.findByEmail( email )
        if( user == null ){
            UsernameNotFoundException("user not found")
            return null
        }
        println(user)
        return SimpleLoginUser(user)
    }

}