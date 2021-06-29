package com.example.data.repository.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object DBItemTable: Table<DBItemEntity>("item"){
    val id = int("id").primaryKey().bindTo { it.id }
    val message = varchar("message").bindTo { it.message }
    val isDone = boolean("is_done").bindTo { it.isDone }
}


interface DBItemEntity: Entity<DBItemEntity> {
    companion object : Entity.Factory<DBItemEntity>()

    val id: Int
    val message: String
    val isDone: Boolean
}