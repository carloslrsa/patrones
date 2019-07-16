package com.patrones.apppanamericanos.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.views.activities.EventActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventReviewAdapter extends RecyclerView.Adapter<EventReviewAdapter.ViewHolder> {


    private List<EventPreview> events;
    private Context context;

    public EventReviewAdapter(List<EventPreview> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.events_fragment_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.background_event_iv)
        ImageView background;
        @BindView(R.id.title_eventpreview_tv)
        TextView title;
        @BindView(R.id.date_eventpreview_tv)
        TextView dateTime;

        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
        }

        public void Bind(final EventPreview event){
            if(event != null){
                title.setText(event.getTitle());
                dateTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(event.getDate()));
                Picasso.get().load(event.getImageUrl()).into(background);

                mView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, EventActivity.class);
                    intent.putExtra("eventId", event.getId());

                    context.startActivity(intent);
                });
            }
        }
    }
}
