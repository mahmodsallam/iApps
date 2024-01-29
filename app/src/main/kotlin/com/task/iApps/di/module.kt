@file:Suppress("DEPRECATION")

package com.task.iApps.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.task.iApps.data.api.ItemsApi
import com.task.iApps.data.repository.ItemsRepository
import com.task.iApps.data.repository.ItemsRepositoryImpl
import com.task.iApps.ui.top.ItemsViewModel
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val module = module {
    // ui
    viewModel {
        ItemsViewModel(get())
    }
    // data/repository
    single<ItemsRepository> { ItemsRepositoryImpl(get()) }

    // data/api
    single {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(ItemsApi.HTTPS_API_GITHUB_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single {
        get<Retrofit>().create(ItemsApi::class.java)
    }

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
}
