package org.sportsstories.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.main_fragment.main_fragment_games
import kotlinx.android.synthetic.main.main_fragment.main_fragment_news
import kotlinx.android.synthetic.main.main_fragment.main_fragment_stories
import org.sportsstories.R
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle_coroutines.extensions.observeLiveData
import org.sportsstories.presentation.base.games.GamesAdapter
import org.sportsstories.presentation.base.news.NewsAdapter
import org.sportsstories.presentation.base.stories.StoriesAdapter
import org.sportsstories.presentation.base.stories.StoriesItem
import org.sportsstories.presentation.decorators.GridMarginDecorations
import org.sportsstories.presentation.fragments.BaseFragment
import org.sportsstories.viewmodel.MainScreenViewModel
import ru.touchin.roboswag.components.utils.UiUtils

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val storiesAdapter = StoriesAdapter()
    private val gamesAdapter = GamesAdapter()
    private val newsAdapter = NewsAdapter()
    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainScreenViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.main_fragment, container, false).also {
                activity?.window?.statusBarColor = ContextCompat.getColor(it.context, R.color.C5)
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        viewModel.fetchStories()
        viewModel.fetchGames()
        viewModel.fetchInitialNews()
    }

    private fun initView() {
        main_fragment_stories.adapter = storiesAdapter
        main_fragment_games.adapter = gamesAdapter
        main_fragment_news.adapter = newsAdapter
        main_fragment_games.addItemDecoration(GridMarginDecorations(UiUtils.OfMetrics.dpToPixels(requireContext(), 8f).toInt()))
        storiesAdapter.itemClickListener = { item, _ ->
            if (item is StoriesItem.AddStories) {
                viewModel.openShootStoriesScreen()
            }
        }
        newsAdapter.itemClickListener = { _, _ ->
            // TODO open stories
            //viewModel.openShootStoriesScreen()
        }
    }

    private fun initObservers() {
        observeLiveData(viewModel.stories)
                .onSuccess(storiesAdapter::submitList)
                .subscribe(this)
        observeLiveData(viewModel.games)
                .onSuccess(gamesAdapter::submitList)
                .subscribe(this)
        observeLiveData(viewModel.news)
                .onSuccess(newsAdapter::submitList)
                .subscribe(this)
    }

}
