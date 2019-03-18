package kldemo.todoapp.tasks

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kldemo.todoapp.di.ActivityScoped
import kldemo.todoapp.di.FragmentScoped
import kldemo.ui.TasksContract

@Module
abstract class TasksModule {
    @ActivityScoped
    @Binds
    abstract fun tasksPresenter(presenter: TasksPresenter): TasksContract.Presenter

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun tasksFragment(): TasksFragment

}