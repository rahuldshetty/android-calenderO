package com.cldnr.rahul.calendero;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Events extends Fragment {

    SQLiteDatabase myDatabase;

    ArrayList<Data> arrayList=new ArrayList<Data>();

    SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yy");

    EventCursorAdapter adapter;

    ListView listView;


    public Events() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_events, container, false);

        listView=view.findViewById(R.id.eventList);

        myDatabase = this.getActivity().openOrCreateDatabase("dbstorage",MODE_PRIVATE,null);

        Date dateObj = Calendar.getInstance().getTime();
        String curDate = curFormater.format(dateObj);


        adapter=new EventCursorAdapter(this.getContext(),arrayList);
        listView.setAdapter(adapter);

        loadData(curDate);

        return view;

    }


    public class EventCursorAdapter extends ArrayAdapter<Data> {


        public EventCursorAdapter(@NonNull Context context, ArrayList<Data> resource) {
            super(context, 0 ,resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final Data data = getItem(position);

            if(convertView==null)
            {
                convertView=LayoutInflater.from(getContext()).inflate(R.layout.listitemview,parent,false);
            }

            TextView title,desc,time;
            ImageView img;
            title=convertView.findViewById(R.id.list_title);
            desc=convertView.findViewById(R.id.list_desc);
            time=convertView.findViewById(R.id.list_time);
            img = convertView.findViewById(R.id.list_delete);

            title.setText(data.getTitle());
            desc.setText(data.getDesc());
            time.setText(data.getTime());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDatabase.execSQL("DELETE FROM  programTable WHERE title="+"'"+ data.getTitle() +"' and description=" + "'" + data.getDesc() + "' and time="+"'"+data.getTime()+"';"    );
                    loadData(data.getDate());
                }
            });

            return convertView;
        }
    }


    public void loadData(String dates){
        String q="Select * from programTable where date='"+dates+"';" ;
        Cursor resultSet = myDatabase.rawQuery(q,null);
        adapter.clear();

        try {
            while (resultSet.moveToNext()) {
                adapter.add(new Data(resultSet.getString(0),resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
            }
        } catch(Exception e) {
            resultSet.close();
        }


    }


}
