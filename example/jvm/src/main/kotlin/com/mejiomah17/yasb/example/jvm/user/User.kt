@file:UseSerializers(UUIDSerializer::class)

package com.mejiomah17.yasb.example.jvm.user

import com.mejiomah17.yasb.example.jvm.pet.Pet
import com.mejiomah17.yasb.example.jvm.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.UseSerializers
import java.util.UUID

@Serializable
data class User(
    val id: UUID,
    val username: String,
    @Transient
    val password: String? = null,
    val pets: List<Pet>
) {
    constructor(record: UserRecord, pets: List<Pet>) : this(
        id = record.id,
        username = record.username,
        password = record.password,
        pets = pets
    )
}
