package com.example.chopin_tp_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Tab1 extends Fragment {

    private TextView N_D = null;
    private TextView N_M = null;
    private TextView N_F = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("N_D", "aucun");
        editor.putString("N_M", "jeremy:5;jeremy:6;jeremy:7;");
        editor.putString("N_F", "jeremy:5;jeremy:6;jeremy:7;");

        editor.apply();

    }

    private void updateTextField()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        N_D.setText(customParser(prefs.getString("N_D","Aucun records encore établi")));
        N_M.setText(customParser(prefs.getString("N_M","Aucun records encore établi")));
        N_F.setText(customParser(prefs.getString("N_F","Aucun records encore établi")));
    }

    private String customParser(String string)
    {

        if(string.contains(";")) {

            String[] strings = string.split(";");
            String textValue = "";
            int index = 1;

            for (String str : strings) {
                textValue += "Joueur n°" + String.valueOf(index) + " : " + str.split(":")[0] + " en " + str.split(":")[1] + "secondes\n";
                index++;
            }

            return textValue;
        }

        return string;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        N_D = (TextView) view.findViewById(R.id.text_N_D);
        N_M = (TextView) view.findViewById(R.id.text_N_M);
        N_F = (TextView) view.findViewById(R.id.text_N_F);

        updateTextField();

        return view;
    }
}
