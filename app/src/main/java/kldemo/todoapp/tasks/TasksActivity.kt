package kldemo.todoapp.tasks

import android.support.v4.app.Fragment
import kldemo.todoapp.SingleFragmentActivity

class TasksActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
       return TasksFragment()
    }
}
