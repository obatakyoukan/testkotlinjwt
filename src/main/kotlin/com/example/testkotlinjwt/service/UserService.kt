package com.example.testkotlinjwt.service

import com.example.testkotlinjwt.entity.User
import com.example.testkotlinjwt.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun findByName(name : String) = userRepository.findFirstByName(name)
    fun findByEmail(email : String) = userRepository.findByEmail(email)
    fun findAll() = userRepository.findAll()

    fun addUser(name: String,password: String,email: String){
        val passwordEncoder : PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        var hash : String = passwordEncoder.encode(password)
        var user : User = User(name,hash,email,false)
        userRepository.save(user)
    }

    fun deleteUser(email: String){
        var user : User? = userRepository.findByEmail(email)
        if( user != null ){
            userRepository.delete(user)
        }
    }

}