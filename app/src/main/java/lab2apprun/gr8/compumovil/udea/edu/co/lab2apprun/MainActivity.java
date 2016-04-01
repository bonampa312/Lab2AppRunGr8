package lab2apprun.gr8.compumovil.udea.edu.co.lab2apprun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import co.edu.udea.lab2apprun.gr8.database.EventsDbManager;
import co.edu.udea.lab2apprun.gr8.database.ExtendedSimpleAdapter;
import co.edu.udea.lab2apprun.gr8.database.LoginDbManager;
import layout.ActivityMainFragment;
import layout.AddCareerFragment;
import layout.AppInfoFragment;
import layout.ListCareerFragment;
import layout.DevelopersInfoFragment;

public class MainActivity extends AppCompatActivity {





    private String[] opciones = null;
    private DrawerLayout drawerLayout = null;
    private ListView listView = null;

    private CharSequence tituloSec = null;
    private CharSequence tituloApp = null;

    private final static String[] FROM = {EventsDbManager.NAME, EventsDbManager.DISTANCE, EventsDbManager.PLACE, EventsDbManager.DATE}; //Nombres de columnas que mapean a la tabla
    private static final int[] TO = {R.id.nameCareerElement, R.id.distCareerElement, R.id.placeCareerElement, R.id.dateCareerElement};
    private ListView listViewElements = null;

    private ActionBarDrawerToggle drawerToggle = null;

    private ActivityMainFragment mainFragment = null;

    private EventsDbManager eventsDbManager = null;
    private LoginDbManager loginDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new ActivityMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContainer, mainFragment).commit();

        eventsDbManager = new EventsDbManager(MainActivity.this);
        loginDbManager = new LoginDbManager(MainActivity.this);

        //inicialiando mi el arreglo opciones
        opciones = new String[] {getResources().getString(R.string.career),
                getResources().getString(R.string.info),
                getResources().getString(R.string.developers),
                getResources().getString(R.string.closeSession),
                getResources().getString(R.string.out)
                };
        drawerLayout= (DrawerLayout) findViewById(R.id.mainContainer) ;
        listView = (ListView) findViewById(R.id.leftMenu);

        //Seteamos el adapter del Listview que hace referencia a menuIzq
        listView.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppInfoFragment fragment2 = null;
                DevelopersInfoFragment fragment3 = null;


                ListCareerFragment listCareerFragment = null;
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (position) {
                    case 0:
                        listCareerFragment = new ListCareerFragment();
                        ExtendedSimpleAdapter adapter = new ExtendedSimpleAdapter(MainActivity.this, EventsDbManager.getFullCareers() ,
                                R.id.listViewCareers, FROM, TO);
                        //R.layout.fragment_list_element
                        listViewElements.setAdapter(adapter);
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, listCareerFragment)
                                .commit();
                        break;
                    case 1:
                        fragment2 = new AppInfoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, fragment2)
                                .commit();
                        break;
                    case 2:
                        fragment3 = new DevelopersInfoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, fragment3)
                                .commit();
                        break;
                    case 3:
                        closeSession();
                        break;
                    case 4:
                        closeApp();
                        break;
                    default:
                        listCareerFragment = new ListCareerFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameContainer, listCareerFragment)
                                .commit();
                        break;

                }


                listView.setItemChecked(position, true);

                tituloSec = opciones[position];
                getSupportActionBar().setTitle(tituloSec);
                drawerLayout.closeDrawer(listView);


            }
        });

        //Obtenemos la referencia de los textos
        tituloSec=getTitle();
        tituloApp=getTitle();
        //Colocar las acciones de abrir y cerrar el navigation drawer, vamos a utilizar un DrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.drawable.ic_action_action_subject,
                R.string.open,

                R.string.closed){

        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void closeSession() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        loginDbManager.deleteData();
                        goLogin();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.closeSure)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();

    }
    private void goLogin(){
        Intent i = new Intent(this,LoginActivity.class);
        finish();
        startActivity(i);
        }


    private void closeApp(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        System.exit(0);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.closeSure)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();

    }


    public void onClickAdd(View view){

        FragmentManager transaction = getSupportFragmentManager();
        AddCareerFragment newFragment = new AddCareerFragment();
        transaction.beginTransaction()
                .replace(R.id.frameContainer, newFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onClickBack(View view){

        FragmentManager transaction = getSupportFragmentManager();
        ListCareerFragment newFragment1 = new ListCareerFragment();
        transaction.beginTransaction()
                .replace(R.id.frameContainer, newFragment1)
                .addToBackStack(null)
                .commit();
    }

    public void onClickAddCareer(View view){
        TextView careerName = (TextView) findViewById(R.id.nameCareerField);
        TextView distance = (TextView) findViewById(R.id.distanceCareerField);
        TextView careerPlace = (TextView) findViewById(R.id.placeCreerField);
        TextView careerDate = (TextView) findViewById(R.id.dateCareerField);

        if(careerName.getText().toString().equals("")
                ||distance.getText().toString().equals("")
                ||careerPlace.getText().toString().equals("")
                ||careerDate.getText().toString().equals("")){

            Toast.makeText(MainActivity.this, getResources().getString(R.string.mistake1), Toast.LENGTH_SHORT).show();
        }
        else if (eventsDbManager.eventExists(careerName.getText().toString())){
            Toast.makeText(MainActivity.this, getResources().getString(R.string.mistake7), Toast.LENGTH_SHORT).show();
        }
        else{
            eventsDbManager.insertEvent(
                    careerName.getText().toString(),
                    distance.getText().toString(),
                    careerPlace.getText().toString(),
                    careerDate.getText().toString());

            Toast.makeText(MainActivity.this, getResources().getString(R.string.mistake8), Toast.LENGTH_SHORT).show();
            careerName.setText("");
            distance.setText("");
            careerPlace.setText("");
            careerDate.setText("");
        }
    }


}
