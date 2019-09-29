package org.sportsstories.presentation.fragments.camera

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_news.dialog_news_recyclerview
import org.sportsstories.R
import org.sportsstories.domain.model.NewsPreview
import org.sportsstories.internal.di.app.viewmodel.LifecycleViewModelProviders
import org.sportsstories.lifecycle.event.ContentEvent
import org.sportsstories.presentation.base.news.NewsAdapter
import org.sportsstories.presentation.base.news.NewsListItem
import org.sportsstories.viewmodel.MainScreenViewModel

class NewsBottomSheet : BottomSheetDialogFragment() {

    private val viewModel by lazy {
        LifecycleViewModelProviders.of(this).get(MainScreenViewModel::class.java)
    }

    private val newsAdapter = NewsAdapter()

    companion object {

        private const val REQUEST_CODE = 1 // value isn't necessary

        fun show(fragment: Fragment) {
            NewsBottomSheet().also {
                it.setTargetFragment(fragment, REQUEST_CODE)
            }.show(fragment.fragmentManager!!, "NewsBottomSheet")
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_news, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_FRAME, R.style.TransparentBottomSheetDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchInitialNews(false)
        initViews()
        initObservers()
    }

    private fun initViews() {
        dialog_news_recyclerview.adapter = newsAdapter
        dialog?.setOnShowListener { dialog ->
            val frameLayout =
                    (dialog as BottomSheetDialog).findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_EXPANDED
        }
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)
        newsAdapter.itemClickListener = { item, viewHolder ->
            (item as? NewsListItem.NewsItem)?.let {
                (targetFragment as NewsChooseListener).onNewsChoosed(it.news)
            }
            dismiss()
        }
    }

    private fun initObservers() {
        viewModel.news.observe(this, Observer { event ->
            when (event) {
                is ContentEvent.Success -> newsAdapter.submitList(event.data)
            }
        })
    }

    interface NewsChooseListener {
        fun onNewsChoosed(news: NewsPreview)
    }

}
