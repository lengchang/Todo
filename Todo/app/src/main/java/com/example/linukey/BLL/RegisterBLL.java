package com.example.linukey.BLL;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.example.linukey.DAL.DBHelper;
import com.example.linukey.DAL.UserContentProvider;
import com.example.linukey.Model.User;

import java.util.Map;

/**
 * Created by linukey on 11/30/16.
 */

public class RegisterBLL {
    public boolean saveInputToDB(User user, Context context){
        Map<String, String> users = new LoginBLL().getUsers(context);

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
}
