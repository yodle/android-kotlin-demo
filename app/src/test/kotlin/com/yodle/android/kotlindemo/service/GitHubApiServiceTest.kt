package com.yodle.android.kotlindemo.service

import com.yodle.android.kotlindemo.MainApp
import com.yodle.android.kotlindemo.dagger.AppComponent
import com.yodle.android.kotlindemo.model.Repository
import com.yodle.android.kotlindemo.model.RepositoryOwner
import com.yodle.android.kotlindemo.model.RepositorySearchResults
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable
import kotlin.test.assertEquals

class GitHubApiServiceTest {

    lateinit var gitHubApiService: GitHubApiService
    lateinit var gitHubService: GitHubService

    @Before
    fun setUp() {
        MainApp.graph = mock(AppComponent::class.java)
        gitHubApiService = mock(GitHubApiService::class.java)
        gitHubService = GitHubService()
        gitHubService.gitHubApiService = gitHubApiService
    }

    @Test
    fun searchRepositories_whenEmptyQuery_returnsEmptyItems() {
        val repositories = gitHubService.searchRepositories("").toBlocking().first()

        assertEquals(arrayListOf(), repositories)
        verifyZeroInteractions(gitHubApiService)
    }

    @Test
    fun searchRepositories_whenAllWhitespaceQuery_returnsEmptyItems() {
        val repositories = gitHubService.searchRepositories("   ").toBlocking().first()

        assertEquals(arrayListOf(), repositories)
        verifyZeroInteractions(gitHubApiService)
    }

    @Test
    fun searchRepositories_whenValidQuery_returnsApiItems() {
        val repository = buildRepository()
        val repositorySearchResults = RepositorySearchResults(1, false, arrayListOf(repository))
        `when`(gitHubApiService.searchRepositories("test")).thenReturn(Observable.just(repositorySearchResults))

        val repositories = gitHubService.searchRepositories("test").toBlocking().first()

        assertEquals(arrayListOf(repository), repositories)
        verify(gitHubApiService).searchRepositories("test")
    }

    /**
     * Data helper functions
     */
    fun buildRepository() = Repository(
            id = 1,
            name = "name",
            full_name = "full_name",
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