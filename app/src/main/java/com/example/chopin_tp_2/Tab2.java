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

public class Tab2 extends Fragment {

    private TextView CM_D = null;
    private TextView CM_M = null;
    private TextView CM_F = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("CM_D", "jeremy:5;");
        editor.putString("CM_M", "jeremy:4;");
        editor.putString("CM_F", "jeremy:3;");

        editor.apply();

    }

    private void updateTextField()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        CM_D.setText(customParser(prefs.getString("CM_D","Aucun records encore établi")));
        CM_M.setText(customParser(prefs.getString("CM_M","Aucun records encore établi")));
        CM_F.setText(customParser(prefs.getString("CM_F","Aucun records encore établi")));
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
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        CM_D = (TextView) view.findViewById(R.id.text_CM_D);
        CM_M = (TextView) view.findViewById(R.id.text_CM_M);
        CM_F = (TextView) view.findViewById(R.id.text_CM_F);

        updateTextField();

        return view;
    }
}
