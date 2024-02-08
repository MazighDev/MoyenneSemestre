package com.l3.moyennecalculateur.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.l3.moyennecalculateur.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MarksViewAdaptater extends ArrayAdapter<MarksView> {
    ArrayList<MarksView> arrayList;

    public MarksViewAdaptater(Context context, ArrayList<MarksView> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null)
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);

        MarksView currentMarkView = getItem(position);

        TextView intitule = currentItemView.findViewById(R.id.item_intitule);
        intitule.setText(checkName(currentMarkView.getIntitule()));

        TextView coefficient = currentItemView.findViewById(R.id.item_coefficient);
        coefficient.setText(String.valueOf(currentMarkView.getCoefficient()));

       TextView moyenne = currentItemView.findViewById(R.id.item_moyenne);
        moyenne.setText(formatDouble(currentMarkView.getMoyenne()));




        return currentItemView;
    }

    private String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(number);
    }

    private String checkName(String name) {
        if(name.contains(" ")){
            String[] splitString = name.split(" ");
            StringBuilder nameBuilder = new StringBuilder();
            for(String s : splitString) {
                nameBuilder.append(s.charAt(0));
            }
            name = nameBuilder.toString();
        }

        if(name.length() > 9) {
            StringBuilder nameBuilder = new StringBuilder();
            for(int i = 0; i < 9; i++) {
                nameBuilder.append(name.charAt(i));
            }
            name = nameBuilder.toString();
        }
        return name.toUpperCase();
    }
}