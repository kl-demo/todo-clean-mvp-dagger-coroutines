package kldemo.todoapp.taskdetails

import dagger.Module
import dagger.Provides
import kldemo.todoapp.di.ActivityScoped
import kldemo.ui.TaskDetailsContract
import kldemo.usecases.TasksDataSource

@Module
class TaskDetailsPresenterModule {
   @Provides
   @ActivityScoped
   fun provideTaskId(activity: TaskDetailsActivity): Int {
      return activity.intent.getIntExtra(TaskDetailsActivity.EXTRA_TASK_ID, 0)
   }
}