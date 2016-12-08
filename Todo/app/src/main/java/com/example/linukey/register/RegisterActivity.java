package com.example.linukey.register;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linukey.todo.R;
import com.example.linukey.util.TodoHelper;
import com.example.linukey.data.model.User;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linukey on 11/24/16.
 */

public class RegisterActivity extends Activity {

    UserInputModel userInputModel;

    class UserInputModel{
        EditText username;
        EditText email;
        EditText phonenumber;
        EditText password;
        EditText rePassword;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUserInputModel();
    }

    public void initUserInputModel(){
        userInputModel = new UserInputModel();
        userInputModel.username = (EditText)findViewById(R.id.username);
        userInputModel.password = (EditText)findViewById(R.id.pwd);
        userInputModel.rePassword = (EditText)findViewById(R.id.repwd);
        userInputModel.email = (EditText)findViewById(R.id.email);
        userInputModel.phonenumber = (EditText)findViewById(R.id.phone);
    }

    public void onClick_submit(View view){
        if(checkInput()){
            if(saveUserInfo()){
                Toast.makeText(this, "注册成功！", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(this, "注册失败！", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean checkInput(){
        if(userInputModel.username.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入用户名!", Toast.LENGTH_LONG).show();
            return false;
        }else if(userInputModel.email.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入邮箱!", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isEmail(userInputModel.email.getText().toString())){
            Toast.makeText(this, "请输入合法邮箱!", Toast.LENGTH_LONG).show();
            return false;
        }else if(userInputModel.phonenumber.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入电话号码!", Toast.LENGTH_LONG).show();
            return false;
        }else if(userInputModel.password.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入密码!", Toast.LENGTH_LONG).show();
            return false;
        }else if(userInputModel.rePassword.getText().toString().isEmpty()){
            Toast.makeText(this, "请输入验证密码!", Toast.LENGTH_LONG).show();
            return false;
        }else if(!userInputModel.password.getText().toString().equals(userInputModel.rePassword.getText().toString())){
            Toast.makeText(this, "两次输入的密码不一致!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean isEmail(String strEmail) {
        String strPattern =
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    public boolean saveUserInfo(){
        User user = new User();
        user.setUsername(userInputModel.username.getText().toString());
        user.setEmail(userInputModel.email.getText().toString());
        user.setPassword(userInputModel.password.getText().toString());
        user.setPhonenumber(userInputModel.phonenumber.getText().toString());
        user.setUsergroup(TodoHelper.UserGroup.get("normal"));
        user.setUserId(UUID.randomUUID().toString());
        if(User.saveUserInfo(user, this)){
            return true;
        }else{
            return false;
        }
    }
}
