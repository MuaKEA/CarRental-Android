package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Departments
import dk.nodes.template.models.DepartmentsInfo
import dk.nodes.template.models.Employee
import dk.nodes.template.repositories.EmployesRepository
import javax.inject.Inject

class FetchDepartmentListInteractor @Inject constructor(private val employesRepository: EmployesRepository) : BaseAsyncInteractor<ArrayList<DepartmentsInfo>> {
    override suspend fun invoke(): ArrayList<DepartmentsInfo> {
        return employesRepository.fetchDepartementNames()

    }
}