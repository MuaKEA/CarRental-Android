package dk.nodes.template.domain.interactors

import dk.nodes.template.models.EditedEmployee
import dk.nodes.template.models.Employee
import dk.nodes.template.repositories.EmployesRepository
import javax.inject.Inject

class SendEmployeeInteractor @Inject constructor(private val employesRepository: EmployesRepository) : BaseInputAsyncInteractor<EditedEmployee, Unit?> {
    override suspend fun invoke(input: EditedEmployee): Unit? {
        return employesRepository.sendEmployee(input)
    }


}