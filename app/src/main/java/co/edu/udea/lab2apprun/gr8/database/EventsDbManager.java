package co.edu.udea.lab2apprun.gr8.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import lab2apprun.gr8.compumovil.udea.edu.co.lab2apprun.MainActivity;
import lab2apprun.gr8.compumovil.udea.edu.co.lab2apprun.R;

/**
 * Created by corgu1995 on 25/03/16.
 */
public class EventsDbManager {

    public static final String TABLE_NAME = "event";

    public static final String NAME = "name";
    public static final String DISTANCE = "distance";
    public static final String PLACE = "place";
    public static final String DATE = "date";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + NAME + " text primary key,"
            + DISTANCE + " text not null,"
            + PLACE + " text not null,"
            + DATE + " text not null);";

    private DatabaseHelper helper;
    private static SQLiteDatabase db;

    public EventsDbManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    private ContentValues generateContentValues(String name, String distance, String place, String date) {
        ContentValues valores = new ContentValues();
        valores.put(NAME, name);
        valores.put(DISTANCE, distance);
        valores.put(PLACE, place);
        valores.put(DATE, date);
        return valores;
    }

    public void insertEvent(String name, String distance, String place, String date) {
        if (!eventExists(name)) {
            db.insert(TABLE_NAME, null, generateContentValues(name, distance, place, date));
        }
    }

    public boolean eventExists(String name) {
        boolean flag=false;
        Cursor cursor = null;
        String eventName = "";
        try{
            cursor = db.rawQuery("SELECT name FROM event WHERE name=?", new String[] {name + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                eventName = cursor.getString(cursor.getColumnIndex("name"));
            }
            if (!eventName.equals("")){flag=true;}
            return flag;
        }finally {
            cursor.close();
        }
    }

    public String getEventName(String name) {
        Cursor cursor = null;
        String eventName = "";
        try{
            cursor = db.rawQuery("SELECT name FROM event WHERE name=?", new String[] {name + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                eventName = cursor.getString(cursor.getColumnIndex("name"));
            }
            return eventName;
        }finally {
            cursor.close();
        }
    }

    public String getEventDistance(String distance) {
        Cursor cursor = null;
        String eventDistance = "";
        try {
            cursor = db.rawQuery("SELECT distance FROM event WHERE distance=?", new String[]{distance + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                eventDistance = cursor.getString(cursor.getColumnIndex("distance"));
            }
            return eventDistance;
        } finally {
            cursor.close();
        }
    }
    public String getEventPlace(String place) {
        Cursor cursor = null;
        String eventPlace = "";
        try {
            cursor = db.rawQuery("SELECT place FROM event WHERE place=?", new String[]{place + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                eventPlace = cursor.getString(cursor.getColumnIndex("place"));
            }
            return eventPlace;
        } finally {
            cursor.close();
        }
    }
    public String getEventDate(String date) {
        Cursor cursor = null;
        String eventDate = "";
        try{
            cursor = db.rawQuery("SELECT date FROM event WHERE date=?", new String[] {date + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                eventDate = cursor.getString(cursor.getColumnIndex("date"));
            }
            return eventDate;
        }finally {
            cursor.close();
        }
    }

    public static Cursor getAllEvents() {
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT name FROM event", null);
        }finally {
            //cursor.close();
        }

        return cursor;
    }

    public static ArrayList getFullCareers() {
        ArrayList<String> name = new ArrayList(),dist = new ArrayList(),place = new ArrayList(),date = new ArrayList();
        boolean control = false;
        Cursor test = db.rawQuery("select * from " + TABLE_NAME + " order by " + NAME, null);
        if (test.moveToFirst()) {
            do {
                name.add(test.getString(1));
                dist.add(test.getString(2));
                place.add(test.getString(3));
                date.add(test.getString(4));

            } while (test.moveToNext());
            control = true;
        } else {

        }
        db.close();
        if (control) {
            ArrayList aList = new ArrayList();
            for (int i = 0; i < name.size(); i++) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                hm.put("name", "Nombre: " + name.get(i));
                hm.put("dist", "Distancia: " + dist.get(i));
                hm.put("place", "Lugar : " + place.get(i));
                hm.put("date", "Fecha : " + date.get(i));
                aList.add(hm);
            }

            return aList;

        }
        return null;
    }

}
