package kldemo.todoapp.tasks

import kldemo.entities.Task
import kldemo.ui.TasksContract
import kldemo.usecases.TasksDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class TasksPresenter @Inject constructor(tasksDataSource: TasksDataSource) : TasksContract.Presenter {
    private var view: TasksContract.View? = null
    private val tasksDataSource = tasksDataSource

    override fun takeView(view: TasksContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun loadTasks() {
        CoroutineScope(Dispatchers.Main).launch() {
            var tasks: MutableList<Task> = mutableListOf()
            withContext(Dispatchers.IO) {
                tasks = tasksDataSource.getAll().toMutableList()
            }
            view?.showTasks(tasks)
        }
    }

    override fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch() {
            withContext(Dispatchers.IO) {
                tasksDataSource.delete(task)
            }
            view?.hideProgress(task.id)
            view?.removeTask(task.id)
        }
    }

    override fun completeTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch() {
            tasksDataSource.save(task)
        }
    }
}