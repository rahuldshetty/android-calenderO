package com.cldnr.rahul.calendero;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Home extends Fragment {

    SQLiteDatabase myDatabase;

    View view;

    ListView listView;

    CalendarView calenderView;

    DatePicker dpk;

    ArrayList<Data> arrayList=new ArrayList<Data>();

    EventCursorAdapter adapter ;

    FloatingActionButton fab;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home, container, false);
        fab=view.findViewById(R.id.addbtn);


        adapter = new EventCursorAdapter(this.getContext(), arrayList);;

        calenderView = view.findViewById(R.id.calendarView);

        myDatabase = this.getActivity().openOrCreateDatabase("dbstorage",MODE_PRIVATE,null);

        listView=view.findViewById(R.id.listview);



        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                adapter.clear();
                m=m+1;
                y=y%100;
                String month=""+m,day=""+d,year=""+y;
                if (m<10)
                    month="0"+m;

                if(d<10)
                    day="0"+d;

                if(y<10)
                    year="0"+y;



                String dates=month+"/"+day+"/"+year;


                String q="Select * from programTable where date='"+dates+"';" ;
                Cursor resultSet = myDatabase.rawQuery(q,null);


                try {
                    while (resultSet.moveToNext()) {
                        adapter.add(new Data(resultSet.getString(0),resultSet.getString(1),resultSet.getString(2)));
                    }
                } catch(Exception e) {
                    System.out.println("Failed."+e);
                    resultSet.close();
                }



            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(view.getContext(),Main2Activity.class);
                System.out.println(calenderView.getDate());

                addIntent.putExtra("eventDate",calenderView.getDate());

                startActivity(addIntent);
            }
        });

        listView.setAdapter(adapter);
        return view;
    }


    public class EventCursorAdapter extends ArrayAdapter<Data>{


        public EventCursorAdapter(@NonNull Context context, ArrayList<Data> resource) {
            super(context, 0 ,resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Data data = getItem(position);

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


            return convertView;
        }
    }



}
