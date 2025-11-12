package pt.iade.ei.firstapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.firstapp.data.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    data class UserSession(
        val userId: Long? = null,
        val nome: String? = null,
        val email: String? = null,
        val fotoPerfil: String? = null
    )

    private val _session = MutableStateFlow(UserSession())
    val session: StateFlow<UserSession> = _session

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun signup(nome: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val res = repo.signup(nome, email, password)
                if (res.ok && res.userId != null) {
                    _session.value = UserSession(
                        userId = res.userId, nome = res.nome, email = res.email, fotoPerfil = res.fotoPerfil
                    )
                    onSuccess()
                } else {
                    _error.value = res.message.ifBlank { "Falha ao criar conta" }
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val res = repo.login(email, password)
                if (res.ok && res.userId != null) {
                    _session.value = UserSession(
                        userId = res.userId, nome = res.nome, email = res.email, fotoPerfil = res.fotoPerfil
                    )
                    onSuccess()
                } else {
                    _error.value = res.message.ifBlank { "Credenciais inválidas" }
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun logout() {
        _session.value = UserSession()
    }
}

