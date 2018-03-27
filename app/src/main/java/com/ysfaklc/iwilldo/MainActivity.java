package com.ysfaklc.iwilldo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener,View.OnClickListener{

    private ListView listeGorunumu;
    private EditText yapılcak;
    private ImageButton Ekle;
    private CustomAdapter adapter;
    private List<Item> ItemList;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Cursor dbdata;
        dbdata=myDB.getListContents();
        if(dbdata.getCount() == 0){ }
        else{
            while(dbdata.moveToNext()){
                ItemList.add(new Item(dbdata.getString(1)));
            }
        }
    }
    private void adepterset() {
        ItemList =new ArrayList<>();
        adapter= new CustomAdapter(this,R.layout.list_item,ItemList);
        listeGorunumu.setAdapter(adapter);
    }
    private void init() {
        yapılcak = (EditText)findViewById(R.id.yazı);
        listeGorunumu = (ListView)findViewById(R.id.listem);
        listeGorunumu.setOnItemClickListener(this);
        Ekle =(ImageButton) findViewById(R.id.ekle);
        Ekle.setOnClickListener(this);
        adepterset();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        long viewId = view.getId();

        if (viewId == R.id.del) {
            String silinen=ItemList.get(position).getVeri();
            myDB.notSil(silinen);
            ItemList.remove(position);
            adapter.notifyDataSetChanged();
        }
        else if (viewId == R.id.edit) {
            final String gelen=ItemList.get(position).getVeri().toString();
            final Dialog dialog=new Dialog(MainActivity.this);
            dialog.setTitle("!!WARNING!!");
            dialog.setContentView(R.layout.input_box);
            TextView txtMessage=(TextView)dialog.findViewById(R.id.txtmessage);
            txtMessage.setText("Update item");
            txtMessage.setTextColor(Color.parseColor("#ff2222"));
            final EditText editText=(EditText)dialog.findViewById(R.id.txtinput);
            editText.setText(gelen);
            Button bt=(Button)dialog.findViewById(R.id.btdone);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String updated=editText.getText().toString();
                    ItemList.get(position).setVeri(updated);
                    myDB.notGuncelle(gelen,updated);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else { }
    }
    @Override
    public void onClick(View view) {
        if(view==Ekle) {
            String mesaj = yapılcak.getText().toString().trim();
            if (!mesaj.isEmpty()) {
                Cursor data = myDB.getListContents();
                ItemList.add(new Item(mesaj));
                AddData(mesaj);
                adapter.notifyDataSetChanged();
                listeGorunumu.smoothScrollToPosition(ItemList.size() - 1);
                listeGorunumu.setAdapter(adapter);
                yapılcak.setText("");
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }
    public void AddData(String newEntry) {
        boolean insertData = myDB.addData(newEntry);
    }
}
