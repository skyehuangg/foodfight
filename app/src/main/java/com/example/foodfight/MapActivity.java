package com.example.foodfight;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppDatabase db;
    private ExecutorService executorService;

    private TextView mapSearchText;
    private Dialog dialog;
    private ArrayList<String> schoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        executorService = Executors.newSingleThreadExecutor();
        db = AppDatabase.getInstance(this);

        mapSearchText = findViewById(R.id.mapSearch);

        setupSchoolList();
        setupSearchableSpinner();
        setupMap();
        setupButtons();
        retrieveData();
    }

    private void setupSchoolList() {
        schoolList = new ArrayList<>();
        schoolList.add("Wall High School");
        schoolList.add("Communications High School");
        schoolList.add("Shore Regional High School");
        schoolList.add("Manasquan High School");
        schoolList.add("Wall Intermediate School");
        schoolList.add("Colts Neck High School");
    }


    private void setupSearchableSpinner() {
        mapSearchText.setOnClickListener(v -> {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_searchable_spinner);
            Objects.requireNonNull(dialog.getWindow()).setLayout(650, 800);
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    schoolList
            );

            listView.setAdapter(adapter);

            editText.addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void afterTextChanged(Editable s) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }
            });

            listView.setOnItemClickListener((parent, view, position, id) -> {
                String school = (String) parent.getItemAtPosition(position);

                mapSearchText.setText(school);

                dialog.dismiss();

                moveMapToLocation(school);
            });

        });
    }

    private void setupMap() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLocation = new LatLng(40.7128, -74.0060);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 5));
    }

    private void moveMapToLocation(String locationName) {
        if (mMap == null) return;

        executorService.execute(() -> { // run geocoding in background
            Geocoder geocoder = new Geocoder(this);
            try {
                List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    runOnUiThread(() -> { // update only map
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void setupButtons() {
        ImageButton back = findViewById(R.id.back2);
        back.setOnClickListener(v ->
                startActivity(new Intent(this, SignupModeActivity.class)));

        Button submit = findViewById(R.id.submitLocationButton);
        submit.setOnClickListener(v ->
                startActivity(new Intent(this, ConfirmSchoolActivity.class)));
    }

    private void retrieveData() {
        int uid = StorageActivity.getCurrentUserId();
        TextView schoolSearch = findViewById(R.id.schoolsearch);

        executorService.execute(() -> {
            UserDAO dao = db.getUserDAO();
            User user = dao.getUserById(uid);

            runOnUiThread(() -> {
                if (user != null) {
                    schoolSearch.setText(
                            user.getAccountType().equals("Student")
                                    ? "Search for your School"
                                    : "Search for your Child's School"
                    );
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
