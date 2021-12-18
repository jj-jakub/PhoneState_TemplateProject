package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData

class FilterSpeciesByNameUseCase {

    operator fun invoke(fishName: String, fishItemsList: List<FishItemViewData>): UseCaseResult<List<FishItemViewData>> {
        val filteredList = if (fishName.isEmpty()) fishItemsList
        else fishItemsList.filter { it.fishName?.contains(fishName, ignoreCase = true) == true }
        return UseCaseResult.Success(filteredList)
    }
}