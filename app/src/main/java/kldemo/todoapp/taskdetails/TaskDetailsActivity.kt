package kldemo.todoapp.taskdetails

import android.support.v4.app.Fragment
import kldemo.todoapp.SingleFragmentActivity

class TaskDetailsActivity : SingleFragmentActivity() {
    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }

    override fun createFragment(): Fragment {
        return TaskDetailsFragment()
    }
}
