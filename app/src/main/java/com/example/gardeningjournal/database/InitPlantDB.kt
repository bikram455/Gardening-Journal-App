package com.example.gardeningjournal.database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.lang.IllegalStateException

object InitPlantDB {
    private var database: PlantDB? = null

    fun initialize(context: Context) {
        if(database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                PlantDB::class.java, "plant_database"
            )
//                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    fun getDatabase(context: Context): PlantDB {
        if (database == null) {
            initialize(context)
        }
        return database!!;
    }


    private val MIGRATION_1_2: Migration = object : Migration( 2, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {

        }
    }
}
