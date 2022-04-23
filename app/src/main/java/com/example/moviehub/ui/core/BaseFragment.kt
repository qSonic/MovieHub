package com.example.moviehub.ui.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected var binding: T? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = createViewBinding(
            inflater = inflater,
            container = container
        )
        this.binding = binding
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = this.binding
        if (binding != null) {
            onBindingCreated(
                binding = binding,
                savedInstanceState = savedInstanceState
            )
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }

    @CallSuper
    protected open fun onBindingCreated(binding: T, savedInstanceState: Bundle?) = Unit

    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T
}