package com.example.testkotlinjwt.entity

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

import java.io.Serializable

@Entity
@Table(name = "user")
@Data
data class User(
    @Column(name = "name", nullable = false)
    var name : String,
    @Column(name = "password", nullable = false)
    var password : String,
    @Column(name = "email", nullable = false, unique = true)
    var email : String,
    @Column(name = "admin_flag" , nullable = false)
    var admin : Boolean,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=null
): Serializable