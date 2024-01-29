## Requirements

- Use Kotlin as a language, Kotlin Flows and Google’s architecture approach including MVVM and
  Repositories,
  I have used the following architecture
  <img src="images/architecture.png" width="250px" />

- Fetch the data and cache it so it’s available offline - reload the data on each app start too
  I have two interceptor one used while we have network access and fetch the data from the API, And
  offline interceptor fetch data from the cache

 ```` 
 single {
        fun hasNetwork(): Boolean {
            val connectivityManager =
                androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

        val onlineInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }

        val offlineInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            if (!hasNetwork()) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(androidContext().cacheDir, cacheSize.toLong())

        OkHttpClient.Builder() // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .build()
    } 

````

- Layouts should be done in XML - not Jetpack Compose.
  I have build the UI using the XML

- Display the data in a vertical RecyclerView list - one column on smartphones and 3 columns on
  larger screens
  <img src="images/one line data.png" width="250px" />
  <img src="images/larger screens.png" width="250px" />

- Each item should display it’s image with a description under it
  <img src="images/one line data.png" width="250px" />

- Click on the item should open the link in an external browser
   ```
    private fun showDetail(item: ResponseModel.Item) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
        startActivity(browserIntent)
    }```


- Sort the data by the published field from the data

 ```
    val data = resource.map {
        it.valueOrNull?.items?.sortedBy { item: ResponseModel.Item? -> item?.published }.orEmpty()
    } ```

## Libraries
* [kotlin](https://kotlinlang.org/)
  * [kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines)
* [androidx](https://developer.android.com/jetpack/androidx)
  * [appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat)
  * [android ktx](https://developer.android.com/kotlin/ktx)
  * [constraintlayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout)
  * [lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [material-components](https://github.com/material-components/material-components-android)
* [coil](https://github.com/coil-kt/coil)
* [koin](https://github.com/InsertKoinIO/koin)
* [retrofit](https://github.com/square/retrofit)
* [okhttp](https://github.com/square/okhttp)
* [moshi](https://github.com/square/moshi)
