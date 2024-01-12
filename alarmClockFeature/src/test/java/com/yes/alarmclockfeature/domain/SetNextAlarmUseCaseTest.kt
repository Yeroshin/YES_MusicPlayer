package com.yes.alarmclockfeature.domain

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.domain.usecase.SetNextAlarmUseCase
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.core.domain.models.DomainResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import java.util.Calendar


internal class SetNextAlarmUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val alarmListRepository: AlarmListRepository = mockk()
    private val alarmManagerRepository: AlarmManagerRepository = mockk()
    private val calendar:Calendar= mockk()
    private lateinit var cut: SetNextAlarmUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() = runTest {
        // @MockK
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        cut = SetNextAlarmUseCase (
            testDispatcher,
            alarmListRepository,
            alarmManagerRepository,
            calendar
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        expected:DomainResult<Alarm>,
        currentDay: Int,
        alarms: Flow<List<Alarm>>,
    ) = runTest {
        coEvery {
            alarmListRepository.subscribeAlarms()
        }returns alarms
        every {
            calendar.get(Calendar.DAY_OF_WEEK)
        }returns currentDay
        every {
            calendar.get(Calendar.HOUR)
        }returns 12
        every {
            calendar.get(Calendar.MINUTE)
        }returns 48

        val actual = cut()
        // Assert
        assert(expected == actual)
    }


    companion object {
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainResult.Success(
                        DomainFixtures.getNextAlarm()
                    ),
                    DomainFixtures.getCurrentDay(),
                    DomainFixtures.getAlarms(),
                )

            )
        }
    }


}