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
    public static final String Source = "from_location";
    public static final String Destination = "to_location";
    public static final String SourceCoordinate = "cord_source";
    public static final String DestinationCoordinate = "cord_destination";
    public static final String TimeStamp = "timestamp";


    public DataBaseManager(Context context){
        super(context,DataBaseName,null,DataBaseVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("#FUCK", "YOU");
        db.execSQL("CREATE TABLE " + TableName + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, from_location TEXT , to_location TEXT , cord_source TEXT , cord_destination TEXT , timestamp TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TableName);
        onCreate(db);
    }

    public boolean insert_data(String From,String To,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Source,From);
        contentValues.put(Destination,To);
        contentValues.put(SourceCoordinate,"76.99,54.67");
        contentValues.put(DestinationCoordinate,"76.99,54.67");
        contentValues.put(TimeStamp, date);
        long result = db.insert(TableName,null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAlldata(){
        String query = "SELECT * FROM "+ TableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public int getSize() {
        String query = "SELECT COUNT(*) FROM "+TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor response = db.rawQuery(query, null);
        response.moveToNext();
        return response.getInt(0);
    }

}
