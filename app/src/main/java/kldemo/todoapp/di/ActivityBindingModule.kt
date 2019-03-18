package kldemo.todoapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kldemo.todoapp.addedittask.AddEditTaskActivity
import kldemo.todoapp.addedittask.AddEditTaskModule
import kldemo.todoapp.addedittask.AddEditTaskPresenterModule
import kldemo.todoapp.taskdetails.TaskDetailsActivity
import kldemo.todoapp.taskdetails.TaskDetailsModule
import kldemo.todoapp.taskdetails.TaskDetailsPresenterModule
import kldemo.todoapp.tasks.TasksActivity
import kldemo.todoapp.tasks.TasksModule

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [TasksModule::class])
    abstract fun tasksActivity(): TasksActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AddEditTaskModule::class, AddEditTaskPresenterModule::class])
    abstract fun addEditTaskActivity(): AddEditTaskActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TaskDetailsModule::class, TaskDetailsPresenterModule::class])
    abstract fun taskDetailsActivity(): TaskDetailsActivity
}