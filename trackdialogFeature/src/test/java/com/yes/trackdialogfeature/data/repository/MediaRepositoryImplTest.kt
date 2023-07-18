package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MediaRepositoryImplTest {
    private val mediaDataStore: MediaDataStore = mockk()
    private val mediaRepositoryMapper: MediaRepositoryMapper = mockk()
    private val cut = MediaRepositoryImpl(
        mediaDataStore,
        mediaRepositoryMapper,
    )

    @ParameterizedTest
    @MethodSource("getRootMenuData")
    fun getRootMenu(

    ) {
    }
    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(

    ) {
    }
    @ParameterizedTest
    @MethodSource("getChildMenuItemData")
    fun getChildMenuItem(

    ) {
    }

    companion object {
        @JvmStatic
        fun getRootMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf()
            )
        }
        @JvmStatic
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf()
            )
        }
        @JvmStatic
        fun getChildMenuItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf()
            )
        }
    }
}