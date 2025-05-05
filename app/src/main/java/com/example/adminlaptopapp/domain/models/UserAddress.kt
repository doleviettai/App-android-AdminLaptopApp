package com.example.adminlaptopapp.domain.models

data class UserAdress(
    var firstName : String = "",
    var lastName : String = "",
    var address : String = "",
    var city : String = "",
    var state : String = "",
    var pinCode : String = "",
    var country : String = "",
    var phoneNumber : String = "",
)