package com.example.android_skills

import androidx.lifecycle.viewModelScope
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.model_module_description.Exhibit
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
    private val firstExpectedName = "iPhone 5s"
    private val dao = DaggerApp.roomComponent.provideDao()

    private val source = DaggerApp.sourceComponent


    @Test
    fun testGeneralRepository(){
        val repository = source.provideGeneralRepository()

        scope.launch {
            val firstName = repository.getExhibitList()[0].title
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testRemoteSource() {
        scope.launch {
            val firstName = source.provideRemoteSource().retrieveData()[0].title
            println(firstName)
            assertEquals(firstName, "iPhone 5s")
            val rows = dao.countOfRows()

            assertEquals("9 items should be inserted", 9, rows)
        }
    }

    @Test
    fun testLocalSource() {
        scope.launch {
            val firstName = source.provideLocalSource().retrieveData()[0].title
            assertEquals("try to retrieve local data", firstName, firstExpectedName)
        }
    }

    @Test
    fun testDatabaseRepository() {
        scope.launch {
            val firstName = DaggerApp.roomComponent.provideDatabaseRepository().getData()[0].title
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testInserting() {

        scope.launch {
            val dao = DaggerApp.roomComponent.provideDao()

            val exhibit = Exhibit(listOf(""), "title")

            val countOfAddedRows = dao.insert(arrayListOf(exhibit)).size

            val countAfterAdding = dao.countOfRows()

            val countOfDeleted = dao.deleteLastItem()

            val countOfLeftItems = dao.countOfRows()

            assertEquals("I've added one new row", 1, countOfAddedRows)
            assertEquals("All rows after adding", 10, countAfterAdding)
            assertEquals("I've deleted one new row", 1, countOfDeleted)
            assertEquals("And it's left one less item", 9, countOfLeftItems)
        }
    }
}
