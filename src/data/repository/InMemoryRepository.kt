package com.example.data.repository

import com.example.data.model.DraftItem
import com.example.data.model.Item

class InMemoryRepository : RepositoryInterface {

    private val items = mutableListOf(
        Item(0, "zero", false),
        Item(1, "one", false),
        Item(2, "two", false),
        Item(3, "three", false),
        Item(4, "four", false),
        Item(5, "five", false),
    )

    override fun getAllItems(): List<Item> = items
    override fun getItem(id: Int): Item? = items.firstOrNull { it.id == id }
    override fun addItem(draftItem: DraftItem) {
        val item = Item(
            id = items.size + 1,
            message = draftItem.message,
            isDone = draftItem.isDone
        )
        items.add(item)
    }

    override fun removeItem(id: Int) {
        items.removeIf { it.id == id }
    }

    override fun updateItem(id: Int, draftItem: DraftItem) {
        val item = items.firstOrNull { it.id == id }
        item?.let { newItem ->
            newItem.apply {
                message = draftItem.message
                isDone = draftItem.isDone
            }
            items[id] = newItem
        }
    }
}