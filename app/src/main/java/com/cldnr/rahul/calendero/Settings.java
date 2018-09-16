package com.cldnr.rahul.calendero;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    Button clearBtn;

    SQLiteDatabase myDatabase;


    View view;

    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        clearBtn=view.findViewById(R.id.settingsClear);

        myDatabase = this.getActivity().openOrCreateDatabase("dbstorage",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS programTable(title VARCHAR(15),description VARCHAR(50),time varchar(5),date varchar(10));");

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDatabase.execSQL("Delete from programTable;");
                Toast.makeText(view.getContext(),"All contents cleared...",Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

}
