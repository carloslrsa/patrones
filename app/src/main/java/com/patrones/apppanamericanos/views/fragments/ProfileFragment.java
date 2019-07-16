package com.patrones.apppanamericanos.views.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.patrones.apppanamericanos.models.session.commands.SaveNamesCommand;
import com.patrones.apppanamericanos.viewmodels.ProfileViewModel;
import com.patrones.apppanamericanos.utils.adapters.AssistanceAdapter;
import com.patrones.apppanamericanos.utils.viewtemplates.MyFragment;
import com.patrones.apppanamericanos.models.entities.Assistance;
import com.patrones.apppanamericanos.models.entities.Profile;
import com.patrones.apppanamericanos.models.entities.User;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.observer.design.IObserver;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.EndUserCommand;
import com.patrones.apppanamericanos.models.session.commands.LoadUserCommand;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends MyFragment {

    @BindView(R.id.loadinglayout_profile_cl)
    ConstraintLayout loadingLayout;
    //Unlogged views
    @BindView(R.id.alternativelayout_profile_cl)
    ConstraintLayout alternativeLayout;
    @BindView(R.id.login_profile_unlogged_btn)
    MaterialButton loginButton;
    @BindView(R.id.signin_profile_unlogged_btn)
    MaterialButton signinButton;
    //Logged views
    @BindView(R.id.baselayout_profile_cl)
    ConstraintLayout baseLayout;
    @BindView(R.id.logout_profile_logged_btn)
    MaterialButton logoutButton;
    @BindView(R.id.changepassword_profile_logged_btn)
    MaterialButton changePasswordButton;
    @BindView(R.id.image_profile_logged_iv)
    ImageView imageProfile;
    @BindView(R.id.name_profile_logged_tv)
    TextView nameProfile;
    @BindView(R.id.type_profile_logged_tv)
    TextView typeProfile;
    @BindView(R.id.eventlist_profile_logged_rv)
    RecyclerView eventListProfile;

    private ProfileViewModel mViewModel;

    boolean loadedFlag = false;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this,view);

        set(baseLayout, loadingLayout, alternativeLayout);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.login_dest);
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.signin_dest);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance().executeCommand(new EndUserCommand(getContext()));
                showAlternative(0, new ILayoutManagerSetup() {
                    @Override
                    public void setUp() {

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mViewModel.getProfile().addObserver(new IObserver<Profile>() {
            @Override
            public void update(Observable<Profile> observable, Profile data) {
                if(data != null){
                    refreshProfile(data);
                    if(loadedFlag){
                        showBase();
                    }else{
                        loadedFlag = true;
                    }
                }else{
                    showAlternative(0, new ILayoutManagerSetup() {
                        @Override
                        public void setUp() {

                        }
                    });
                }
            }
        });
        mViewModel.getAssistances().addObserver(new IObserver<List<Assistance>>() {
            @Override
            public void update(Observable<List<Assistance>> observable, List<Assistance> data) {
                if(data != null){
                    refreshAssistances(data);
                    if(loadedFlag){
                        showBase();
                    }else{
                        loadedFlag = true;
                    }
                }else{
                    showAlternative(0, new ILayoutManagerSetup() {
                        @Override
                        public void setUp() {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        User user = SessionManager.getInstance().executeCommand(new LoadUserCommand(getContext()));
        if (user != null) {
            showLoading();
            mViewModel.loadProfile(user,getContext());
            mViewModel.loadAssistances(user,getContext());
            loadedFlag = false;
        }else{
            showAlternative(0, new ILayoutManagerSetup() {
                @Override
                public void setUp() {

                }
            });
        }
    }

    private void refreshProfile(Profile profile){
        User user = SessionManager.getInstance().executeCommand(new LoadUserCommand(getContext()));;

        nameProfile.setText(profile.getFirstname() + " " + profile.getLastname());
        typeProfile.setText(user.getType()==0?"ASISTENTE":"PARTICIPANTE");

        SessionManager.getInstance().executeCommand(new SaveNamesCommand(getContext(), profile.getFirstname() + " " + profile.getLastname()));
    }

    private void refreshAssistances(List<Assistance> assistances) {
        eventListProfile.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        AssistanceAdapter adapter = new AssistanceAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        },assistances,getContext());
        eventListProfile.setAdapter(adapter);
    }
}
