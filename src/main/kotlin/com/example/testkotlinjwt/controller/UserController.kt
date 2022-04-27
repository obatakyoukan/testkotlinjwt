package com.example.testkotlinjwt.controller

import com.example.testkotlinjwt.entity.User
import com.example.testkotlinjwt.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @GetMapping("/test")
    fun test() : String{
        return "test"
    }

    @PostMapping("/add")
    fun add(
        @RequestParam("name")
        name : String,
        @RequestParam("password")
        password : String,
        @RequestParam("email")
        email : String
    ) : List<User>{
        userService.addUser(name,password,email)
        return userService.findAll()
    }

    @PostMapping("/post")
    fun posttest() : String {
        return "post success"
    }




}