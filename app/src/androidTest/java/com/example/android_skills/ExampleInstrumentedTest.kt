package com.example.android_skills

import androidx.lifecycle.viewModelScope
import androidx.test.runner.AndroidJUnit4
import com.example.android_skills.dagger.dagger.App
import com.example.android_skills.model.Exhibit
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

    private val viewModel = App.appComponent.provideViewModel()
    private val scope = viewModel.viewModelScope
    private val firstExpectedName = "iPhone 5s"
    private val dao = App.appComponent.provideDao()


    @Test
    fun testGeneralRepository(){
        val repository = App.appComponent.provideModelRepo()

        scope.launch {
            val firstName = repository.getExhibitList()[0].title
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testRemoteSource() {
        scope.launch {
            val firstName = App.appComponent.provideRemoteSrc().retrieveData()[0].title
            println(firstName)
            assertEquals(firstName, "iPhone 5s")
            val rows = dao.countOfRows()

            assertEquals("9 items should be inserted", 9, rows)
        }
    }

    @Test
    fun testLocalSource() {
        scope.launch {
            val firstName = App.appComponent.provideLocalSrc().retrieveData()[0].title
            assertEquals("try to retrieve local data", firstName, firstExpectedName)
        }
    }

    @Test
    fun testDatabaseRepository() {
        scope.launch {
            val firstName = App.appComponent.provideDBRepo().getData()[0].title
            assertEquals(firstName, firstExpectedName)
        }
    }

    @Test
    fun testInserting() {

        scope.launch {
            val exhibit = Exhibit(listOf(""), "title")

            val countOfAddedRows = dao.insert(arrayListOf(exhibit)).size

            val countAfterAdding = dao.countOfRows()


            val countAfterRemoving = dao.deleteAllExhibits()

            assertEquals("I've added one new row", 1, countOfAddedRows)
            assertEquals("All rows after adding", 19, countAfterAdding)
            assertEquals("And it's left one less item", 0, countAfterRemoving)
        }
    }
}
