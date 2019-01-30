package com.example.chopin_tp_2;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Carte extends Fragment {

    private ImageView imageView;

    private int verso;
    private int recto;
    private int idCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verso = R.drawable.verso;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carte, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageViewCarte);
        imageView.setImageResource(verso);

        return view;
    }

    public void setIdCard(int id)
    {
        this.idCard = id;
    }

    public int getIdCard()
    {
        return this.idCard;
    }

    public void setRecto(int recto)
    {
        this.recto = recto;
    }

    public void swapToRecto()
    {
        imageView.setImageResource(recto);
    }

    public void swapToVerso()
    {
        imageView.setImageResource(verso);
    }
}
