package kldemo.todoapp.tasks

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kldemo.entities.Task
import kldemo.todoapp.R
import kldemo.ui.TasksContract
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import android.widget.TextView
import kldemo.todoapp.addedittask.AddEditTaskActivity
import kldemo.todoapp.taskdetails.TaskDetailsActivity
import kotlinx.android.synthetic.main.task_item.view.*

class TasksFragment : DaggerFragment(), TasksContract.View {
    @Inject
    lateinit var presenter: TasksContract.Presenter
    private var adapter: TasksAdapter? = null
    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var no_tasks: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)
        tasksRecyclerView = view.tasks_recycler_view
        no_tasks = view.no_tasks
        tasksRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        view.tasks_recycler_view.setLayoutManager(LinearLayoutManager(context))
        presenter.loadTasks()
        view.add_task_btn.setOnClickListener {
            this.showAddTask()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun showTasks(tasks: MutableList<Task>) {
        if (adapter == null) {
            adapter = TasksAdapter(tasks, this)
            tasksRecyclerView.adapter = adapter
        } else {
            adapter?.updateTasks(tasks)
            adapter?.notifyDataSetChanged()
        }
        updateNoTaskVisibility(tasks.size == 0)
    }

    private fun updateNoTaskVisibility(visible: Boolean)
    {
        no_tasks.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showDeleteTaskDialog(task: Task) {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(getString(R.string.delete_task, task.title))
        builder.setPositiveButton(R.string.delete) { _, _ ->
            this.showProgress(task.id)
            presenter.deleteTask(task)
        }
        builder.setNegativeButton(R.string.cancel) { _, _ -> }
        val dialog = builder.create()
        dialog.show()
    }

    override fun showProgress(taskId: Int) {
        val position = adapter?.getTaskPosition(taskId)
        if (position != null) {
            var itemView = tasksRecyclerView.findViewHolderForAdapterPosition(position) as TasksHolder;
            itemView.delete_btn.visibility = View.GONE
            itemView.progress_bar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress(taskId: Int) {
        val position = adapter?.getTaskPosition(taskId)
        if (position != null) {
            var itemView = tasksRecyclerView.findViewHolderForAdapterPosition(position) as TasksHolder;
            itemView.progress_bar.visibility = View.GONE
            itemView.delete_btn.visibility = View.VISIBLE
        }
    }

    override fun removeTask(taskId: Int) {
        adapter?.removeTasks(taskId)
    }

    override fun showAddTask() {
        val intent = Intent(context, AddEditTaskActivity::class.java)
        startActivity(intent)
    }

    override fun showTaskDetails(task: Task) {
        val intent = Intent(context, TaskDetailsActivity::class.java)
        intent.putExtra(TaskDetailsActivity.EXTRA_TASK_ID, task.id)
        startActivity(intent)
    }

    private inner class TasksHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val complete = itemView.task_item_complete
        val title = itemView.task_item_title
        val delete_btn = itemView.delete_btn
        val progress_bar = itemView.progress_item_bar
    }

    private inner class TasksAdapter(var tasks: MutableList<Task>, val tasksContractView: TasksContract.View) :
        RecyclerView.Adapter<TasksHolder>() {
        override fun getItemCount(): Int {
            return tasks.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
            return TasksHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.task_item, parent, false
                )
            )
        }

        override fun onBindViewHolder(viewHolder: TasksHolder, position: Int) {
            val task = tasks[position]
            viewHolder.complete.isChecked = task.isCompleted
            viewHolder.title.text = task.title

            viewHolder.itemView.setOnClickListener {
               tasksContractView.showTaskDetails(task)
            }

            viewHolder.complete.setOnClickListener {
                task.isCompleted = viewHolder.complete.isChecked
                presenter.completeTask(task)
            }

            viewHolder.delete_btn.setOnClickListener {
                tasksContractView.showDeleteTaskDialog(task)
            }
        }

        fun updateTasks(tasks: MutableList<Task>) {
            this.tasks = tasks
        }

        fun removeTasks(taskId: Int) {
            this.tasks.remove(this.tasks.filter { it.id == taskId }.single())
            adapter?.notifyDataSetChanged()
            updateNoTaskVisibility(tasks.size == 0)
        }

        fun getTaskPosition(taskId: Int): Int {
            return this.tasks.indexOf(this.tasks.filter { it.id == taskId }.single())
        }
    }
}
