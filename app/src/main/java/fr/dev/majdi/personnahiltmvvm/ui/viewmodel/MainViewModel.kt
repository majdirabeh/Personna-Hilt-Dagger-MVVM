package fr.dev.majdi.personnahiltmvvm.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.dev.majdi.personna.model.ResponsePersonna
import fr.dev.majdi.personna.model.Result
import fr.dev.majdi.personnahiltmvvm.App
import fr.dev.majdi.personnahiltmvvm.data.repository.MainRepository
import kotlinx.coroutines.launch

/**
 *Created by Majdi RABEH on 24/08/2020
 *Email = "m.rabeh.majdi@gmail.com")
 */
class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository
) : ViewModel() {

    var listOfUsers = MutableLiveData<List<Result>>()

    init {
        listOfUsers.value = listOf()
    }

    fun getUsers() {
        viewModelScope.launch {
            mainRepository.getUsers(object : MainRepository.OnPersonnaData {
                override fun onSuccess(responsePersonna: ResponsePersonna) {
                    listOfUsers.value = responsePersonna.results
                }

                override fun onFailure() {
                    listOfUsers.value = listOf()
                }
            })
        }
    }
}

