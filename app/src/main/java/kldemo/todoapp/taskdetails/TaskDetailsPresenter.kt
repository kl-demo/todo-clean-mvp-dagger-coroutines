package kldemo.todoapp.taskdetails

import kldemo.entities.Task
import kldemo.ui.TaskDetailsContract
import kldemo.usecases.TasksDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskDetailsPresenter @Inject constructor(tasksDataSource: TasksDataSource, val taskId: Int) : TaskDetailsContract.Presenter {
    private var view: TaskDetailsContract.View? = null
    private val tasksDataSource = tasksDataSource

    override fun takeView(view: TaskDetailsContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }

    override fun start() {
        CoroutineScope(Dispatchers.Main).launch() {
            var task: Task = Task()
            withContext(Dispatchers.IO) { task = tasksDataSource.getOne(taskId) }
            view?.showTask(task)
        }
    }

    override fun editTask() {
        view?.showEditTask(taskId)
    }

    override fun completeTask(isCompleted: Boolean) {
        CoroutineScope(Dispatchers.IO).launch() {
            var task = tasksDataSource.getOne(taskId)
            task.isCompleted = isCompleted
            tasksDataSource.save(task)
        }
    }
}