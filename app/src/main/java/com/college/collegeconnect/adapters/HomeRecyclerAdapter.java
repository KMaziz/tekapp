package com.college.collegeconnect.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.college.collegeconnect.R;
import com.college.collegeconnect.timetable.NewTimeTable;
import com.college.collegeconnect.timetable.TimeTable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import static com.college.collegeconnect.R.drawable.ic_time_table;
import static com.college.collegeconnect.R.drawable.ic_time_table_white;

public  class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private final ArrayList<String> arrayList;
    private final ArrayList<Integer> arrayListImage;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private Intent intent;

    private final Context context;


    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_layout, parent, false);
        return new HomeRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = (new Builder()).build();


        mFirebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings);


        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);


        //noinspection unchecked
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener((OnCompleteListener)(new OnCompleteListener() {
            public final void onComplete(@NotNull Task it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it.isSuccessful()) {

                    HomeRecyclerAdapter.this.intent = new Intent(HomeRecyclerAdapter.this.getContext(), NewTimeTable.class);
                } else {
                    Log.d("Tools", "Config params  not updated");
                }

            }
        }));
        holder.getTextView().setText((CharSequence)this.arrayList.get(position));

        holder.getImageView().setImageResource(ic_time_table);
        holder.itemView.setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                HomeRecyclerAdapter.this.getContext().startActivity(intent);
            }
        }));
    }


    public int getItemCount() {
        return this.arrayList.size();
    }

    public final Context getContext() {
        return this.context;
    }

    public HomeRecyclerAdapter(@NotNull Context context) {
        super();
        this.context = context;
        this.arrayList = new ArrayList<>();
        this.arrayListImage =new ArrayList<>();
        this.arrayList.add("Time Table");
        this.arrayListImage.add(ic_time_table);
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public final TextView getTextView() {
            return this.textView;
        }

        public final ImageView getImageView() {
            return this.imageView;
        }

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            textView  = (TextView) itemView.findViewById(R.id.cardName);

            imageView = (ImageView)itemView.findViewById(R.id.cardImageHome);

        }
    }
}
