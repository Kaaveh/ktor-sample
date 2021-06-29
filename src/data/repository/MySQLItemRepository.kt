package com.example.data.repository

import com.example.data.model.DraftItem
import com.example.data.model.Item
import com.example.data.repository.database.DatabaseManager

class MySQLItemRepository : RepositoryInterface {

    private val database = DatabaseManager()

    override fun getAllItems() = database.getAllItems().map { Item(it.id, it.message, it.isDone) }

    override fun getItem(id: Int): Item? =
        database.getItem(id)?.let {
            Item(it.id, it.message, it.isDone)
        }

    override fun addItem(draftItem: DraftItem) = database.addItem(draftItem)

    override fun removeItem(id: Int) = database.removeItem(id)

    override fun updateItem(id: Int, draftItem: DraftItem) = database.updateItem(id, draftItem)
}