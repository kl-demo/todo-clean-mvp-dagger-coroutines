package kldemo.todoapp.taskdetails

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kldemo.todoapp.di.ActivityScoped
import kldemo.todoapp.di.FragmentScoped
import kldemo.ui.TaskDetailsContract


@Module
abstract class TaskDetailsModule {
    @ActivityScoped
    @Binds
    abstract fun taskDetailsPresenter(presenter: TaskDetailsPresenter): TaskDetailsContract.Presenter

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun taskDetailsFragment(): TaskDetailsFragment
}