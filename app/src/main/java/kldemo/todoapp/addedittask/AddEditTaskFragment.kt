package kldemo.todoapp.addedittask

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import dagger.android.support.DaggerFragment
import kldemo.entities.Task
import kldemo.todoapp.R
import kldemo.ui.AddEditTaskContract
import kotlinx.android.synthetic.main.fragment_add_edit_task.view.*
import javax.inject.Inject

class AddEditTaskFragment : DaggerFragment(), AddEditTaskContract.View {
    @Inject
    lateinit var presenter: AddEditTaskContract.Presenter
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var saveBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_edit_task, container, false)
        title = view.add_task_title
        description = view.add_task_description
        saveBtn = view.save_btn
        progressBar = view.progress_bar
        saveBtn.setOnClickListener {
            presenter.saveTask(title.text.toString(), description.text.toString())
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
        title.setText(task.title)
        description.setText(task.description)
    }

    override fun showProgress() {
        saveBtn.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        saveBtn.isEnabled = true
    }

    override fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun showTaskError(errors: HashMap<String, String>) {
        val titleName = "title"
        if(errors.containsKey(titleName)) {
            title.error = resources.getString(resources.getIdentifier(errors.get(titleName), "string", activity?.packageName));
        }
    }

    override fun showTasksList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
