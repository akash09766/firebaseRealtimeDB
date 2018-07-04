package com.developer.skylight.firebaserealtimedb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = mRoofRef.child("condition");

    private TextView mConditionTv;
    private Button mSunnyBtn, mFoggyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mConditionTv = findViewById(R.id.condition_tv_id);

        mSunnyBtn = findViewById(R.id.sunny_btn_id);
        mFoggyBtn = findViewById(R.id.foggy_btn_id);

        mSunnyBtn.setOnClickListener(this);
        mFoggyBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String val = dataSnapshot.getValue(String.class);

                Log.d(TAG, "onDataChange: val : " + val);

                if (val != null)
                    mConditionTv.setText(val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d(TAG, "onCancelled() called with: databaseError Detail = [" + databaseError.getDetails() + "]");
                Log.d(TAG, "onCancelled() called with: databaseError message = [" + databaseError.getMessage() + "]");
                Log.d(TAG, "onCancelled() called with: databaseError code = [" + databaseError.getCode() + "]");
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (mSunnyBtn == v) {
            mConditionRef.setValue("sunny");
            return;
        }

        if (mFoggyBtn == v) {
            mConditionRef.setValue("foggy");
            return;
        }
    }
}
