package com.example.android_skills

import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.Currency
import com.example.android_skills.model.Image
import com.example.android_skills.model.Results
import kotlinx.coroutines.launch

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val viewModel = DaggerApp.appComponent.provideViewModel()
    private val scope = viewModel.viewModelScope
    private val src = DaggerApp.sourceComponent.provideGeneralRepository()
    private val firstExpectedName = "Peep"

    private val source = DaggerApp.sourceComponent


    @Test
    fun testGeneralRepository(){
        val repository = source.provideGeneralRepository()

        scope.launch {
            val firstName = repository.retrieveData()[0].name
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testRemoteSource() {
        scope.launch {
            val firstName = source.provideRemoteSource().retrieveData()[0].name
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testLocalSource() {
        scope.launch {
            val firstName = source.provideLocalSource().retrieveData()[0].name
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testDatabaseRepository() {
        scope.launch {
            val firstName = DaggerApp.roomComponent.provideDatabaseRepository().getData()[0].name
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testInserting() {

        scope.launch {
            val dao = DaggerApp.roomComponent.provideDao()

            val artist = Results(Image("", "", ""), "", "", Currency(""))

            val countOfAddedRows = dao.insert(arrayListOf(artist)).size

            val countAfterAdding = dao.countOfRows()

            val countOfDeleted = dao.deleteLastItem()

            val countOfLeftItems = dao.countOfRows()

            assertEquals("I've added one new row", 1, countOfAddedRows)
            assertEquals("All rows after adding", 7, countAfterAdding)
            assertEquals("I've deleted one new row", 1, countOfDeleted)
            assertEquals("And it's left one less item", 6, countOfLeftItems)
        }
    }
}
