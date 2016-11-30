package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.UserContentProvider;
import com.example.linukey.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linukey on 11/30/16.
 */

public class LoginBLL {

    public Map<String, String> getUsers(Context context){
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
