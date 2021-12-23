package com.example.portfolio1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio1.database.repositories.UserRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

private typealias ListUser = List<User>

class MainViewModelTest (

    private val randomUserDatabase: UserRepository
) : ViewModel () {

        private val _sharedFlow = MutableSharedFlow<ListUser>(
            replay = 1,
            onBufferOverflow = BufferOverflow.SUSPEND
        )

        val userListFlow : SharedFlow<ListUser> = _sharedFlow

        fun loadAllUser(count: Int) =
            viewModelScope.launch {
                _sharedFlow.emitAll(
                    randomUserDatabase.getUsers(count)
                )
            }



}
