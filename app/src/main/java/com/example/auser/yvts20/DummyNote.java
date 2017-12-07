package com.example.auser.yvts20;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class DummyNote extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    private DB mDbHelper;
    private long rowId;
    private String editString1;
    private int mNoteNumber = 1;
    protected static final int MENU_INSERT = Menu.FIRST;
    protected static final int MENU_DELETE = Menu.FIRST + 1;
    protected static final int MENU_UPDATE = Menu.FIRST + 2;
    protected static final int MENU_INSERT_WITH_SPECIFIC_ID = Menu.FIRST + 3;
    Cursor c;
    Button button1, button2, button3;
    EditText editText1, editText2, editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        // listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(this);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);

        setAdapter();
    }

    private void setAdapter() {
        mDbHelper = new DB(this).open();
        fillData();
    }

    void fillData() {
        c = mDbHelper.getAll();
        startManagingCursor(c);
        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(
                this,
                R.layout.dbitem_layout,
                c,
                new String[]{ "NAME", "PHONE", "EMAIL"},
//

                new int[]{R.id.textView1, R.id.textView2, R.id.textView3},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(scAdapter);



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rowId = id;
        Cursor c = mDbHelper.get(rowId);
        c.moveToFirst();

        editText1.setText(c.getString(1));
        editText2.setText(c.getString(2));
        editText3.setText(c.getString(3));

    }



    public void onclick1(View v) {
        mDbHelper.create(editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString());
        setAdapter();
    }

    public void onclick2(View v) {
        mDbHelper.update(rowId, editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString());
        setAdapter();
    }

    public void onclick3(View v) {
        mDbHelper.delete(rowId);
        setAdapter();
    }
}
