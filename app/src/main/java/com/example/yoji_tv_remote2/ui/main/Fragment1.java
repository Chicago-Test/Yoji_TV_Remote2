package com.example.yoji_tv_remote2.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yoji_tv_remote2.R;
import com.example.yoji_tv_remote2.yojiWebSocket;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.samsung.multiscreen.Search;
import com.samsung.multiscreen.Service;

import static com.example.yoji_tv_remote2.yojiWebSocket.createWebSocketYoji;
import static com.example.yoji_tv_remote2.yojiWebSocket.setWebSocket;
import static com.koushikdutta.async.AsyncServer.LOGTAG;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment1 extends Fragment {
    // yoji
    WebSocket ws = null; //nv-websocket-client
    WebSocketFactory factory = null;
    View root = null;


    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static Fragment1 newInstance(int index) {
        Fragment1 fragment = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // View root = inflater.inflate(R.layout.fragment_main, container, false);
        root = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // Create a WebSocket factory and set 5000 milliseconds as a timeout
        // value for socket connection.
        factory = new WebSocketFactory().setConnectionTimeout(5000);
        setWebSocket(factory);
        createWebSocketYoji();  // Only for emulator debug purpose. Without samsung.jar. Remove after testing

        // button start
        final Button button1 = root.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("debug", "button1, Perform action on click");
                startSamsungSearch();
            }
        });
        final Button button2 = root.findViewById(R.id.button2);
        button2.setText("KEY_RIGHT");
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("debug", "button2, Perform action on click");
                //sendMessage(view);
                yojiWebSocket.sendMessage(view, "KEY_RIGHT");
            }
        });
        // button end

        return root;
    }

    public void startSamsungSearch() {
        Log.d("xx", "eee");

        // samsung start
        // Get an instance of Search
        final Search search = Service.search(getActivity().getApplicationContext());
        //final Search search = Service.search(MainActivity.this);
        // Start the discovery process
        search.start();


// Add a listener for the onServiceFound event
        search.setOnServiceFoundListener(
                new Search.OnServiceFoundListener() {
                    @Override
                    public void onFound(Service service) {
                        Log.d(LOGTAG, "Search onFound() " + service.toString());
                        // Add service to a visual list where your user can select.
                        // For display, we recommend that you show: service.getName()

                        final Button button1 = root.findViewById(R.id.button1);
                        button1.setText(service.getName());
                        //setContentView(button1);
                        search.stop(); //// You can also stop the discovery process after some amount of time. Preferably once the user has selected a service to work with
                        //createWebSocketYoji();
                    }
                }
        );

// Add a listener for the onServiceLost event
        search.setOnServiceLostListener(
                new Search.OnServiceLostListener() {
                    @Override
                    public void onLost(Service service) {
                        Log.d(LOGTAG, "Search onLost() " + service.toString());
                        // Remove this service from the display list
                    }
                }
        );

// Add a listener for the onStop event
        search.setOnStopListener(
                new Search.OnStopListener() {
                    @Override
                    public void onStop() {
                        Log.d(LOGTAG, "Search onStop() services found: " + search.getServices().size());
                    }
                }
        );
        // samsung end
    }

}