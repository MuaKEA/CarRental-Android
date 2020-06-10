package dk.nodes.template.domain.interactors

import dk.nodes.template.repositories.AuthRepository
import javax.inject.Inject

class FetchTokenInteractor @Inject constructor(private val authRepository: AuthRepository) : BaseAsyncInteractor<String?> {
    override suspend fun invoke(): String? {
        return authRepository.fetch_Token();
    }
}