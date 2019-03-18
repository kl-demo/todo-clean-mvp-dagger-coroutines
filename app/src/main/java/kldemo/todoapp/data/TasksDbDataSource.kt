package kldemo.todoapp.data

import kldemo.entities.Task
import kldemo.todoapp.data.db.TaskEntity
import kldemo.todoapp.data.db.TasksDao
import kldemo.usecases.TasksDataSource

class TasksDbDataSource(val tasksDao: TasksDao) : TasksDataSource {
    companion object {
        const val FIELD_IS_REQUIRED = "field_is_required"
    }

    override fun save(task: Task): Task {
        if (task.id > 0) {
          tasksDao.updateTask(convertToTaskEntity(task))
        } else {
            task.id = tasksDao.insertTask(convertToTaskEntity(task)).toInt()
        }
        return task;
    }

    override fun delete(task: Task): Task {
        tasksDao.deleteTaskById(task.id)
        return task;
    }

    override fun getAll(): List<Task> {
        val tasks: MutableList<Task> = mutableListOf()
        val taskEntityList: List<TaskEntity> = tasksDao.getTasks()
        for (taskEntity in taskEntityList) {
            tasks.add(convertToTask(taskEntity))
        }
        return tasks
    }

    override fun getOne(id: Int): Task {
        val taskEntity = tasksDao.getTaskById(id)
        if (taskEntity != null) {
            return convertToTask(taskEntity)
        }
        return Task()
    }

    override fun validate(task: Task): HashMap<String, String> {
        val errors = HashMap<String, String>()
        if (task.title.isEmpty()) {
            errors.put("title", TasksDbDataSource.FIELD_IS_REQUIRED)
        }
        return errors
    }

    private fun convertToTask(taskEntity: TaskEntity): Task {
        return Task(taskEntity.id, taskEntity.title, taskEntity.description, taskEntity.isCompleted)
    }

    private fun convertToTaskEntity(task: Task): TaskEntity {
        val taskEntity = TaskEntity()
        taskEntity.id = task.id
        taskEntity.title = task.title
        taskEntity.description = task.description
        taskEntity.isCompleted = task.isCompleted
        return taskEntity
    }
}