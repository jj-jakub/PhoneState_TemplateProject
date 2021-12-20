package com.jj.templateproject.monitoring.data.server.server

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

class HttpServerManager(private val context: Context) : ServerManager {

    override fun startServer(port: Int) {
        val app: HttpHandler = routes(
                "/capture" bind GET to { _: Request -> Response(OK).body("done") },
                "/ping" bind GET to { _: Request -> Response(Status.CONNECTION_REFUSED).body("Impossible") }
        )
        val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ip: String = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)
        val serverConfig = Netty(port)
        app.asServer(serverConfig).start()
        Log.d("ABAB", "Started server on $ip ${serverConfig.port}")
    }
}