package com.example.royalclips.model

object Constants {
    const val BASE_URL = "https://psmobitech.com/barberapp/v3/index.php/api/"
    const val BASE_IMAGE_URL = "https://psmobitech.com/barberapp/"
    const val LOGIN_END_POINT = "AppUser/login"
    const val REGISTER_END_POINT = "AppUser/signup"
    const val GET_SERVICES_END_POINT = "Service/getServices"
    const val GET_BARBERS_END_POINT = "Barber/getBarbers"
    const val ADD_BARBERS_WITH_SERVICES_END_POINT = "BarberServices/addBarberWithServices"
    const val GET_SERVICES_OF_BARBER_END_POINT = "BarberServices/getBarberServices"
    const val GET_CURRENT_BOOKING_AND_AVAILABLE_SLOTS_END_POINT = "Appointment/currentAppointments/{barberId}"
    const val BOOK_CONFIRMED_APPOINTMENT_END_POINT = "Appointment/book"
}