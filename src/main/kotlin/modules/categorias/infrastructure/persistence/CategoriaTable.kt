package com.finanzasana.modules.categorias.infrastructure.persistence

import org.jetbrains.exposed.dao.id.IntIdTable

object CategoriaTable : IntIdTable("categorias") {
    val nombre = varchar("nombre", 100)
}
