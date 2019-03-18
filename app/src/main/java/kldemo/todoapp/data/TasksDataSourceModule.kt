package kldemo.todoapp.data

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import kldemo.todoapp.data.db.TasksDao
import kldemo.todoapp.data.db.ToDoDatabase
import kldemo.usecases.TasksDataSource
import javax.inject.Singleton

@Module
class TasksDataSourceModule {
    @Singleton
    @Provides
    internal fun provideDb(context: Application): ToDoDatabase {
        return Room.databaseBuilder(context, ToDoDatabase::class.java, "Tasks.db").build()
    }

    @Singleton
    @Provides
    internal fun provideTasksDao(db: ToDoDatabase): TasksDao {
        return db.taskDao()
    }

    @Singleton
    @Provides
    fun tasksDataSource(tasksDao: TasksDao): TasksDataSource = TasksDbDataSource(tasksDao)
}
