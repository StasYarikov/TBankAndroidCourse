package com.example.mynotfirstproject.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.mynotfirstproject.di.module.DataBindsModule
import com.example.mynotfirstproject.di.module.DataModule
import com.example.mynotfirstproject.di.module.PresentationModule
import com.example.mynotfirstproject.presentation.JokeActivity
import com.example.mynotfirstproject.presentation.add_joke.AddJokeFragment
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsFragment
import com.example.mynotfirstproject.presentation.jokes_list.JokeListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DataModule::class,
        DataBindsModule::class,
        PresentationModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: JokeActivity)
    fun inject(listFragment: JokeListFragment)
    fun inject(addFragment: AddJokeFragment)
    fun inject(detailsFragment: JokeDetailsFragment)

    @Component.Factory
    interface AppComponentFactory {

        fun create(@BindsInstance context: Context) : AppComponent
    }
}