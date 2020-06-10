package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Employee
import dk.nodes.template.repositories.AuthRepository
import dk.nodes.template.repositories.EmployesRepository
import javax.inject.Inject

class FetchEmployesInteractor @Inject constructor(private val employesRepository: EmployesRepository) : BaseAsyncInteractor<ArrayList<Employee>> {
    override suspend fun invoke(): ArrayList<Employee> {
        return employesRepository.fetchemployees()

        }
}