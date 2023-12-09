package com.dsb.notepad.ui.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update

interface ViewStateBase
interface UiActionBase
abstract class BaseViewModel<T : ViewStateBase, A : UiActionBase>() : ViewModel() , LifecycleReceiver {
    private val viewStateFlow: MutableStateFlow<T?> = MutableStateFlow(null)

    protected var coroutineScope : CoroutineScope = MainScope()
    protected val lastViewState: T
        get() = viewStateFlow.value ?: defaultViewState()

    abstract fun defaultViewState(): T
    abstract fun onAction(action: A)

    protected fun update(block: (T) -> T) {
        viewStateFlow.update { state ->
            state?.let(block) ?: defaultViewState().let(block)
        }
    }

    fun viewStateStream(): Flow<T> {
        return viewStateFlow.filterNotNull()
    }

    /**
     * Optionally track screen view
     */
    override fun onShow() {
        // Default
    }

    /**
     * Optionally track when screen disappears
     */
    override fun onDisappear() {
        // Default implementation is empty
    }

}