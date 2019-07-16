package com.patrones.apppanamericanos.views.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.patrones.apppanamericanos.viewmodels.EventViewModel;
import com.patrones.apppanamericanos.models.entities.Comment;
import com.patrones.apppanamericanos.models.entities.Event;
import com.patrones.apppanamericanos.utils.adapters.CommentAdapter;
import com.patrones.apppanamericanos.utils.viewtemplates.MyActivity;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.observer.design.IObserver;
import com.patrones.apppanamericanos.views.dialogs.PostCommentDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends MyActivity implements OnMapReadyCallback {
    //Layouts
    @BindView(R.id.baselayout_event_cl)
    View baseLayout;
    @BindView(R.id.loadinglayout_event_cl)
    View loadingLayout;
    @BindView(R.id.alternativelayout_event_cl)
    View alternativeLayout;
    //Base
    @BindView(R.id.place_event_tv)
    TextView placeTextView;
    @BindView(R.id.date_event_tv)
    TextView dateTextView;
    @BindView(R.id.title_event_tv)
    TextView titleTextView;
    @BindView(R.id.description_event_tv)
    TextView descriptionTextView;
    @BindView(R.id.image_event_iv)
    ImageView photoImageView;
    @BindView(R.id.comment_list_event_rv)
    RecyclerView commentsRecyclerView;
    @BindView(R.id.comment_nomatch_event_tv)
    TextView nomatchTextView;
    @BindView(R.id.postcomment_event_fab)
    FloatingActionButton postComment;

    EventViewModel viewModel;

    String eventId;

    GoogleMap mMap;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
        ButterKnife.bind(this);

        set(baseLayout, loadingLayout, alternativeLayout);
        showLoading();

        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        viewModel.getEvent().addObserver(new IObserver<Event>() {
            @Override
            public void update(Observable<Event> observable, Event data) {
                refreshEvent(data);
            }
        });
        viewModel.getComments().addObserver(new IObserver<List<Comment>>() {
            @Override
            public void update(Observable<List<Comment>> observable, List<Comment> data) {
                refreshComments(data);
            }
        });
        eventId = getIntent().getStringExtra("eventId");

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCommentDialog dialog = new PostCommentDialog(EventActivity.this,eventId);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        viewModel.loadEvent(eventId,getApplicationContext());
                    }
                });
                dialog.show();
            }
        });


        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location_event_fg);
        mapFragment.getMapAsync(this);
    }

    private void refreshEvent(Event event){
        placeTextView.setText(event.getPlace().getName());
        dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(event.getDate()));
        titleTextView.setText(event.getTitle());
        descriptionTextView.setText(event.getDescription());

        marker = mMap.addMarker(new MarkerOptions().position(event.getPlace().getCoordinates()).title(event.getTitle()));
        Picasso.get().load(event.getImageUrl()).into(photoImageView);
        showBase();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(event.getPlace().getCoordinates(),15));
    }

    private void refreshComments(List<Comment> comments) {
        if(comments == null || comments.size() == 0) {
            commentsRecyclerView.setAdapter(new CommentAdapter(new ArrayList<>(),getApplicationContext()));
            commentsRecyclerView.setVisibility(View.GONE);
            nomatchTextView.setVisibility(View.VISIBLE);
        }
        else{
            commentsRecyclerView.setAdapter(new CommentAdapter(comments,getApplicationContext()));
            commentsRecyclerView.setVisibility(View.VISIBLE);
            nomatchTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.getUiSettings().setAllGesturesEnabled(false);

        viewModel.loadEvent(eventId,getApplicationContext());

    }

}
