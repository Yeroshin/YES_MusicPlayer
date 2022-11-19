package com.yes.trackdiialogfeature.data.mapper


import com.yes.trackdiialogfeature.data.repository.entity.MediaParam
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals



@RunWith(JUnit4::class)
internal class MenuToParamMapperTest {
    val mapper= MenuToParamMapper()


    val itemsParam= arrayListOf<MediaItem>()
    val menu= Menu("categories",0,null,itemsParam)

    @Test
    fun map_shouldReturnParamForAtists() {

        val item=MediaItem()
        item.title="artists"
        itemsParam.add(item)
        val actual:MediaParam=mapper.map(menu)
        val expected= MediaParam("artists",null)
        assertEquals(actual.what,expected.what)
        assertEquals(actual.where,expected.where)
    }
}