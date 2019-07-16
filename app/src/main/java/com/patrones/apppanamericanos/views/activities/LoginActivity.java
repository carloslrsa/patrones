package com.patrones.apppanamericanos.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.viewmodels.UserViewModel;
import com.patrones.apppanamericanos.utils.viewtemplates.MyActivity;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.observer.design.IObserver;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.InitUserCommand;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends MyActivity {

    @BindView(R.id.baselayout_login_cl)
    ConstraintLayout baseLayout;
    @BindView(R.id.loadinglayout_login_cl)
    ConstraintLayout loadingLayout;

    @BindView(R.id.dni_login_til)
    TextInputLayout dniParam;
    @BindView(R.id.password_login_til)
    TextInputLayout passwordParam;
    @BindView(R.id.login_login_btn)
    MaterialButton loginButton;

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        set(baseLayout,loadingLayout);

        userViewModel.getUser().addObserver(new IObserver<User>() {
            @Override
            public void update(Observable<User> observable, User data) {
                if(data == null){
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                    showBase();
                }
                else{
                    SessionManager.getInstance().executeCommand(new InitUserCommand(getApplicationContext(), data));
                    finish();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
    }

    private void tryLogin(){
        String dni = dniParam.getEditText().getText().toString();
        String password = passwordParam.getEditText().getText().toString();
        boolean canLogin = true;

        if(dni.length() < 8){
            dniParam.setError("Debe ingresar un DNI válido");
            dniParam.setErrorEnabled(true);
            canLogin = false;
        }
        if(password.length() < 3){
            passwordParam.setError("Debe ingresar una contraseña válida");
            passwordParam.setErrorEnabled(true);
            canLogin = false;
        }

        if (canLogin) {
            showLoading();
            userViewModel.login(dni,password,getApplicationContext());
        }
    }
}
