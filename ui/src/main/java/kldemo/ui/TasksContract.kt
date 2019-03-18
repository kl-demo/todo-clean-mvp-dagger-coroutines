package kldemo.ui

import kldemo.entities.Task

interface TasksContract {
    interface Presenter : BasePresenter<View> {
        fun loadTasks()

        fun completeTask(task: Task)

        fun deleteTask(task: Task)
    }

    interface View {
        fun showTasks(tasks: MutableList<Task>)

        fun showAddTask()

        fun showTaskDetails(task: Task)

        fun showDeleteTaskDialog(task: Task)

        fun showProgress(taskId: Int)

        fun hideProgress(taskId: Int)

        fun removeTask(taskId: Int)
    }
}