package hu.neilus;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.warting.bubbles.OneComment;

public class ConversationDb {
	Context context;
	
	private ConversationDbHelper dbHelper;
	private SQLiteDatabase db;
	final private String DATABASE_NAME = "unatkozom";
	private int DATABASE_VERSION = 1;
	final private String CONVERSATIONS="conversations";
	
	public ConversationDb(Context context){
		this.context = context;
		dbHelper = new ConversationDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void open(){
		db = dbHelper.getWritableDatabase();
		dbHelper.onCreate(db);
	}
	
	/**
	 * Adds a new message to the database. You need to specify the conversation's
	 * ID to which the new message belongs to
	 * @param conversationId as int
	 * @param msg as String
	 */
	public void insertMsg(int conversationId, String msg){
		ContentValues cv = new ContentValues();
		cv.put("conversationid", conversationId);
		cv.put("msg", msg);
		Log.v("Conversation database insertion", conversationId + ": " + msg);
		db.insert(CONVERSATIONS, null, cv);
	}
	
	/**
	 * returns the list of all the messages saved in the database
	 * @return ArrayList as String
	 */
	/*
	public ArrayList<String>selectAllMsg(){
		ArrayList<String> allMsg = new ArrayList<String>();
		Cursor cursor = db.query(CONVERSATIONS, null, null, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			do{
				allMsg.add(cursor.getString(2));
			}while(cursor.moveToNext());
		}
		return allMsg;
	}*/
	public ArrayList<OneComment> selectAllMsg(){
		ArrayList<OneComment> allMsg = new ArrayList<OneComment>();
		Cursor cursor = db.query(CONVERSATIONS, null, null, null, null, null, null, null);
		if(cursor != null && cursor.moveToFirst()){
			do{
				allMsg.add(new OneComment( (cursor.getInt(1) ==0), cursor.getString(2) ));
			}while(cursor.moveToNext());
		}
		return allMsg;
	}
	
	public class ConversationDbHelper extends SQLiteOpenHelper{

		public ConversationDbHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			final String createDb = " CREATE TABLE IF NOT EXISTS "+ CONVERSATIONS 
					+"(id integer primary key autoincrement, conversationid "
					+ "integer, msg text);";
			db.execSQL(createDb);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String query = "DROP TABLE IF EXISTS " + CONVERSATIONS + ";";
			db.execSQL(query);
		}
		
	}
}
