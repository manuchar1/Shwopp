package com.tbcacademy.shwop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tbcacademy.shwop.data.entities.Post


@Database(
    entities = [Post::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun getProductsDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductsDatabase::class.java,
                "products_db.db"
            ).build()
    }
}