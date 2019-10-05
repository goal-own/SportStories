package org.sportsstories.presentation.fragments.camera.bottom_sheet_controller

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.bottom_news_sheet.view.bottom_news_sheet_recyclerview
import org.sportsstories.R
import org.sportsstories.domain.model.NewsPreview
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.base.news.NewsAdapter
import org.sportsstories.presentation.base.news.NewsListItem
import org.sportsstories.presentation.controllers.FullScreenBottomSheetController
import org.sportsstories.viewmodel.MainScreenViewModel

class NewsBottomSheetController(
        lifecycleOwner: LifecycleOwner,
        parentContainer: View
) : FullScreenBottomSheetController(parentContainer.findViewById(R.id.bottom_news_sheet)) {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(lifecycleOwner).get(MainScreenViewModel::class.java)
    }

    var onNewsChose: (NewsPreview) -> Unit = {}

    private val newsAdapter = NewsAdapter()

    init {
        bottomSheetContentContainer.bottom_news_sheet_recyclerview.adapter = newsAdapter
        viewModel.news.observe(lifecycleOwner, Observer { event ->
            when (event) {
                is ContentEvent.Success -> newsAdapter.submitList(event.data)
            }
        })
        newsAdapter.itemClickListener = { item, _ ->
            onNewsChose((item as NewsListItem.NewsItem).news)
            dismiss()
        }
        viewModel.fetchInitialNews(false)
    }

}
