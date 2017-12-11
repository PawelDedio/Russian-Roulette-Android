package com.dedio.russianroulette.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.dedio.russianroulette.BR

data class User(private var _firstName: String, private var _lastName: String, private var _age: Int) : BaseObservable() {

    var firstName: String
        @Bindable get() = _firstName
        set(value) {
            _firstName = value
            notifyPropertyChanged(BR.firstName)
        }

    var lastName: String
        @Bindable get() = _lastName
        set(value) {
            _lastName = value
            notifyPropertyChanged(BR.lastName)
        }

    var age: Int
        @Bindable get() = _age
        set(value) {
            _age = value
            notifyPropertyChanged(BR.age)
        }
}