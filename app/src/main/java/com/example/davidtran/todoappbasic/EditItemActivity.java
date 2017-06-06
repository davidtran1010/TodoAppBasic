package com.example.davidtran.todoappbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.id.message;

/**
 * Created by davidtran on 6/6/17.
 */

public class EditItemActivity extends AppCompatActivity {
    EditText editText;
    Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlistview);

        editText = (EditText) findViewById(R.id.editText);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        String itemData = getIntent().getStringExtra("itemdata");
        final int posItem = getIntent().getIntExtra("posItem",-1);
        editText.setText(itemData);
        editText.setSelection(editText.length());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedText = editText.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("editedData",editedText);
                intent.putExtra("posItem",posItem);
                setResult(2,intent);
                finish();
            }
        });


    }
    @Override
    public void onBackPressed() {
    }
}
