package kldemo.ui

import kldemo.entities.Task

interface TaskDetailsContract {
    interface Presenter : BasePresenter<View> {
        fun start()

        fun editTask()

        fun completeTask(isCompleted: Boolean)
    }
    interface View {
        fun showTask(task: Task)

        fun showEditTask(taskId: Int)
    }
}