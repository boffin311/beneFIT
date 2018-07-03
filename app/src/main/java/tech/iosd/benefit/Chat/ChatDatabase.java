package tech.iosd.benefit.Chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Message;

public class ChatDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chat.db";
    private static final String TABLE_NAME = "chat";
    private static final String COULMN_ID = "id";
    private static final String COULMN_AUTHOR = "author";
    private static final String COULMN_MESSAGE = "message";
    private static final String COULMN_TIME = "time";

    public ChatDatabase(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ TABLE_NAME +
                        "(id integer primary key, author integer, time integer, message text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS chat");
        onCreate(db);
    }

    public void insertMessage(int authorInt, String message, long time){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COULMN_AUTHOR,authorInt);
        contentValues.put(COULMN_MESSAGE,message);
        contentValues.put(COULMN_TIME,time);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Message> getMessages(){
        List<Message> allMessages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();
        while (!res.isAfterLast()){

        }

        res.close();
        return allMessages;
    }
}
