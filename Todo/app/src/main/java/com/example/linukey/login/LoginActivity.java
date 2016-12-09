package com.example.linukey.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linukey.data.model.User;
import com.example.linukey.register.RegisterActivity;
import com.example.linukey.todo.MainActivity;
import com.example.linukey.todo.R;

import java.util.Map;

/**
 * Created by linukey on 11/24/16.
 */

public class LoginActivity extends Activity {

    final int register_ResultCode = 1;
    UserInputModel userInputModel;

    class UserInputModel{
        EditText username;
        EditText password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUserInputModel();
    }

    public void initUserInputModel(){
        userInputModel = new UserInputModel();
        userInputModel.username = (EditText)findViewById(R.id.username);
        userInputModel.password = (EditText)findViewById(R.id.pwd);
    }

    public void onClick_register(View view){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivityForResult(register, register_ResultCode);
    }

    public void onClick_reSetPass(View view){
        Toast.makeText(this, "本功能等待开发!", Toast.LENGTH_LONG).show();
    }

    public void onClick_submit(View view){
        if(checkInput()) {
            Map<String, String> users = User.getUsers(this);
            if(users.containsKey(userInputModel.username.getText().toString())){
                if(users.get(userInputModel.username.getText().toString())
                        .equals(userInputModel.password.getText().toString())){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "密码错误!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "账号不存在!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean checkInput(){
        if(userInputModel.username.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入用户名!", Toast.LENGTH_LONG).show();
            return false;
        }else if(userInputModel.password.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
