package com.example.data.repository

import com.example.data.model.DraftItem
import com.example.data.model.Item

interface RepositoryInterface {
    fun getAllItems(): List<Item>
    fun getItem(id: Int): Item?
    fun addItem(draftItem: DraftItem)
    fun removeItem(id: Int)
    fun updateItem(id: Int, draftItem: DraftItem)
}

interface UserRepository {
    fun getUser(username: String, password: String): User?

    data class User(
        val userId: Int,
        val username: String,
    )
}