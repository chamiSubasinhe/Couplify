/*
 * Couplify Version 2,3
 *  SLIIT MAD Project
 *  SLIIT Kandy
 *  asiriofficial@gmail.com
 *  All Rights reserved
 * */

package com.android.couplify.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.couplify.R;
import com.android.couplify.data.SharedPreferenceHelper;
import com.android.couplify.data.StaticConfig;
import com.android.couplify.model.Configuration;
import com.android.couplify.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;
import java.util.List;


public class SensorDataFragment extends Fragment{


    private List<Configuration> listConfig = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserInfoAdapter infoAdapter;

    private static final String STEP_COUNTER_LABLE = "Step Count per day";
    private static final String EDUCATION_LABLE = "Educational Qualification";
    private static final String ACCELERATION_LABLE = "Acceleration Average";
    private static final String HEALTH_LABLE = "Overall Health Index";
    private static final String LOCATION_LABLE = "Current Location";
    private static final String POPULARITY_LABLE = "Network Popularity";
    private static final String VISIBILITY_LABLE = "Account Visibility";

    private static final int PICK_IMAGE = 1994;
    private LovelyProgressDialog waitingDialog;

    private DatabaseReference userDB;
    private FirebaseAuth mAuth;
    private User myAccount;
    private Context context;


    public SensorDataFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        userDB = FirebaseDatabase.getInstance().getReference().child("user").child(StaticConfig.UID);
        mAuth = FirebaseAuth.getInstance();

        View view = inflater.inflate(R.layout.fragment_sensordata, container, false);
        context = view.getContext();

        SharedPreferenceHelper prefHelper = SharedPreferenceHelper.getInstance(context);
        myAccount = prefHelper.getUserInfo();
        setupArrayListInfo(myAccount);

        recyclerView = (RecyclerView)view.findViewById(R.id.info_recycler_view);
        infoAdapter = new UserInfoAdapter(listConfig);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(infoAdapter);

        waitingDialog = new LovelyProgressDialog(context);
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void setupArrayListInfo(User myAccount){

        listConfig.clear();

        Configuration stepcount = new Configuration(STEP_COUNTER_LABLE, getDefaults(String.valueOf(myAccount.stepcount),context), R.mipmap.ic_transfer_within_a_station_black_48dp);
        listConfig.add(stepcount);

        Configuration acceleration = new Configuration(ACCELERATION_LABLE, "3mps", R.mipmap.ic_timeline_black_48dp);
        listConfig.add(acceleration);

        Configuration location = new Configuration(LOCATION_LABLE, myAccount.location, R.mipmap.ic_add_location_black_48dp);
        listConfig.add(location);

        Configuration health = new Configuration(HEALTH_LABLE, "4.8/5", R.mipmap.ic_graphic_eq_black_48dp);
        listConfig.add(health);

        Configuration education = new Configuration(EDUCATION_LABLE, "3155", R.mipmap.ic_school_black_48dp);
        listConfig.add(education);

        Configuration popularity = new Configuration(POPULARITY_LABLE, "1323346 Total Views", R.mipmap.ic_whatshot_black_48dp);
        listConfig.add(popularity);

        Configuration visibility = new Configuration(VISIBILITY_LABLE, "Visible", R.mipmap.ic_visibility_off_black_48dp);
        listConfig.add(visibility);
    }


    public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder>{
        private List<Configuration> profileConfig;

        public UserInfoAdapter(List<Configuration> profileConfig){
            this.profileConfig = profileConfig;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_info_item_layout, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Configuration config = profileConfig.get(position);
            holder.label.setText(config.getLabel());
            holder.value.setText(config.getValue());
            holder.icon.setImageResource(config.getIcon());
        }



        @Override
        public int getItemCount() {
            return profileConfig.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView label, value;
            public ImageView icon;
            public ViewHolder(View view) {
                super(view);
                label = (TextView)view.findViewById(R.id.tv_title);
                value = (TextView)view.findViewById(R.id.tv_detail);
                icon = (ImageView)view.findViewById(R.id.img_icon);
            }
        }

    }

}
