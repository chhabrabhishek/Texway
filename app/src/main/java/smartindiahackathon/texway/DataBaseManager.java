package smartindiahackathon.texway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by abhishek on 13/3/18.
 */

public class DataBaseManager extends SQLiteOpenHelper {

    public static final String TableName = "Recent_Searches";
    public static final String DataBaseName = "YeahDatabase.db";
    public static final int DataBaseVersion = 1;
    public static final String ID = "_id";
    public static final String Source = "From_location";
    public static final String Destination = "To_location";

    public DataBaseManager(Context context){
        super(context,DataBaseName,null,DataBaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("#FUCK", "YOU");
        db.execSQL("CREATE TABLE " + TableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, From_location TEXT , To_location TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TableName);
        onCreate(db);
    }

    public boolean insert_data(String Sid,String SName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Source,Sid);
        contentValues.put(Destination,SName);
        long result = db.insert(TableName,null,contentValues);
        Log.d("#FUCK", Long.toString(result));
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

}
