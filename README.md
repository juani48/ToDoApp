# ToDoApp
ToDoApp is a simple project to implement and research new knowledge and reflect it in an app that displays a list of tasks to be done.

# Technologies and Progress
## Languages
I am using [Kotlin](https://kotlinlang.org/) as the language, as this app is primarily geared towards Android development.

I create the views using XML, due to its simplicity and similarity to HTML. In future versions, I will look to apply [Jetpack Compose](https://developer.android.com/compose) technology to improve the simplicity and robustness of the project.

## Project Architecture
This project presents an [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture to simplify database calls and generate a reactive and dynamic interface, providing robustness and solidity to the project.

## Data Persistence
The data persistence is done through a database, using [Room](https://developer.android.com/training/data-storage/room?hl=es-419) as administrator and mediator between the system and the data.

Currently the project presents a single table (the task table), but I plan to add the possibility of adding different types of categories, which can be persisted.
