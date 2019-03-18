package kldemo.todoapp.addedittask

import dagger.Module
import dagger.Provides
import kldemo.todoapp.di.ActivityScoped

@Module
class AddEditTaskPresenterModule {
   @Provides
   @ActivityScoped
   internal fun provideTaskId(activity: AddEditTaskActivity): Int {
      return activity.intent.getIntExtra(AddEditTaskActivity.EXTRA_TASK_ID, 0)
   }
}