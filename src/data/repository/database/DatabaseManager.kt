package com.example.data.repository.database

import com.example.data.model.DraftItem
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    // config
    private val hostname = "vm-core.fritz.box"
    private val databaseName = "ktor_item"
    private val username = "ktor_item"
    private val password = System.getenv("MY_PASSWORD")

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getAllItems() = ktormDatabase.sequenceOf(DBItemTable).toList()

    fun getItem(id: Int) = ktormDatabase.sequenceOf(DBItemTable).firstOrNull { it.id eq id }

    fun addItem(draftItem: DraftItem) {
        ktormDatabase.insertAndGenerateKey(DBItemTable) {
            set(DBItemTable.message, draftItem.message)
            set(DBItemTable.isDone, draftItem.isDone)
        }
    }

    fun updateItem(id: Int, draftItem: DraftItem) {
        ktormDatabase.update(DBItemTable) {
            set(DBItemTable.message, draftItem.message)
            set(DBItemTable.isDone, draftItem.isDone)
            where {
                it.id eq id
            }
        }
    }

    fun removeItem(id: Int) {
        ktormDatabase.delete(DBItemTable) {
            it.id eq id
        }
    }
}