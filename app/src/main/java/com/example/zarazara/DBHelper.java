package com.example.zarazara;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        // _id primary key
        // steps INTEGER 실시간 걸음 수
        // date DATE 날짜
        String sql = "CREATE TABLE IF NOT EXISTS stepStore ("
                + "_id integer primary key autoincrement,"
                + "steps INTEGER,"
             //   + "date DATETIME DEFAULT (datetime('now','localtime')));";
                + "date TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists stepStore;";

        db.execSQL(sql);
        onCreate(db);
    }

    public int checkTable() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT count(*) FROM stepStore;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        // 컬럼 값이 하나 이상 있다면
        if(cursor.getInt(0) > 0)
            return 0;
        else
            return -1;
    }

    public void updateDailyStep(String today, int step) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 날짜(당일)의 실시간 걸음 수 업데이트
        //db.execSQL("UPDATE stepStore SET steps =" + step + " WHERE _id = (SELECT max(_id) FROM stepStore);");
        db.execSQL("UPDATE stepStore SET steps =" + step + " WHERE date = '" + today +"';");
        db.close();
    }

    public void insert(String today, int step) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
      //  db.execSQL("INSERT INTO stepStore (_id, steps) VALUES (null, " + step + today +");");
        db.execSQL("INSERT INTO stepStore VALUES (null, " + step + ",'" + today +"');");
        db.close();
    }

    public int getStep(String today) {
        SQLiteDatabase db = getWritableDatabase();

       // Cursor cursor = db.rawQuery("SELECT SUM(steps) FROM stepStore WHERE date BETWEEN '" + today + " 00:00:00' AND '" + today + " 23:59:59';" , null);
        // _id>1 : 첫 실행 시 걸음 수가 올라가지 않는 부분 임시 체크
        Cursor cursor = db.rawQuery("SELECT SUM(steps) FROM stepStore WHERE _id > 1 AND date LIKE '" + today + "%';" , null);
        cursor.moveToFirst();
        if(cursor.getCount() <= 0)
            return 0;
        int step = cursor.getInt(0);

        return step;
    }

    public int getTotalStep() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(steps) FROM stepStore", null);
        cursor.moveToFirst();
        if(cursor.getCount() <= 0)
            return 0;
        int step = cursor.getInt(0);

        return step;
    }
}
