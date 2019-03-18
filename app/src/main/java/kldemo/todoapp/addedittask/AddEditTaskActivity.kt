package kldemo.todoapp.addedittask

import android.support.v4.app.Fragment
import kldemo.todoapp.SingleFragmentActivity

class AddEditTaskActivity : SingleFragmentActivity() {
    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }

    override fun createFragment(): Fragment {
        return AddEditTaskFragment()
    }
}
