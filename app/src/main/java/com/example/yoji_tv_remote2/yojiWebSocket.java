package com.example.yoji_tv_remote2;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.samsung.multiscreen.Search;
import com.samsung.multiscreen.Service;

import java.io.IOException;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class yojiWebSocket {
    static WebSocket ws = null; //nv-websocket-client
    static WebSocketFactory factory = null;

    public static void setWebSocket(WebSocketFactory _factory){
        factory=_factory;
    }

    //-------------- nv-websocket-client start
    //@Override
    protected void onDestroy() {
        //super.onDestroy();

        if (ws != null) {
            ws.disconnect();
            ws = null;
        }
    }

    public static void sendMessage(View v,String strKey) {
        //{"method":"ms.remote.control","params":{"Cmd": "Click","DataOfCmd": "KEY_RIGHT","Option": "false","TypeOfRemote": "SendRemoteKey"}}
        if (ws.isOpen()) {
            //ws.sendText("{\"method\":\"ms.remote.control\",\"params\":{\"Cmd\": \"Click\",\"DataOfCmd\": \"KEY_RIGHT\",\"Option\": \"false\",\"TypeOfRemote\": \"SendRemoteKey\"}}");
            ws.sendText("{\"method\":\"ms.remote.control\",\"params\":{\"Cmd\": \"Click\",\"DataOfCmd\": \"" + strKey + "\",\"Option\": \"false\",\"TypeOfRemote\": \"SendRemoteKey\"}}");
        }
    }

    public static void createWebSocketYoji() {
        // Create a WebSocket. The timeout value set above is used.
        try {
            ws = factory.createSocket("ws://192.168.0.3:8001/api/v2/channels/samsung.remote.control?name=cmNjbGk=");

            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    Log.d("TAG", "onTextMessage: " + message);
                }
            });

            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------- nv-websocket-client end

    }
