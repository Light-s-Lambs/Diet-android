package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.RepositoryErrorException
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


class DeleteLifeStyleInfoUseCaseTest {
    lateinit var deleteUseCase: DeleteLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteUseCase = DeleteLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenDeleteDataSuccess() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        coEvery { repository.delete(dateString) } returns flowOf(Unit)

        runBlocking {
            deleteUseCase(dateString)
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenDeleteFailedButRepoHasData_raiseRepositoryErrorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = RepositoryErrorException("Delete Failed. But Found Data. Check Repository.")

        coEvery { repository.delete(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString)
                Assert.fail()
            }.onFailure {
                when (it) {
                    is RepositoryErrorException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_raiseNoMatchDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = NoMatchDataException("Delete Failed. There is No Match Date")

        coEvery { repository.delete(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString)
                Assert.fail()
            }.onFailure {
                when (it) {
                    is NoMatchDataException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenUnexpectedErrorOccurs_raiseUnexpectedBehaviorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = UnexpectedBehaviorException("Load Failed. Something weired happened.")

        coEvery { repository.delete(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString)
                Assert.fail()
            }.onFailure {
                when (it) {
                    is UnexpectedBehaviorException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.delete(dateString) }
    }
}
