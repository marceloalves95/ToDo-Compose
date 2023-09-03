package br.com.todo_compose.app

import android.app.Application
import br.com.todo_compose.app.di.ToDoComposeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@Application)
            ToDoComposeModule.load()
        }
    }
}