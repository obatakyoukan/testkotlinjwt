package com.example.testkotlinjwt.repository

import com.example.testkotlinjwt.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Long>{
    fun findByEmail(email : String) : User?
    fun findFirstByName(name : String) : User?
}