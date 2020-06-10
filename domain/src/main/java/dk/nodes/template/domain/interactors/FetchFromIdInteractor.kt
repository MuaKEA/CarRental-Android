package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Employee
import dk.nodes.template.repositories.AuthRepository
import dk.nodes.template.repositories.EmployesRepository
import javax.inject.Inject

class FetchFromIdInteractor @Inject constructor(private val employesRepository: EmployesRepository) : BaseInputAsyncInteractor<Int,Employee?> {
    override suspend fun invoke(input: Int): Employee? {
        return employesRepository.fetchFromId(input)
    }

}