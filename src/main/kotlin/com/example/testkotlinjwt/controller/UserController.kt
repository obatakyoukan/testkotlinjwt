package com.example.testkotlinjwt.controller

import com.example.testkotlinjwt.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @Value("\${security.secret-key:secret}")
    private var secretKey : String = "secret"

    @GetMapping("/hello")
    fun hello() : String {
        return "hello"
    }




}