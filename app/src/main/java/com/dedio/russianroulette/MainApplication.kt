package com.dedio.russianroulette

import android.app.Application
import android.content.Context
import com.dedio.russianroulette.base.BaseActivity
import com.dedio.russianroulette.di.components.ActivityComponent
import com.dedio.russianroulette.di.components.AppComponent
import com.dedio.russianroulette.di.components.DaggerAppComponent
import com.dedio.russianroulette.di.module.ActivityModule
import com.dedio.russianroulette.di.module.AppModule

class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        generateAppComponent()
    }

    companion object {
        fun getApplication(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }

    private fun generateAppComponent(): AppComponent {
        return DaggerAppComponent.builder().run {
            appModule(AppModule(this@MainApplication))
            build()
        }
    }

    fun createActivityComponent(activity: BaseActivity): ActivityComponent {
        return appComponent.plus(ActivityModule(activity))
    }
}