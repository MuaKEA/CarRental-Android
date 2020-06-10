package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Departments
import dk.nodes.template.models.DepartmentsInfo
import dk.nodes.template.models.EditedEmployee
import dk.nodes.template.models.Employee
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        private val fetchTokenInteractor : FetchTokenInteractor,
        private val fetchoEmployesInteractor: FetchEmployesInteractor,
        private val fetchdepartmentListInteractor: FetchDepartmentListInteractor,
        private val fetchFromIdInteractor: FetchFromIdInteractor,
        private val sendEmployeeInteractor: SendEmployeeInteractor


) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()
    private val fetchEmployesInteractor = fetchoEmployesInteractor.asResult()
    private val fetchTestIntepretor = fetchTokenInteractor.asResult()
    private val fetchDepartmentListInteractor = fetchdepartmentListInteractor.asResult()
    private val fetchFromIdInt = fetchFromIdInteractor.asResult()
    private val sendEmployeeInt =sendEmployeeInteractor.asResult()


     fun fetchAuth() = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { fetchTestIntepretor.invoke() }
        state = mapResultAuth(result)

    }

    private fun mapResultAuth(result: CompleteResult<String?>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(message = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }


    }

    fun fetchEmployes() = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { fetchEmployesInteractor.invoke() }
        state = mapResultEmp(result)

    }

    private fun mapResultEmp(result: CompleteResult<ArrayList<Employee>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(employyeList = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }


    }

    fun fetchDepartments() = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { fetchDepartmentListInteractor.invoke() }
        state = mapResultdpt(result)

    }

    private fun mapResultdpt(result: CompleteResult<ArrayList<DepartmentsInfo>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(departmentsList = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }


    }

    fun fetchfromID(id : Int) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { fetchFromIdInt.invoke(id) }
        state = mapResualt(result)

    }

    private fun mapResualt(result: CompleteResult<Employee?>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(employee = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }


    }

    fun sendEmployee(employee : EditedEmployee ) = viewModelScope.launch(Dispatchers.Main) {
        val result = withContext(Dispatchers.IO) { sendEmployeeInt.invoke(employee) }
        state = mapSend(result)

    }

    private fun mapSend(result: CompleteResult<Unit?>): MainActivityViewState {
        return when (result) {
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> MainActivityViewState()
        }


    }

    }

