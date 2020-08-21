package me.pitok.neew.mapper

import me.pitok.mapper.Mapper
import me.pitok.neew.api.response.NeewApiEntity
import me.pitok.neew.entity.NeewEntity
import java.util.Date

class NeewApiEntityToNeewEntityMapper: Mapper<NeewApiEntity, NeewEntity> {

    override fun mapFirstToSecond(first: NeewApiEntity): NeewEntity {
        return NeewEntity(first._id,first.content, Date(first.timestamp.toLong()))
    }

}