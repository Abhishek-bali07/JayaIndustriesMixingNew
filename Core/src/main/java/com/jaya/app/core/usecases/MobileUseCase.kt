package com.jaya.app.core.usecases

import com.jaya.app.core.domain.repositories.MobileRepository
import javax.inject.Inject

class MobileUseCase @Inject constructor(
    private val repository: MobileRepository,
) {
}