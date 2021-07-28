<h1 align="center">Movies</h1>

<p align="center">
  <a href="https://opensource.org/licenses/MIT"><img alt="License" src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

Movies is a small demo application based on MVVM architecture. Also features fetching from the network and caching. 

# 🛠️ Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) and [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - UI related data holder, lifecycle aware.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - lifecycle-aware observable data holder class. 
  - [Room](https://developer.android.com/jetpack/androidx/releases/room) Persistence - abstraction layer for local SQLite database.
- Architecture
  - MVVM (Model - ViewModel - View) Architecture 
  - Repository pattern
- [Retrofit2](https://github.com/square/retrofit) - Type-safe HTTP client used to connect to send network requests.
- [Gson](https://github.com/google/gson) - JSON parser.
- [Glide](https://github.com/bumptech/glide) - loading images from the network.
- 🔥[Firebase](https://firebase.google.com/) - for authentication

# 🎬 Preview 
<p align="center">
<img src="previews/movies%20demo.gif"/>
</p>
