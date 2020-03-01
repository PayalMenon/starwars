package com.example.starwars.dagger.module

import com.example.starwars.dagger.ViewModelBuilder
import com.example.starwars.ui.FilmFragment
import com.example.starwars.ui.SearchActivity
import com.example.starwars.ui.SearchFragment
import com.example.starwars.ui.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun startActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun searchFragment(): SearchFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun userFragment(): UserFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun filmFragment(): FilmFragment

}