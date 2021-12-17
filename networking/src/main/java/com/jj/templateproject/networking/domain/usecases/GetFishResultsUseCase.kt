package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.core.domain.result.UseCaseResult
import com.jj.templateproject.networking.data.repositories.FishDataRepository
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData

class GetFishResultsUseCase(private val fishDataRepository: FishDataRepository,
        private val textHelper: TextHelper) {

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

    private fun prepareFishResults(fishResults: ArrayList<FishDataResponseItem>): List<FishItemViewData> {
        return fishResults.map {
            val speciesCleanString = textHelper.htmlToString(it.speciesName)
            val biologyCleanString = textHelper.htmlToString(it.biology)
            val fisheriesRegionCleanString = textHelper.htmlToString(it.nOAAFisheriesRegion)
            val sugarsTotalCleanString = textHelper.htmlToString(it.sugarsTotal)
            val tasteCleanString = textHelper.htmlToString(it.taste)
            DefaultFishItemViewData(
                    speciesCleanString, biologyCleanString, fisheriesRegionCleanString, sugarsTotalCleanString, tasteCleanString)
        }
    }
}