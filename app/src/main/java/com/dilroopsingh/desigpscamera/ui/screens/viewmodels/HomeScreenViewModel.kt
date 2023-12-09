package com.dilroopsingh.desigpscamera.ui.screens.viewmodels

import com.dilroopsingh.desigpscamera.preferences.Preferences
import com.dilroopsingh.desigpscamera.ui.architecture.doNothing
import com.dsb.notepad.ui.architecture.BaseViewModel
import com.dsb.notepad.ui.architecture.UiActionBase
import com.dsb.notepad.ui.architecture.ViewStateBase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val preferences: Preferences
): BaseViewModel<HomeScreenViewModel.ViewState, HomeScreenViewModel.UIActions>(){

    override fun defaultViewState(): ViewState = ViewState()

    override fun onAction(action: UIActions) {
        when (action) {
            UIActions.MAPClicked -> doNothing()
        }
    }

    data class ViewState(
        val isManualMode: Boolean = false
    ) : ViewStateBase

    sealed class UIActions : UiActionBase {
        object MAPClicked : UIActions()
    }
}