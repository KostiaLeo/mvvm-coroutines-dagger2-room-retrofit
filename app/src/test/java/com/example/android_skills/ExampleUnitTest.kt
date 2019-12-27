package com.example.android_skills

import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.dagger.NetworkUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val component = DaggerApp.component

    private val networkComponent = DaggerApp.networkComponent

    @Test
    fun checkNetworkDefault() {
        val networkUtilsDefault = networkComponent.getNetworkUtilsDefault()
        assertEquals(networkUtilsDefault.showUrl(), "default is your url")
    }

    @Test
    fun checkNetworkByUrl(){
        val networkUtilsByUrl = networkComponent.getNetworkUtilsByUrl()
        assertEquals(networkUtilsByUrl.showUrl(), "http:/hello is your url")
    }

    @Test
    fun checkNetworkDBBuilder(){
        val networkDB = networkComponent.getNetworkDB()
        assertEquals(networkDB.showDao(), "this is dao - myDaoLink")
    }

    @Test
    fun checkLocalDB(){
        val localDB = component.getDatabaseHelper()
        assertEquals(localDB.showDB(), "You're welcome to local database")
    }
}
