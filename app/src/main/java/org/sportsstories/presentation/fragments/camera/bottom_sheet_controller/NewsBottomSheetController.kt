package org.sportsstories.presentation.fragments.camera.bottom_sheet_controller

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.bottom_news_sheet.view.bottom_news_sheet_recyclerview
import kotlinx.android.synthetic.main.fragment_shoot_strories.view.fragment_shoot_stories_news
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_reactions_count
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_time
import kotlinx.android.synthetic.main.item_news_preview.view.item_news_preview_title
import org.sportsstories.R
import org.sportsstories.domain.model.NewsPreview
import org.sportsstories.extensions.toTimeString
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

    private fun onNewsChose(news: NewsPreview) {
        with(bottomSheetContentContainer.fragment_shoot_stories_news) {
            isVisible = true
            item_news_preview_time.text = news.date.toTimeString(context)
            item_news_preview_title.text = news.title
            item_news_preview_reactions_count.text = news.reactionsCount.toString()
        }
    }

}
