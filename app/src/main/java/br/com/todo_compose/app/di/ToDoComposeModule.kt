package br.com.todo_compose.app.di

import androidx.room.Room
import br.com.todo_compose.data.ToDoComposeRepositoryImpl
import br.com.todo_compose.data.local.DataSourceLocalImpl
import br.com.todo_compose.data.local.database.ToDoComposeDatabase
import br.com.todo_compose.data.local.database.ToDoComposeDatabase.Companion.DATABASE_NAME
import br.com.todo_compose.data.source.DataSource
import br.com.todo_compose.domain.repository.ToDoComposeRepository
import br.com.todo_compose.domain.usecases.AddTaskUseCase
import br.com.todo_compose.domain.usecases.DeleteAllTaskUseCase
import br.com.todo_compose.domain.usecases.TaskAllUseCase
import br.com.todo_compose.domain.usecases.UpdateTaskUseCase
import br.com.todo_compose.presentation.ui.addAndAlter.AddAndAlterTaskViewModel
import br.com.todo_compose.presentation.ui.main.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object ToDoComposeModule {

    fun load() {
        loadKoinModules(
            listOf(
                dataModule(),
                domainModule(),
                presentationModule()
            )
        )
    }

    private fun dataModule(): Module = module {
        single {
            Room.databaseBuilder(
                context = get(),
                klass = ToDoComposeDatabase::class.java,
                name = DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
        single {
            get<ToDoComposeDatabase>().taskDao()
        }
        single<DataSource.Local> {
            DataSourceLocalImpl(dao = get())
        }
        single<ToDoComposeRepository> {
            ToDoComposeRepositoryImpl(get())
        }
    }

    private fun domainModule(): Module = module {
        factory {
            AddTaskUseCase(get())
        }
        factory {
            TaskAllUseCase(get())
        }
        factory {
            DeleteAllTaskUseCase(get())
        }
        factory {
            UpdateTaskUseCase(get())
        }
    }

    private fun presentationModule(): Module = module {
        viewModel {
            MainActivityViewModel(
                taskAllUseCase = get(),
                deleteAllTaskUseCase = get()
            )
        }
        viewModel {
            AddAndAlterTaskViewModel(
                addTaskUseCase = get(),
                updateTaskUseCase = get(),
            )
        }
    }
}