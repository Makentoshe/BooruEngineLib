package com.makentoshe.boorusdk.base.request

import com.makentoshe.boorusdk.base.model.Type

open class GetPoolsRequest(
    override val type: Type,
    val category: String? = null,
    val creatorId: Int? = null,
    val order: String? = null,
    val isDeleted: Boolean? = null,
    val isActive: Boolean? = null,
    val creatorName: String? = null,
    val nameMatches: String? = null,
    val descriptionMatches: String? = null,
    val id: List<Int>? = null
) : PoolRequest