package kldemo.todoapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kldemo.todoapp.di.DaggerAppComponent

class ToDoApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}