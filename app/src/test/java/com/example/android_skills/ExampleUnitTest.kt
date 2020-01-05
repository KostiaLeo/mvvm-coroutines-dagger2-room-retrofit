package com.example.android_skills

import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.viewmodel.DaggerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    val daggerViewModel: DaggerViewModel = DaggerApp.appComponent.provideViewModel()

    fun initApp(){

    }
    @Test
    fun start(){
        assertNotNull(daggerViewModel)
    }
//    private val component = DaggerApp.component
//
//    private val networkComponent = DaggerApp.networkComponent
//
//    @Test
//    fun checkNetworkDefault() {
//        val networkUtilsDefault = networkComponent.getNetworkUtilsDefault()
//        assertEquals(networkUtilsDefault.showUrl(), "default is your url")
//    }
//
//    @Test
//    fun checkNetworkByUrl(){
//        val networkUtilsByUrl = networkComponent.getNetworkUtilsByUrl()
//        assertEquals(networkUtilsByUrl.showUrl(), "http:/hello is your url")
//    }
//
//    @Test
//    fun checkNetworkDBBuilder(){
//        val networkDB = networkComponent.getNetworkDB()
//        assertEquals(networkDB.showDao(), "this is dao - myDaoLink")
//    }
//
//    @Test
//    fun checkLocalDB(){
//        val localDB = component.getDatabaseHelper()
//        assertEquals(localDB.showDB(), "You're welcome to local database")
//    }
//
//    @Test
//    fun testVMComponent(){
//        //val testString = DaggerApp.vmComponent.getVM().getTestingString()
//        //assertEquals(testString, "test")
//    }
}
