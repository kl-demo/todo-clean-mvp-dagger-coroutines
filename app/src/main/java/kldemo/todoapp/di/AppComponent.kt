package kldemo.todoapp.di

import android.app.Application
import kldemo.todoapp.ToDoApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kldemo.todoapp.data.TasksDataSourceModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
    TasksDataSourceModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<ToDoApplication> {
    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}