package com.example.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    private static MyDatabase minstance;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DB_SIMPLE_BANK";

    private static final String USERS_TABLE_NAME = "users";
    private static final String USERS_CLN_ID = "id";
    private static final String USERS_CLN_USERNAME = "username";
    private static final String USERS_CLN_EMAIL = "email";
    private static final String USERS_CLN_CURRENTBALANCE = "currentBalance";

    private static final String TRANSFER_TABLE_NAME = "transfer";
    private static final String TRANSFER_CLN_SENDER = "senderID";
    private static final String TRANSFER_CLN_RECEIVER = "receiverID";
    private static final String TRANSFER_CLN_AMOUNT = "amount";
    private static final String TRANSFER_CLN_DATETIME = "dateTime";

    private MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static MyDatabase getInstance(Context context) {
        if (minstance == null)
            synchronized (MyDatabase.class) {
                if (minstance == null)
                    minstance = new MyDatabase(context);
            }
        return minstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + USERS_TABLE_NAME
                + " ( "
                + USERS_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_CLN_USERNAME + " TEXT UNIQUE NOT NULL, "
                + USERS_CLN_EMAIL + " TEXT NOT NULL, "
                + USERS_CLN_CURRENTBALANCE + " REAL NOT NULL"
                + " )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TRANSFER_TABLE_NAME
                + " ( "
                + TRANSFER_CLN_AMOUNT + " REAL NOT NULL, "
                + TRANSFER_CLN_DATETIME + " TEXT NOT NULL, "
                + TRANSFER_CLN_SENDER + " INTEGER NOT NULL, "
                + TRANSFER_CLN_RECEIVER + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + TRANSFER_CLN_SENDER + ") REFERENCES " + USERS_TABLE_NAME + "(" + USERS_CLN_ID + "), "
                + "FOREIGN KEY (" + TRANSFER_CLN_RECEIVER + ") REFERENCES " + USERS_TABLE_NAME + "(" + USERS_CLN_ID + ")"
                + " )");

        intiData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertUser(User user, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(USERS_CLN_USERNAME, user.getUsername());
        values.put(USERS_CLN_EMAIL, user.getEmail());
        values.put(USERS_CLN_CURRENTBALANCE, user.getCurrentBalance());

        long result = database.insert(USERS_TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USERS_CLN_USERNAME, user.getUsername());
        values.put(USERS_CLN_EMAIL, user.getEmail());
        values.put(USERS_CLN_CURRENTBALANCE, user.getCurrentBalance());

        long result = getWritableDatabase().insert(USERS_TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean insertTransfer(Transfer transfer) {
        ContentValues values = new ContentValues();
        values.put(TRANSFER_CLN_SENDER, transfer.getSenderID());
        values.put(TRANSFER_CLN_RECEIVER, transfer.getReceiverID());
        values.put(TRANSFER_CLN_AMOUNT, transfer.getAmount());
        values.put(TRANSFER_CLN_DATETIME, transfer.getDateTime());

        long result = getWritableDatabase().insert(TRANSFER_TABLE_NAME, null, values);

        return result != -1;
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USERS_CLN_USERNAME, user.getUsername());
        values.put(USERS_CLN_EMAIL, user.getEmail());
        values.put(USERS_CLN_CURRENTBALANCE, user.getCurrentBalance());

        String[] args = {user.getId() + ""};
        long result = getWritableDatabase().update(USERS_TABLE_NAME, values, USERS_CLN_ID + " = ?", args);

        return result > 0;
    }

    public long getNumOfSending(User user) {
        String[] args = {user.getId() + ""};
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TRANSFER_TABLE_NAME, TRANSFER_CLN_SENDER + " = ?", args);
    }

    public long getNumOfReceiving(User user) {
        String[] args = {user.getId() + ""};
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TRANSFER_TABLE_NAME, TRANSFER_CLN_RECEIVER + " = ?", args);
    }

    public void intiData(SQLiteDatabase database) {
        User user = new User("Yash", "yash@gmail.com", 500);
        insertUser(user, database);
        user = new User("Chirag", "chirag@gmail.com", 500);
        insertUser(user, database);
        user = new User("Aakash", "aakash@gmail.com", 500);
        insertUser(user, database);
        user = new User("Amithesh", "amithesh@gmail.com", 500);
        insertUser(user, database);
        user = new User("Ravi", "ravi@gmail.com", 500);
        insertUser(user, database);
        user = new User("Ayush", "ayush@gmail.com", 500);
        insertUser(user, database);
        user = new User("Ranjan", "ranjan@gmail.com", 500);
        insertUser(user, database);
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + USERS_TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(USERS_CLN_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_EMAIL));
                float currentBalance = cursor.getFloat(cursor.getColumnIndexOrThrow(USERS_CLN_CURRENTBALANCE));
                User user = new User(id, username, email, currentBalance);
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return users;
    }

    public ArrayList<User> getAllUser(int removedId) {
        ArrayList<User> users = new ArrayList<>();

        String[] args = {removedId + ""};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_CLN_ID + " != ?", args);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(USERS_CLN_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_EMAIL));
                float currentBalance = cursor.getFloat(cursor.getColumnIndexOrThrow(USERS_CLN_CURRENTBALANCE));
                User user = new User(id, username, email, currentBalance);
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return users;
    }


    public User getUser(int id) {
        User user = new User();
        String[] args = {id + ""};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_CLN_ID + " = ?", args);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_EMAIL));
            float currentBalance = cursor.getFloat(cursor.getColumnIndexOrThrow(USERS_CLN_CURRENTBALANCE));

            user = new User(id, username, email, currentBalance);

            cursor.close();
        }

        return user;
    }

    public ArrayList<Transfer> getTransferAsSender(int senderId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        String[] args = {senderId + ""};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TRANSFER_TABLE_NAME + " WHERE " + TRANSFER_CLN_SENDER + " = ?", args);

        if (cursor.moveToFirst()) {
            do {
                int receiverId = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSFER_CLN_RECEIVER));
                float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(TRANSFER_CLN_AMOUNT));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(TRANSFER_CLN_DATETIME));
                Transfer transfer = new Transfer(senderId, receiverId, amount, dateTime);
                transfers.add(transfer);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return transfers;
    }

    public ArrayList<Transfer> getTransferAsReceiver(int receiverId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        String[] args = {receiverId + ""};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TRANSFER_TABLE_NAME + " WHERE " + TRANSFER_CLN_RECEIVER + " = ?", args);

        if (cursor.moveToFirst()) {
            do {
                int senderId = cursor.getInt(cursor.getColumnIndexOrThrow(TRANSFER_CLN_SENDER));
                float amount = cursor.getFloat(cursor.getColumnIndexOrThrow(TRANSFER_CLN_AMOUNT));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(TRANSFER_CLN_DATETIME));
                Transfer transfer = new Transfer(senderId, receiverId, amount, dateTime);
                transfers.add(transfer);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return transfers;
    }
    public ArrayList getMatchedUser(String username){
        ArrayList<User> users = new ArrayList<>();
        String[] args = {username + "%"};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE " + USERS_CLN_USERNAME + " Like ?",args);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(USERS_CLN_ID));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_EMAIL));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(USERS_CLN_USERNAME));
                float currentBalance = cursor.getFloat(cursor.getColumnIndexOrThrow(USERS_CLN_CURRENTBALANCE));

                User user = new User(id, name, email, currentBalance);
                users.add(user);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return users;
    }
}
