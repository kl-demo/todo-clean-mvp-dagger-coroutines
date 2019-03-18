package kldemo.usecases

import kldemo.entities.Task

interface TasksDataSource {
    fun save(task: Task): Task

    fun delete(task: Task): Task

    fun getAll(): List<Task>

    fun getOne(id: Int): Task

    fun validate(task: Task): HashMap<String, String>
}