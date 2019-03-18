package kldemo.ui

import kldemo.entities.Task

interface AddEditTaskContract {
    interface Presenter : BasePresenter<View> {
        fun start()

        fun saveTask(title: String, description: String)
    }

    interface View {
        fun showTask(task: Task)

        fun showProgress()

        fun hideProgress()

        fun showTaskError(errors: HashMap<String, String>)

        fun showError(error:String)

        fun showTasksList()
    }
}