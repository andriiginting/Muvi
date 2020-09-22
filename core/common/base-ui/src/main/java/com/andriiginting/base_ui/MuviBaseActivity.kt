package com.andriiginting.base_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.common_di.ViewModelFactory
import javax.inject.Inject

abstract class MuviBaseActivity<V : ViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjector()
        setContentView(getLayoutId())

        viewModel = ViewModelProvider(setObserver(), viewModelFactory).get(setViewModel())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView()
        setData()
    }

    abstract fun getLayoutId(): Int

    abstract fun setupView()

    abstract fun setData()

    abstract fun setupInjector()

    abstract fun setViewModel(): Class<V>

    abstract fun setObserver(): FragmentActivity
}