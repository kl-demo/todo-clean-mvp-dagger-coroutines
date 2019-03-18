package kldemo.todoapp.addedittask

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kldemo.todoapp.di.ActivityScoped
import kldemo.todoapp.di.FragmentScoped
import kldemo.ui.AddEditTaskContract

@Module
abstract class AddEditTaskModule {
    @ActivityScoped
    @Binds
    abstract fun addEditTaskPresenter(presenter: AddEditTaskPresenter): AddEditTaskContract.Presenter

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun addEditTaskFragment(): AddEditTaskFragment
}