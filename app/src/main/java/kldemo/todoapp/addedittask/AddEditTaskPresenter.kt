package kldemo.todoapp.addedittask

import kldemo.entities.Task
import kldemo.ui.AddEditTaskContract
import kldemo.usecases.TasksDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class AddEditTaskPresenter @Inject constructor(tasksDataSource: TasksDataSource, val taskId: Int) : AddEditTaskContract.Presenter {
    private var view: AddEditTaskContract.View? = null
    private val tasksDataSource = tasksDataSource

    override fun start() {
        CoroutineScope(Dispatchers.Main).launch() {
            var task: Task = Task()
            if (taskId > 0) {
                withContext(Dispatchers.IO) { task = tasksDataSource.getOne(taskId) }
            }
            view?.showTask(task)
        }
    }

    override fun saveTask(title: String, description: String) {
        view?.showProgress()
        val task = Task();
        task.id = taskId;
        task.title = title
        task.description = description
        val errors: HashMap<String, String> = tasksDataSource.validate(task)
        if (errors.size > 0) {
            view?.showTaskError(errors)
        } else {
            CoroutineScope(Dispatchers.Main).launch() {
                try {
                    withContext(Dispatchers.IO) {
                        tasksDataSource.save(task)
                    }
                    view?.showTasksList()
                } catch (ex: Exception) {
                    view?.showError(ex.message.toString())
                }
            }
        }
        view?.hideProgress()
    }

    override fun takeView(view: AddEditTaskContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }
}