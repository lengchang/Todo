package com.example.linukey.data.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.linukey.data.source.local.DBHelper;
import com.example.linukey.data.source.local.UserContentProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linukey on 11/29/16.
 */

public class User {
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private String usergroup;
    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(String usergroup) {
        this.usergroup = usergroup;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static boolean saveUserInfo(User user, Context context){
        Map<String, String> users = getUsers(context);

        if(users.containsKey(user.getUsername())){
            Toast.makeText(context, "该用户名已存在!", Toast.LENGTH_LONG).show();
            return false;
        }

        ContentValues newValues = new ContentValues();
        newValues.put(UserContentProvider.key_username, user.getUsername());
        newValues.put(UserContentProvider.key_email, user.getEmail());
        newValues.put(UserContentProvider.key_password, user.getPassword());
        newValues.put(UserContentProvider.key_phonenumber, user.getPhonenumber());
        newValues.put(UserContentProvider.key_usergroup, user.getUsergroup());
        newValues.put(UserContentProvider.key_userId, user.getUserId());

        ContentResolver cr = context.getContentResolver();
        Uri myRowUri = cr.insert(DBHelper.ContentUri_user, newValues);

        if(myRowUri != null)
            return true;

        return false;
    }

    public static Map<String, String> getUsers(Context context){
        ContentResolver cr = context.getContentResolver();

        String[] result_columns = new String[]{
                UserContentProvider.key_username,
                UserContentProvider.key_password
        };

        String where = null;

        String whereArgs[] = null;
        String order = null;

        Cursor resultCursor = cr.query(DBHelper.ContentUri_user,
                result_columns, where, whereArgs, order);

        Map<String, String> result = null;
        if(resultCursor != null){
            result = new HashMap<String, String>();
            while(resultCursor.moveToNext()){
                result.put(resultCursor.getString(0), resultCursor.getString(1));
            }

            resultCursor.close();

            return result;
        }
        Toast.makeText(context, "null", Toast.LENGTH_LONG).show();
        return result;
    }
}
