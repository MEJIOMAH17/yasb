@file:UseSerializers(UUIDSerializer::class)

package com.mejiomah17.yasb.example.jvm.pet

import com.mejiomah17.yasb.example.jvm.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.UUID

@Serializable
data class Pet(
    val id: UUID,
    val owner: UUID,
    val name: String
)
