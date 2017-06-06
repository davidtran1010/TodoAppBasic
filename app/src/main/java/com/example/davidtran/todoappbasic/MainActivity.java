package com.example.davidtran.todoappbasic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    Button addButton;
    EditText editText;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addBtn);
        editText = (EditText) findViewById(R.id.inputText);
        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<String>();

        readItems();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,R.layout.activity_listview,items);

        listView.setAdapter(arrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text ="";
                text = editText.getText().toString();
                items.add(text);
                listView.invalidateViews();
                editText.setText("");
                writeItems();

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        setupListViewListener();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            String string = data.getStringExtra("editedData");
            int pos = data.getIntExtra("posItem",-1);
            items.set(pos,string);
            listView.invalidateViews();

            writeItems();
        }
    }


    private void setupListViewListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("itemdata", items.get(position));
                i.putExtra("posItem",position);
                startActivityForResult(i, 2);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                listView.invalidateViews();
                return true;
            }
        });
    }
    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }
        catch (IOException e)  {
            items = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);

        }catch(IOException e){
            e.printStackTrace();

        }
    }

}
