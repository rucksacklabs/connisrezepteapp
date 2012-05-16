package de.reneruck.connisRezepteApp.DB;

import de.reneruck.connisRezepteApp.Configurations;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String CREATE_REZEPTE_DB = "create table " + Configurations.table_Rezepte + " (" + Configurations.rezepte_Id + " integer primary key, "
			+ Configurations.rezepte_Name+ " text not null, "
			+ Configurations.rezepte_DocumentName + " text not null, "
			+ Configurations.rezepte_DocumentHash + " integer, "
			+ Configurations.rezepte_PathToDocument + " text , " 
			+ Configurations.rezepte_Zubereitung + " text, " 
			+ Configurations.rezepte_Zeit + " integer);"; 
	
	private static final String CREATE_ZUTATEN_DB = "create table " + Configurations.table_Zutaten+ " (" + Configurations.zutaten_Id + " integer primary key autoincrement, "
			+ Configurations.zutaten_value + " text not null);";
	
	private static final String CREATE_KATEGORIEN_DB = "create table " + Configurations.table_Kategorien+ " (" + Configurations.kategorien_Id + " integer primary key autoincrement, "
			+ Configurations.kategorien_value + " text not null);"; 

	private static final String CREATE_REZEPT_TO_ZUTATE_DB = "create table " + Configurations.table_Rezept_to_Zutat + " (" + Configurations.ID + " integer primary key autoincrement, " 
			+ Configurations.rezept_to_zutat_rezeptId+ " long not null, "
			+ Configurations.rezept_to_zutat_zutatId + " text not null);"; 
	
	private static final String CREATE_REZEPT_TO_KATEGORIE_DB = "create table " + Configurations.table_Rezept_to_Kategorie + " ("  + Configurations.ID + " integer primary key autoincrement, "
			+ Configurations.rezept_to_kategorie_rezeptId + " long not null, "
			+ Configurations.rezept_to_kategorie_kategorieId + " text not null);"; 
	
	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, Configurations.databaseName, factory,  Configurations.databaseVersion);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_REZEPTE_DB);
			db.execSQL(CREATE_ZUTATEN_DB);
			db.execSQL(CREATE_KATEGORIEN_DB);
			db.execSQL(CREATE_REZEPT_TO_ZUTATE_DB);
			db.execSQL(CREATE_REZEPT_TO_KATEGORIE_DB);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		/*
		 * TODO: Here should happen a data migration!!  
		 */
		db.execSQL("DROP TABLE IF EXISTS " +Configurations.table_Rezepte + "");
		db.execSQL("DROP TABLE IF EXISTS " +Configurations.table_Zutaten + "");
		db.execSQL("DROP TABLE IF EXISTS " +Configurations.table_Kategorien + "");
		db.execSQL("DROP TABLE IF EXISTS " +Configurations.table_Rezept_to_Kategorie+ "");
		db.execSQL("DROP TABLE IF EXISTS " +Configurations.table_Rezept_to_Zutat + "");
		onCreate(db);
	}
}