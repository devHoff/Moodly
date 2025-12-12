package pt.iade.ei.firstapp.data.repository

import pt.iade.ei.firstapp.data.remote.EventApi
import pt.iade.ei.firstapp.data.remote.RetrofitClient

class EventRepository {

    private val api = RetrofitClient.eventApi()

    data class UiEvent(
        val id: Long,
        val titulo: String,
        val desc: String?,
        val local: String?,
        val dataRaw: String?,
        val estado: String,
        val isOwner: Boolean,
        val criadorNome: String?
    ) {
        val isCancelled: Boolean
            get() = estado.equals("cancelado", true)

        val dataFormatted: String
            get() {
                val raw = dataRaw ?: return "-"
                return try {
                    // esperado: yyyy-MM-ddTHH:mm[:ss]
                    val year = raw.substring(0, 4)
                    val month = raw.substring(5, 7)
                    val day = raw.substring(8, 10)
                    val time = raw.substring(11, 16)
                    "$day/$month/$year $time"
                } catch (_: Exception) {
                    raw.replace("T", " ")
                }
            }
    }

    suspend fun loadEvents(userId: Long): Result<List<UiEvent>> {
        return try {
            val raw = api.eventsForUser(userId)
            val mapped = raw.mapNotNull { dto ->
                val estado = dto.estado?.lowercase() ?: "pendente"
                val incluir = dto.isOwner || estado == "aceite" || estado == "cancelado"
                if (!incluir) return@mapNotNull null

                UiEvent(
                    id = dto.id,
                    titulo = dto.titulo,
                    desc = dto.descricao,
                    local = dto.local,
                    dataRaw = dto.dataEvento,
                    estado = dto.estado ?: "pendente",
                    isOwner = dto.isOwner,
                    criadorNome = dto.criadorNome
                )
            }.sortedBy { it.dataRaw ?: "" }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun create(
        cid: Long,
        tit: String,
        desc: String?,
        loc: String?,
        data: String?,
        convidadoIds: List<Long>
    ): Result<Unit> {
        return try {
            val body = EventApi.CreateEventBody(cid, tit, desc, loc, data, convidadoIds)
            val r = api.createEvent(body)
            if (r.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Erro ${r.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun accept(eventId: Long, userId: Long) = wrap { api.acceptInvite(eventId, userId) }
    suspend fun leave(eventId: Long, userId: Long) = wrap { api.leaveEvent(eventId, userId) }
    suspend fun cancel(eventId: Long, userId: Long) = wrap { api.cancelEvent(eventId, userId) }
    suspend fun hide(eventId: Long, userId: Long) = wrap { api.hideEvent(eventId, userId) }

    private suspend fun wrap(block: suspend () -> retrofit2.Response<Void>): Result<Unit> {
        return try {
            val r = block()
            if (r.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Erro ${r.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

