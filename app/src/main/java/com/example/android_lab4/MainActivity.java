package com.example.android_lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> target;
    private SimpleCursorAdapter adapter;
    private MySQLite db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MySQLite(this);

        String[] values = new String[] {"Pies", "Kot", "Koń", "Gołąb", "Kruk", "Dzik", "Karp",
                "Osioł", "Chomik", "Mysz", "Jeż", "Karaluch"};

        this.target = new ArrayList<String>();
        this.target.addAll(Arrays.asList(values));

        this.adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                this.db.lista(), new String[] {"_id", "gatunek"}, new int[] {android.R.id.text1,android.R.id.text2},
                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);

        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(this.adapter);

        //komentarz
        //---------------------------------------------------------
        //TODO
        //Kolejny komentarz
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void nowyWpis(MenuItem mi)
    {
        Intent intencja = new Intent(this,
                DodajWpis.class);
        startActivityForResult(intencja, 1);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            assert extras != null;
            Animal nowy = (Animal)extras.getSerializable("nowy");
            assert nowy != null;
            this.db.dodaj(nowy);
            //target.add(nowy);
            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();
        }
    }

}
