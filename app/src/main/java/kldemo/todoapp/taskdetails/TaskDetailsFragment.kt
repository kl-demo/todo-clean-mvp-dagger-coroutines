package kldemo.todoapp.taskdetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import dagger.android.support.DaggerFragment
import kldemo.entities.Task
import kldemo.todoapp.R
import kldemo.todoapp.addedittask.AddEditTaskActivity
import kldemo.ui.TaskDetailsContract
import kotlinx.android.synthetic.main.fragment_task_details.view.*
import javax.inject.Inject

class TaskDetailsFragment : DaggerFragment(), TaskDetailsContract.View {
    @Inject
    lateinit var presenter: TaskDetailsContract.Presenter
    private lateinit var complete: CheckBox
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var editBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_details, container, false)
        complete = view.task_details_complete
        title = view.task_details_title
        description = view.task_details_description
        editBtn = view.edit_btn

        complete.setOnClickListener {
            presenter.completeTask(complete.isChecked)
        }

        editBtn.setOnClickListener {
            presenter.editTask()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun showTask(task: Task) {
        complete.isChecked = task.isCompleted
        title.text = task.title
        description.text = task.description
    }

    override fun showEditTask(taskId:Int) {
        val intent = Intent(context, AddEditTaskActivity::class.java)
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, taskId)
        startActivity(intent)
    }
}
