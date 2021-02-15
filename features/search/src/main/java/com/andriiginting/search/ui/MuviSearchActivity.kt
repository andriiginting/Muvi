package com.andriiginting.search.ui

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.airbnb.deeplinkdispatch.DeepLink
import com.andriiginting.base_ui.MuviBaseActivity
import com.andriiginting.base_ui.MuviBaseAdapter
import com.andriiginting.core_network.MovieItem
import com.andriiginting.navigation.DetailNavigator
import com.andriiginting.search.R
import com.andriiginting.search.di.MuviSearchInjector
import com.andriiginting.uttils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_muvi_search.*
import java.util.*
import java.util.concurrent.TimeUnit

@DeepLink("muvi://search")
class MuviSearchActivity : MuviBaseActivity<MuviSearchViewModel>() {

    private lateinit var searchAdapter: MuviBaseAdapter<MovieItem, MuviSearchViewHolder>

    private val searchBarTextChangeSubject = PublishSubject.create<CharSequence>()

    override fun getLayoutId(): Int = R.layout.activity_muvi_search

    override fun setupView() {
        setupAdapter()
        setupSearchList()
        setUpFieldObserve()
        setupObserverCommand()
        ivClearText.apply {
            bringToFront()
            setOnClickListener { clearSearchBar() }
        }
    }

    override fun setData() {
        viewModel.initialView()
    }

    override fun setupInjector() = MuviSearchInjector.of(this)

    override fun setViewModel(): Class<MuviSearchViewModel> = MuviSearchViewModel::class.java

    override fun setObserver(): FragmentActivity = this

    private fun setUpFieldObserve() {
        etSearchMovie.apply {
            setOnEditorActionListener(keyboardEditActionListener())
            imeOptions = EditorInfo.IME_ACTION_SEARCH
        }
        addSearchBarTextChangeListener()
        addListenerToTextChangeSubject()
    }

    private fun keyboardEditActionListener() =
        TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                searchBarTextChangeSubject.onNext(v.text)
                return@OnEditorActionListener true
            }
            false
        }

    private fun addSearchBarTextChangeListener() = etSearchMovie.afterTextChanged { searchBarTextChangeSubject.onNext(it) }

    private fun addListenerToTextChangeSubject() =
        searchBarTextChangeSubject
            .doOnNext { if (it.isBlank()) ivClearText.makeGone() else ivClearText.makeVisible() }
            .doOnNext { if (it.isBlank()) viewModel.onSearchTextCleared() }
            .map { text -> text.toString().toLowerCase(Locale.getDefault()).trim() }
            .debounce(DEBOUNCE_TIME_LIMIT, TimeUnit.MILLISECONDS)
            .filter { it.isNotBlank() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewModel.submittedQuery(it) }

    private fun setupSearchList() {
        rvSearchMovie.apply {
            setVertical(this@MuviSearchActivity)
            adapter = searchAdapter
            bringToFront()
        }
    }

    private fun setupAdapter() {
        searchAdapter = MuviBaseAdapter({ parent, _ ->
            MuviSearchViewHolder.inflate(parent)
        }, { viewHolder, _, item ->
            viewHolder.apply {
                bind(item)
                setCardActionListener {
                    DetailNavigator
                        .getDetailPageIntent(item.id)
                        .also(::startActivity)
                }
            }
        })
    }

    private fun clearSearchBar() {
        etSearchMovie.text.clear()
        ivClearText.makeGone()
        searchAdapter.clear()
    }

    private fun setupObserverCommand() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is MuviSearchViewState.ShowLoading -> {
                    searchAdapter.clear()
                    pbLoadingIndicator.makeVisible()
                    ivEmptySearch.makeGone()
                }
                is MuviSearchViewState.HideLoading -> {
                    pbLoadingIndicator.makeGone()
                    ivEmptySearch.makeGone()
                }
                is MuviSearchViewState.GetMovieDataError -> {
                    ivEmptySearch.makeGone()
                    errorLayout.showErrorScreen()
                }
                is MuviSearchViewState.GetMovieData -> {
                    searchAdapter.add(state.data)
                    errorLayout.hideErrorScreen()
                }
                MuviSearchViewState.EmptyScreen -> {
                    ivEmptySearch.makeVisible()
                }
                MuviSearchViewState.HideKeyboard -> {
                    hideKeyboard()
                }
                MuviSearchViewState.ClearText -> clearSearchBar()
            }
        })
    }

    companion object {
        private const val DEBOUNCE_TIME_LIMIT = 400L
        private const val SKIP_INITIAL_TYPE = 1L
    }
}