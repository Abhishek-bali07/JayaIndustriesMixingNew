package com.jaya.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jaya.app.core.common.constants.Destination
import com.jaya.app.core.utils.helper.NavigationIntent
import com.jaya.app.navigation.screen_transition.AppScreenTransitions
import com.jaya.app.presentation.ui.screens.intro.SplashScreen
import com.jaya.app.presentation.ui.screens.login.MobileNumberScreen
import com.jaya.app.presentation.ui.screens.login.OtpScreen
import com.jaya.app.presentation.ui.view_models.BaseViewModel
import kotlinx.coroutines.channels.Channel

@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    navigationChannel: Channel<NavigationIntent>,
    paddingValues: PaddingValues,
    baseViewModel: BaseViewModel

){
    navHostController.NavEffects(navigationChannel)
    AppNavHost(
            navController = navHostController,
            startDestination = Destination.SplashScreen,
            modifier = Modifier.padding(paddingValues),
            enterTransition = AppScreenTransitions.ScreenEnterTransition,
            popEnterTransition = AppScreenTransitions.ScreenPopEnterTransition,
            exitTransition = AppScreenTransitions.ScreenExitTransition,
            popExitTransition = AppScreenTransitions.ScreenPopExitTransition,
        ){
        composable(destination = Destination.SplashScreen){
            SplashScreen()
        }

        composable(destination = Destination.MobileNumberScreen){
           MobileNumberScreen()
        }


        composable(destination =  Destination.OtpScreen){
            OtpScreen()
        }

    }
}