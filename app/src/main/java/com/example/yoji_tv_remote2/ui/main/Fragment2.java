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

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static Fragment2 newInstance(int index) {
        Fragment2 fragment = new Fragment2();
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
        View root = inflater.inflate(R.layout.fragment_2, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //yoji
        final Button button3 = root.findViewById(R.id.button3);
        button3.setText("ENTER");
        button3.setOnClickListener(new buttonClick());
        final Button button4 = root.findViewById(R.id.button4); //down
        button4.setOnClickListener(new buttonClick());
        button3.setText("DOWN");
        final Button button5 = root.findViewById(R.id.button5);//left
        button5.setOnClickListener(new buttonClick());
        button3.setText("LEFT");
        final Button button6 = root.findViewById(R.id.button6);//right
        button6.setOnClickListener(new buttonClick());
        button3.setText("RIGHT");
        final Button button7 = root.findViewById(R.id.button7); // up
        button7.setOnClickListener(new buttonClick());
        button3.setText("UP");
        final Button button8 = root.findViewById(R.id.button8); // home
        button8.setOnClickListener(new buttonClick());
        button3.setText("HOME");
        final Button button9 = root.findViewById(R.id.button9); // return
        button9.setOnClickListener(new buttonClick());
        button9.setText("RETURN");

        return root;
    }
}
class buttonClick implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button3) {
            Log.d("debug","button3, Perform action on click");
            yojiWebSocket.sendMessage(view, "KEY_ENTER");
        }
        else if(view.getId() == R.id.button4)    {
            yojiWebSocket.sendMessage(view, "KEY_DOWN"); //down
        }
        else if(view.getId() == R.id.button5)    {
            yojiWebSocket.sendMessage(view, "KEY_LEFT"); //left
        }
        else if(view.getId() == R.id.button6)    {
            yojiWebSocket.sendMessage(view, "KEY_RIGHT"); //right
        }
        else if(view.getId() == R.id.button7)    {
            yojiWebSocket.sendMessage(view, "KEY_UP"); //up
        }
        else if(view.getId() == R.id.button8)    {
            yojiWebSocket.sendMessage(view, "KEY_HOME"); // SMARTHUB
        }
        else if(view.getId() == R.id.button9)    {
            yojiWebSocket.sendMessage(view, "KEY_RETURN"); // return
        }
    }
}