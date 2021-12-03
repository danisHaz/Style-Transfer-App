package com.example.styletransferapp.business.interactors

import java.lang.ClassCastException
import java.lang.IllegalArgumentException

data class GenericData<T>(val data: T?)

class EventResult<T: GenericData<*>> {
    private companion object {
        const val KEY_NOT_EXIST: String
            = "Key doesn't exist"
    }

    private val resultData: MutableMap<String, T> by lazy {
        mutableMapOf()
    }

    fun <LocalType: Any?> addData(key: String, data: LocalType?) {
        try {
            resultData[key] = GenericData(data) as T
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }


    fun <LocalType: Any?> getDataByKey(key: String): LocalType? {
        if (!resultData.containsKey(key))
            return null

        var res: LocalType? = null
        try {
            res = resultData[key]!!.data as LocalType
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return res
    }
}