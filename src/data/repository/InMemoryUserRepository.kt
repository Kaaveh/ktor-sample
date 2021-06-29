package com.example.data.repository

class InMemoryUserRepository : UserRepository {

    private val credentialsToUsers = mapOf<String, UserRepository.User>(
        "admin:admin" to UserRepository.User(1, "admin"),
        "kaaveh:1234" to UserRepository.User(2, "kaaveh")
    )

    override fun getUser(username: String, password: String) = credentialsToUsers["$username:$password"]
}