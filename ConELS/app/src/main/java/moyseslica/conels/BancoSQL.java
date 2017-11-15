package moyseslica.conels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSQL extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB.db";

    public String[] comandos;

    public BancoSQL(Context context, String[] comandos) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.comandos = comandos;

    }
    public void onCreate(SQLiteDatabase db) {

        for(int i = 0; i < this.comandos.length; i++){

            db.execSQL(this.comandos[i]);

        }

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Imagens");

        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onUpgrade(db, oldVersion, newVersion);

    }

}
