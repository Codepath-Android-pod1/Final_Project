package com.example.final_project.models

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Event")
class ParseEvent : ParseObject() {
    fun getTitle(): String? {
        return getString(KEY_TITLE)
    }

    fun setTitle(title: String) {
        put(KEY_TITLE, title)
    }

    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }

    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }

    fun getDate(): Date? {
        return getDate(KEY_DATE)
    }

    fun setDate(date: Date) {
        put(KEY_DATE, date)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getLocation(): ParseGeoPoint? {
        return getParseGeoPoint(KEY_LOCATION)
    }

    fun setLocation(parseLocation: ParseGeoPoint) {
        put(KEY_LOCATION, parseLocation)
    }

    fun getLocName(): String? {
        return getString(KEY_LOCATION_NAME)
    }

    fun setLocName(name: String) {
        put(KEY_LOCATION_NAME, name)
    }

    fun getLocAddress(): String? {
        return getString(KEY_LOCATION_ADDRESS)
    }

    fun setLocAddress(address: String) {
        put(KEY_LOCATION_ADDRESS, address)
    }

    fun getLogistics() : ParseObject? {
        return getParseObject(KEY_LOGISTICS)
    }

    fun setLogistics(logistics: ParseObject) {
        put(KEY_LOGISTICS, logistics)
    }

    companion object {
        const val KEY_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
        const val KEY_DATE = "date"
        const val KEY_USER = "user"
        const val KEY_LOCATION = "location"
        const val KEY_LOCATION_NAME = "locationName"
        const val KEY_LOCATION_ADDRESS = "locationAddress"
        const val KEY_LOGISTICS = "logistics"
    }
}