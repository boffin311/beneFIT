package tech.iosd.benefit.Chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tech.iosd.benefit.Author;
import tech.iosd.benefit.DashboardFragments.Chat;
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

    public List<Message> getMessages(){//doesn't set author , use if else in chat activity and reuse author objects
        List<Message> allMessages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);

        Author nutritionist = new Author("49", "Nutritionist", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/LetterN.svg/1200px-LetterN.svg.png");
        Author coach = new Author("50", "Coach", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Letter_c.svg/1200px-Letter_c.svg.png");
        Author me = new Author("51", "Me");

        res.moveToFirst();
        while (!res.isAfterLast()){
            Author author;
            if(res.getInt(res.getColumnIndex(COULMN_AUTHOR))==Chat.ID_ME){
                author = me;
            }else if(res.getInt(res.getColumnIndex(COULMN_AUTHOR))==Chat.ID_COACH){
                author = coach;
            }else {//nutritionist
                author = nutritionist;
            }
                Message message = new Message(
                    String.valueOf(res.getInt(res.getColumnIndex(COULMN_AUTHOR))),
                    res.getString(res.getColumnIndex(COULMN_MESSAGE)),
                    author,
                    new Date(res.getLong(res.getColumnIndex(COULMN_TIME)))
                    );
            allMessages.add(message);

            res.moveToNext();
        }

        res.close();
        return allMessages;
    }

    public void deleteAllMessages(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
    }

}
