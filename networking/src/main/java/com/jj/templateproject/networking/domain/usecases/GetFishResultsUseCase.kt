package com.jj.templateproject.networking.domain.usecases

import com.jj.templateproject.core.data.text.TextHelper
import com.jj.templateproject.networking.domain.fishdata.FishDataResponseItem
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.DefaultFishItemViewData
import com.jj.templateproject.networking.framework.presentation.adapters.fishlistitems.FishItemViewData

abstract class GetFishResultsUseCase(private val textHelper: TextHelper) {

    protected fun prepareFishResults(fishResults: ArrayList<FishDataResponseItem>): List<FishItemViewData> {
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