package com.cxyzy.websocket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), MessageListener {
//    private val serverUrl = "ws://192.168.18.145:8086/socketServer/abc"
    private val serverUrl = "wss://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self"
	
	// ref url
	// https://www.piesocket.com/websocket-tester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WebSocketManager.init(serverUrl, this)
        connectBtn.setOnClickListener {
            thread {
                kotlin.run {
                    WebSocketManager.connect()
                }
            }
        }
        clientSendBtn.setOnClickListener {
            if (WebSocketManager.sendMessage("客户端发送")) {
                addText("客户端发送\n")
            }
        }
        closeConnectionBtn.setOnClickListener {
            WebSocketManager.close()
        }
    }

    override fun onConnectSuccess() {
        addText("Connect success\n")
    }

    override fun onConnectFailed() {
        addText("connection failed\n")
    }

    override fun onClose() {
        addText("close\n")
    }

    override fun onMessage(text: String?) {
        addText("onMessage：$text\n")
    }

    private fun addText(text: String?) {
        runOnUiThread {
            contentEt.text.append(text)
        }

        println("Final Response: $text")
    }

    override fun onDestroy() {
        super.onDestroy()
        WebSocketManager.close()
    }
}
