# ToDoApp
ToDoApp is a simple project to implement and research new knowledge and reflect it in an app that displays a list of tasks to be done.

# Technologies and Progress
## Languages
I am using [Kotlin](https://kotlinlang.org/) as the language, as this app is primarily geared towards Android development.

I create the views using XML, due to its simplicity and similarity to HTML. In future versions, I will look to apply [Jetpack Compose](https://developer.android.com/compose) technology to improve the simplicity and robustness of the project.

## Project Architecture

There is currently no specific software architecture implemented, but a simple separation of packages with the main parts of the project.
I plan to apply [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture in the future to improve the robustness of the protection.

## Data Persistence

The [TaskRepository](https://github.com/juani48/ToDoApp/blob/main/app/src/main/java/com/juani48/todoapp/repository/TaskRepository.kt) class currently does not have a fundamental role in the app,
but will be used as a gateway to implement first Preferences Datastore and then [Room](https://developer.android.com/training/data-storage/room?hl=es-419) along with SQL for data persistence.
