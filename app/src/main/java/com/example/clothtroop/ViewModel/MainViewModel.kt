package com.example.clothtroop.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.clothtroop.Model.CategoryModel
import com.example.clothtroop.Model.ItemsModel
import com.example.clothtroop.Model.SliderModel
import com.example.clothtroop.Repository.MainRepository

class MainViewModel():ViewModel() {
    private val repository=MainRepository()

    fun loadBanner(): LiveData<MutableList<SliderModel>>{
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>>{
        return repository.loadCategory()
    }

    fun loadPopular():LiveData<MutableList<ItemsModel>>{
        return repository.loadPopular()
    }
}