package hu.bme.sch.parkett.parkettapplication.network

import java.lang.Exception

class NetworkException(override val message: String) : Exception(message) {
}