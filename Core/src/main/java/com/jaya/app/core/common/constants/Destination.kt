package com.jaya.app.core.common.constants

import android.util.Log


sealed class Destination(
 protected val route: String,
 vararg arguments: Any) {

 val fullRoute: String = if (arguments.isEmpty()) route else {
  val builder = StringBuilder(route)
  arguments.forEach { builder.append("/{${it}}") }
     Log.e("FullRoute", builder.toString())
  builder.toString()
 }

 sealed class NoArgumentsDestination(route: String) : Destination(route) {
  operator fun invoke(): String = route
 }

 object SplashScreen : NoArgumentsDestination(AppRoutes.SPLASH)

 object MobileNumberScreen : NoArgumentsDestination(AppRoutes.MOBILE)


 object OtpScreen : NoArgumentsDestination(AppRoutes.OTP)


 object DashboardScreen : NoArgumentsDestination(AppRoutes.DASHBOARD)



 object ProductAddScreen:NoArgumentsDestination(AppRoutes.ADDSCREEN)

// object DashboardScreen : Destination(AppRoutes.DASHBOARD, "productId"){
//  const val productId_KEY = "productId"
//  operator  fun  invoke(productId: String?): String = route.appendParams(
//   productId_KEY  to productId
//  )
// }

 object ProductionDetailScreen :Destination(AppRoutes.DETAILSCREEN, "productId"){
  const val productId_KEY = "productId"

  operator  fun invoke(productId: String) : String = route.appendParams(
   productId_KEY to productId
  )
 }



}

private fun String.appendParams(vararg params: Pair<String, Any?>): String {
 val builder = StringBuilder(this)

 params.forEach {
  it.second?.toString()?.let { arg ->
   builder.append("/$arg")
  }
 }

 return builder.toString()
}