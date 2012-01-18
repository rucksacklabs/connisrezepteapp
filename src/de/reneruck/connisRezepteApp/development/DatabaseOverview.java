package de.reneruck.connisRezepteApp.development;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import de.reneruck.connisRezepteApp.AppContext;
import de.reneruck.connisRezepteApp.Configurations;
import de.reneruck.connisRezepteApp.DBManager;
import de.reneruck.connisRezepteApp.Main;
import de.reneruck.connisRezepteApp.R;

public class DatabaseOverview extends Activity implements TabListener {

	private DBManager dbManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.debug_database_overview);
		setUpActionbar();
		this.dbManager = ((AppContext) getApplicationContext()).getDBManager();
	}
	
	private void setUpActionbar() {

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = actionBar.newTab().setText("Rezepte").setTabListener(this);
		actionBar.addTab(tab);
		Tab tab2 = actionBar.newTab().setText("Andere Tabellen").setTabListener(this);
		actionBar.addTab(tab2);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.debug_actionbar_menu, menu);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, Main.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
//		((ViewGroup)findViewById(R.id.debug_scroll)).removeAllViews();
		
		if (tab.getText().toString().equals("Andere Tabellen")) {
			setContentView(R.layout.debug_database_others);
			showSimple(Configurations.table_Kategorien, R.id.debug_table_kategorien);
			showSimple(Configurations.table_Zutaten, R.id.debug_table_zutaten);
			show3parted(Configurations.table_Rezept_to_Kategorie, Configurations.rezept_to_kategorie_kategorieId, R.id.debug_table_rezept_to_kategorien);
			show3parted(Configurations.table_Rezept_to_Zutat, Configurations.rezept_to_zutat_zutatId, R.id.debug_table_rezept_to_zutat);
			
		}else if(tab.getText().toString().equals("Rezepte")) {
			showRezepte();
		}
	}


	private void show3parted(String table, String valueId, int parentView) {

		if(this.dbManager == null){
			this.dbManager = ((AppContext) getApplicationContext()).getDBManager();
		}
		SQLiteDatabase db = this.dbManager.getReadableDatabase();
		Cursor query = db.query(table, new String[] { "*" }, null, null, null, null, Configurations.ID);
		
		if (query.getCount() > 0) {
			
			int index_id = query.getColumnIndex(Configurations.ID);
			int index_id2 = query.getColumnIndex("rezeptId");
			int index_id3 = query.getColumnIndex(valueId);
			
			for (query.moveToFirst(); !query.isAfterLast(); query.moveToNext()) {
				
				int id = query.getInt(index_id);
				int id2 = query.getInt(index_id2);
				int id3 = query.getInt(index_id3);
				
				View inflated_row = getLayoutInflater().inflate(R.layout.debug_db_row_three_parts, (ViewGroup) findViewById(parentView), false);
				((TextView) inflated_row.findViewById(R.id.field_id)).setText(String.valueOf(id));
				((TextView) inflated_row.findViewById(R.id.field_id2)).setText(String.valueOf(id2));
				((TextView) inflated_row.findViewById(R.id.field_id3)).setText(String.valueOf(id3));
				((TableLayout)findViewById(parentView)).addView(inflated_row);
			}
		}
		db.close();		
		
	}

	private void showSimple(String table, int parentView) {
		
		if(this.dbManager == null){
			this.dbManager = ((AppContext) getApplicationContext()).getDBManager();
		}		SQLiteDatabase db = this.dbManager.getReadableDatabase();
		Cursor query = db.query(table, new String[] { "*" }, null, null, null, null, Configurations.ID);
		
		if (query.getCount() > 0) {
			
			int index_id = query.getColumnIndex(Configurations.ID);
			int index_name = query.getColumnIndex("value");
			
			for (query.moveToFirst(); !query.isAfterLast(); query.moveToNext()) {
				
				int id = query.getInt(index_id);
				String name = query.getString(index_name);
				
				View inflated_row = getLayoutInflater().inflate(R.layout.debug_db_row_simple, (ViewGroup) findViewById(parentView), false);
				((TextView) inflated_row.findViewById(R.id.field_id)).setText(String.valueOf(id));
				((TextView) inflated_row.findViewById(R.id.field_value)).setText(name);
				((TableLayout)findViewById(parentView)).addView(inflated_row);
			}
		}
		db.close();		
	}

	private void showRezepte() {
		
		setContentView(R.layout.debug_database_overview);
		if(this.dbManager == null){
			this.dbManager = ((AppContext) getApplicationContext()).getDBManager();
		}
		SQLiteDatabase db = this.dbManager.getReadableDatabase();
		Cursor query = db.query(Configurations.table_Rezepte, new String[] { "*" }, null, null, null, null, Configurations.rezepte_Id);

		if (query.getCount() > 0) {

			int index_id = query.getColumnIndex(Configurations.rezepte_Id);
			int index_name = query.getColumnIndex(Configurations.rezepte_Name);
			int index_docName = query.getColumnIndex(Configurations.rezepte_DocumentName);
			int index_docHash = query.getColumnIndex(Configurations.rezepte_DocumentHash);
			int index_documentPath = query.getColumnIndex(Configurations.rezepte_PathToDocument);
			int index_zubereitung = query.getColumnIndex(Configurations.rezepte_Zubereitung);
			int index_zeit = query.getColumnIndex(Configurations.rezepte_Zeit);
			for (query.moveToFirst(); !query.isAfterLast(); query.moveToNext()) {

				int id = query.getInt(index_id);
				String name = query.getString(index_name);
				String docName = query.getString(index_docName);
				int hash = query.getInt(index_docHash);
				String path = query.getString(index_documentPath);
				String zubereitung = query.getString(index_zubereitung);
				int zeit = query.getInt(index_zeit);

				View inflated_row = getLayoutInflater().inflate(R.layout.debug_db_table_row, (ViewGroup) findViewById(R.id.table_rezepte), false);
				((TextView) inflated_row.findViewById(R.id.field_id)).setText(String.valueOf(id));
				((TextView) inflated_row.findViewById(R.id.field_rezept_name)).setText(name);
				((TextView) inflated_row.findViewById(R.id.field_document_name)).setText(docName);
				((TextView) inflated_row.findViewById(R.id.field_hash)).setText(String.valueOf(hash));
				((TextView) inflated_row.findViewById(R.id.field_document_path)).setText(path);
				((TextView) inflated_row.findViewById(R.id.field_zubereitung)).setText(zubereitung);
				((TextView) inflated_row.findViewById(R.id.field_zeit)).setText(String.valueOf(zeit));
				((TableLayout)findViewById(R.id.table_rezepte)).addView(inflated_row);
			}
		}
		db.close();		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}