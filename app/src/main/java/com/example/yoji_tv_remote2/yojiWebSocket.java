package com.example.yoji_tv_remote2;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;
import com.samsung.multiscreen.Search;
import com.samsung.multiscreen.Service;

import java.io.IOException;

import static com.koushikdutta.async.AsyncServer.LOGTAG;

public class yojiWebSocket {
    static WebSocket ws = null; //nv-websocket-client
    static WebSocketFactory factory = null;

    public static void setWebSocket(WebSocketFactory _factory) {
        factory = _factory;
    }

    public static void initializeSocket() {
        // Create a WebSocket factory and set 5000 milliseconds as a timeout
        // value for socket connection.
        factory = new WebSocketFactory().setConnectionTimeout(5000);
        //setWebSocket(factory);
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

    public static int sendMessage(View v, String strKey) {
        //{"method":"ms.remote.control","params":{"Cmd": "Click","DataOfCmd": "KEY_RIGHT","Option": "false","TypeOfRemote": "SendRemoteKey"}}
        if (ws.isOpen()) {
            //ws.sendText("{\"method\":\"ms.remote.control\",\"params\":{\"Cmd\": \"Click\",\"DataOfCmd\": \"KEY_RIGHT\",\"Option\": \"false\",\"TypeOfRemote\": \"SendRemoteKey\"}}");
            ws.sendText("{\"method\":\"ms.remote.control\",\"params\":{\"Cmd\": \"Click\",\"DataOfCmd\": \"" + strKey + "\",\"Option\": \"false\",\"TypeOfRemote\": \"SendRemoteKey\"}}");
            return 0;
        }else{
            initializeSocket();
            return -1;
        }
    }

    public static void createWebSocketYoji() {
        // Create a WebSocket. The timeout value set above is used.
        try {
            if (factory == null) {
                initializeSocket();
            }
            ws = factory.createSocket("ws://192.168.0.3:8001/api/v2/channels/samsung.remote.control?name=cmNjbGk=");
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    Log.d("TAG", "onTextMessage: " + message);
                }
            });
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
                    //super.onSendError(websocket, cause, frame);
                }

            });

            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //-------------- nv-websocket-client end

}
