package com.sugarspoon.home

import androidx.lifecycle.viewModelScope
import com.sugarspoon.data.repositories.FoundsRepository
import com.sugarspoon.data.request.FoundsRequest
import com.sugarspoon.data.response.CredentialsResponse
import com.sugarspoon.data.response.FoundsResponse
import com.sugarspoon.data.response.DetailsItem
import com.sugarspoon.network.interceptors.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: FoundsRepository,
    val sessionManager: SessionManager
) : BaseViewModelMVI<HomeScreenState, HomeScreenEvents>(HomeScreenState()) {

    override fun handleEvents(oldState: HomeScreenState, screenEvents: HomeScreenEvents) {
        when (screenEvents) {
            is HomeScreenEvents.FetchCredentials -> {
                getCredentials(oldState)
            }
            is HomeScreenEvents.FetchFundsDetails -> {
                getFundsDetails(
                    oldState = oldState,
                    code = screenEvents.code
                )
            }
        }
    }

    private fun getCredentials(oldState: HomeScreenState) = viewModelScope.launch {
        repository.getCredentials()
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "Erro de token: $it"
                        )
                    )
                }
            }
            .collect {
                createNewState(
                    newState = oldState.copy(
                        error = "Sucesso de token: ${it.accessToken}"
                    )
                )
                sessionManager.saveAuthToken(it.accessToken)
                getFounds(oldState)
            }
    }

    private fun getFounds(oldState: HomeScreenState) = viewModelScope.launch {
        repository.getFounds(request = FoundsRequest())
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "Erro de fundos: $it",
                        )
                    )
                }
            }
            .collect {
                createNewState(
                    newState = oldState.copy(
                        founds = it
                    )
                )
                //getFounds()
            }
    }

    private fun getFundsDetails(oldState: HomeScreenState, code: Int) = viewModelScope.launch {
        repository.getFundsDetail(code)
            .catch {
                withContext(Main) {
                    createNewState(
                        newState = oldState.copy(
                            error = "Erro de detalhes de fundos: $it",
                            openModal = true
                        )
                    )
                }
            }
            .collect {
                createNewState(
                    newState = oldState.copy(
                        fundsDetailResponse = it,
                        openModal = true
                    )
                )
            }
    }

    fun getCredential() = run {
        emitEvent(HomeScreenEvents.FetchCredentials)
    }

    fun fetchFoundsDetail(code: Int) = run {
        emitEvent(HomeScreenEvents.FetchFundsDetails(code))
    }
}

data class HomeScreenState(
    val credentialsResponse: CredentialsResponse? = null,
    val founds: List<FoundsResponse?> = listOf(),
    val error: String = "",
    val openModal: Boolean = false,
    val fundsDetailResponse: DetailsItem? = null
) : ScreeenState

sealed class HomeScreenEvents : ScreenEvents {
    object FetchCredentials : HomeScreenEvents()
    data class FetchFundsDetails(val code: Int) : HomeScreenEvents()
}