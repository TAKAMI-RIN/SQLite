package com.example.jana.g10581097_hw4_4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String DATABASE_TABLE = "book";
    private SQLiteDatabase db;
    private MyDBHelper dbHelper;
    private EditText txtName, txtP_no, txtEmail;
    private TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 建立SQLiteOpenHelper物件
        dbHelper = new MyDBHelper(this);
        db = dbHelper.getWritableDatabase(); // 開啟資料庫
        // 取得TextView元件
        output = (TextView) findViewById(R.id.textView4);
        // 取得EditText元件
        txtName=(EditText) findViewById(R.id.editText1);
        txtP_no=(EditText) findViewById(R.id.editText2);
        txtEmail=(EditText) findViewById(R.id.editText3);
    }
    @Override
    protected void onStop() {
        super.onStop();
        db.close(); // 關閉資料庫
    }

   public void insert (View v){
        db.execSQL("INSERT INTO " + DATABASE_TABLE + " ("
                + "Name, Phome_No, Email) VALUES ('" + txtName.getText().toString() +
                "','" + txtP_no.getText().toString() +"','"+ txtEmail.getText().toString()+ "')");
        output.setText("新增記錄成功...");

    }
    public void delete(View v){
        db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE Name='" +
                txtName.getText().toString() + "'");
        output.setText("刪除記錄成功...");

    }

    public void query(View v){
        String[] colNames;
        String str = "";
        Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        colNames = c.getColumnNames();


        c.moveToFirst();  // 第1筆
        // 顯示欄位值
        for (int i = 0; i < c.getCount(); i++) {
            if(c.getString(1).equals(txtName.getText().toString())){
                str += "Name:  "+c.getString(1) + "\n";
                str += "Phone_Number:  "+c.getString(2) + "\n";
                str += "Email:  "+c.getString(3) + "\n";
            }
            c.moveToNext();  // 下一筆
        }
        output.setText(str.toString());
    }

    public void upDate(View v){
        db.execSQL("UPDATE " + DATABASE_TABLE + " SET Phome_No=" +
                txtP_no.getText().toString() +" WHERE Name='" +
                txtName.getText().toString() + "'");
        db.execSQL("UPDATE " + DATABASE_TABLE + " SET Email='" +
                txtEmail.getText().toString() +" 'WHERE Name='" +
                txtName.getText().toString() + "'");

        output.setText("更新記錄成功...");
    }

    public void list(View v){
        String[] colNames;
        String str = "";
        Cursor c = db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        colNames = c.getColumnNames();
        // 顯示欄位名稱
        for (int i = 0; i < colNames.length; i++)
            str += colNames[i] + "\t\t";
        str += "\n";
        c.moveToFirst();  // 第1筆
        // 顯示欄位值
        for (int i = 0; i < c.getCount(); i++) {
            str += c.getString(0) + "\t\t";
            str += c.getString(1) + "\t\t";
            str += c.getString(2) + "\t\t";
            str += c.getString(3) + "\n";
            c.moveToNext();  // 下一筆
        }
        output.setText(str.toString());
    }
}
