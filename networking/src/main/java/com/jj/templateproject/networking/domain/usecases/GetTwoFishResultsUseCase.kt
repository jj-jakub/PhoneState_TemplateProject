package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData

class GetTwoFishResultsUseCase(private val fishDataRepository: FishDataRepository, textHelper: TextHelper) :
    GetFishResultsUseCase(textHelper) {

    suspend operator fun invoke(): UseCaseResult<List<FishItemViewData>> {
        val fishResults: ArrayList<FishDataResponseItem> = arrayListOf()

        try {
            fishDataRepository.fetchSpecifiedSpeciesInfo("red-snapper")
                .onSuccess {
                    getValue()?.let { list -> fishResults.addAll(list) }
                }.onError {
                    throw exception
                }

            fishDataRepository.fetchSpecifiedSpeciesInfo("canary-rockfish")
                .onSuccess {
                    getValue()?.let { list -> fishResults.addAll(list) }
                }.onError {
                    throw exception
                }
        } catch (e: Exception) {
            return UseCaseResult.Error(prepareFishResults(fishResults), e)
        }

        return UseCaseResult.Success(prepareFishResults(fishResults))
    }
}