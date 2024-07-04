package com.dedee.service.conduct

import com.dedee.entities.ConductEntity
import com.dedee.model.conduct.ConductRequest
import com.dedee.model.conduct.ConductResponse
import org.ktorm.database.Database
import org.ktorm.dsl.*
import util.Constants.emptyString

class ConductServiceImpl(
    private val db: Database,
) : ConductService{
    override suspend fun getAllConducts(): List<ConductResponse>? {
        val conduct = db.from(ConductEntity).select()
            .map {
                val uniqueConductId = it[ConductEntity.uniqueConductId] ?: emptyString
                val uniqueTeacherId = it[ConductEntity.uniqueTeacherId] ?: emptyString
                val uniqueClassId = it[ConductEntity.uniqueClassId] ?: emptyString
                val uniqueAttendanceId = it[ConductEntity.uniqueAttendanceId] ?: emptyString
                val uniqueStudentId = it[ConductEntity.uniqueStudentId] ?: emptyString
                val uniqueTermName = it[ConductEntity.uniqueTermName] ?: emptyString
                val conduct = it[ConductEntity.conduct] ?: emptyString
                val interests = it[ConductEntity.interests] ?: emptyString
                ConductResponse(uniqueConductId, uniqueStudentId, uniqueAttendanceId, uniqueTeacherId, uniqueClassId, uniqueTermName, conduct, interests)
            }
        return conduct.ifEmpty { null }
    }

    override suspend fun addConduct(conduct: ConductRequest, uniqueConductId: String): Int {
        return db.insert(ConductEntity){
            set(it.uniqueConductId, conduct.uniqueConductId)
            set(it.uniqueClassId, conduct.uniqueClassId)
            set(it.uniqueTeacherId, conduct.uniqueTeacherId)
            set(it.uniqueStudentId, conduct.uniqueStudentId)
            set(it.uniqueTermName, conduct.uniqueTermName)
            set(it.uniqueAttendanceId, conduct.uniqueAttendanceId)
            set(it.interests, conduct.interests)
            set(it.conduct, conduct.conduct)
        }
    }

    override suspend fun getConduct(uniqueConductId: String): ConductResponse?{
        return db.from(ConductEntity)
            .select()
            .where{ ConductEntity.uniqueConductId eq uniqueConductId}
            .map {
                val uniqueTeacherId = it[ConductEntity.uniqueTeacherId] ?: emptyString
                val uniqueClassId = it[ConductEntity.uniqueClassId] ?: emptyString
                val uniqueAttendanceId = it[ConductEntity.uniqueAttendanceId] ?: emptyString
                val uniqueStudentId = it[ConductEntity.uniqueStudentId] ?: emptyString
                val uniqueTermName = it[ConductEntity.uniqueTermName] ?: emptyString
                val conduct = it[ConductEntity.conduct] ?: emptyString
                val interests = it[ConductEntity.interests] ?: emptyString
                ConductResponse(uniqueConductId, uniqueStudentId, uniqueAttendanceId, uniqueTeacherId, uniqueClassId, uniqueTermName, conduct, interests)
            }.firstOrNull()

    }

    override suspend fun updateConduct(uniqueConductId: String, updatedConduct: ConductRequest): ConductResponse? {
        val rowsAffected = db.update(ConductEntity){
            set(it.uniqueClassId, updatedConduct.uniqueClassId)
            set(it.uniqueTeacherId, updatedConduct.uniqueTeacherId)
            set(it.uniqueStudentId, updatedConduct.uniqueStudentId)
            set(it.uniqueTermName, updatedConduct.uniqueTermName)
            set(it.uniqueAttendanceId, updatedConduct.uniqueAttendanceId)
            set(it.interests, updatedConduct.interests)
            set(it.conduct, updatedConduct.conduct)
            where { it.uniqueConductId eq uniqueConductId }
        }
        return if (rowsAffected < 1){
            null
        }else {
            db.from(ConductEntity)
                .select()
                .where { ConductEntity.uniqueConductId eq uniqueConductId }
                .map {
                    val uniqueTeacherId = it[ConductEntity.uniqueTeacherId] ?: emptyString
                    val uniqueClassId = it[ConductEntity.uniqueClassId] ?: emptyString
                    val uniqueAttendanceId = it[ConductEntity.uniqueAttendanceId] ?: emptyString
                    val uniqueStudentId = it[ConductEntity.uniqueStudentId] ?: emptyString
                    val uniqueTermName = it[ConductEntity.uniqueTermName] ?: emptyString
                    val conduct = it[ConductEntity.conduct] ?: emptyString
                    val interests = it[ConductEntity.interests] ?: emptyString
                    ConductResponse(uniqueConductId, uniqueStudentId, uniqueAttendanceId, uniqueTeacherId, uniqueClassId, uniqueTermName, conduct, interests)
                }.firstOrNull()
        }
    }

    override suspend fun deleteConduct(uniqueConductId: String): Int {
        return db.delete(ConductEntity) {
            ConductEntity.uniqueConductId eq uniqueConductId
        }
    }

}