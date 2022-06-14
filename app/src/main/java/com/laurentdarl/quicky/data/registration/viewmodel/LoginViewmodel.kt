package com.laurentdarl.quicky.data.registration.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laurentdarl.quicky.data.registration.models.RegistrationState
import com.laurentdarl.quicky.domain.usecases.*
import com.laurentdarl.quicky.presentation.fragments.registration.RegistrationFormEvent
import kotlinx.coroutines.launch

class LoginViewmodel(
    private val validateName: ValidateName = ValidateName(),
    private val validatePhone: ValidatePhone = ValidatePhone(),
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatPassword: ValidateRepeatPassword = ValidateRepeatPassword(),

): ViewModel() {

    private var _state = MutableLiveData<RegistrationState>()
    private val state: LiveData<RegistrationState> = _state

    private var _nameState = MutableLiveData<RegistrationState>()
    private val nameState: LiveData<RegistrationState> = _state

    fun onEvent(event: RegistrationFormEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is RegistrationFormEvent.NameChanged -> {
                        _nameState.value = _nameState.value?.copy(
                            name = ""
                        )
                    }
                    is RegistrationFormEvent.PhoneChanged -> {
                        if (state.value == null) {
                            validatePhone
                        }
                    }
                    is RegistrationFormEvent.EmailChanged -> {

                    }
                    is RegistrationFormEvent.PasswordChanged -> {

                    }
                    is RegistrationFormEvent.ResetPasswordChanged -> {

                    }
                    is RegistrationFormEvent.Submit -> {

                    }
                }
            }
            catch (e: Exception) {
                Log.i(TAG, "onEvent: Exception $e")
            }
        }
    }
}