package com.yodle.android.kotlindemo.activity

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.test.ActivityInstrumentationTestCase2
import android.widget.TextView
import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.R
import com.yodle.android.kotlindemo.dagger.TestAppComponent
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryOwner
import com.yodle.android.kotlindemo.service.GitHubService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import rx.Observable
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest : ActivityInstrumentationTestCase2<MainActivity>(MainActivity::class.java) {

    lateinit var mainActivity: MainActivity

    @Inject lateinit var gitHubService: GitHubService

    @Before
    override public fun setUp() {
        super.setUp()
        injectInstrumentation(InstrumentationRegistry.getInstrumentation())
        getTestGraph().inject(this)
    }

    @Test
    fun testRepositorySearch() {
        `when`(gitHubService.searchRepositories(anyString())).thenReturn(Observable.just(arrayListOf()))
        `when`(gitHubService.searchRepositories("kotlin")).thenReturn(Observable.just(arrayListOf(buildRepository("kotlin"))))
        `when`(gitHubService.searchRepositories("test")).thenReturn(Observable.just(arrayListOf(buildRepository("test"))))

        startActivity()
        clickClearSearchButton()
        typeSearchText("test")

        val repositoryTitleTextView = mainActivity.findViewById(R.id.repositoryItemTitle) as TextView
        assertEquals("test", repositoryTitleTextView.text)
    }

    /**
     * Framework helper functions
     */
    fun getTestGraph() = MainApp.graph as TestAppComponent

    fun startActivity() {
        mainActivity = activity
    }

    /**
     * View helper functions
     */
    fun typeSearchText(query: String) {
        onView(withId(R.id.searchCardEditText)).perform(typeText(query))
    }

    fun clickClearSearchButton() {
        onView(withId(R.id.searchCardClear)).perform(click())
    }

    /**
     * Data helper functions
     */
    fun buildRepository(fullName: String) = Repository(
            id = 1,
            name = "name",
            full_name = fullName,
            owner = RepositoryOwner(
                    id = 2,
                    login = "login",
                    avatar_url = "avatar_url",
                    url = "url",
                    type = "type"
            ),
            html_url = "html_url",
            description = "description",
            url = "url",
            created_at = "2012-02-13T17:29:58Z",
            updated_at = "2016-06-12T03:33:09Z",
            pushed_at = "2016-06-11T14:27:31Z",
            homepage = "homepage",
            stargazers_count = 3,
            watchers_count = 4,
            watchers = "4",
            score = 1.0
    )
}