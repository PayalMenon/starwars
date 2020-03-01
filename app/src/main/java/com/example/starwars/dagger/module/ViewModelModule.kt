package com.example.starwars.dagger.module

import androidx.lifecycle.ViewModel
import com.example.starwars.dagger.ViewModelKey
import com.example.starwars.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindStarWarsViewModel(viewModel: SearchViewModel) : ViewModel
}