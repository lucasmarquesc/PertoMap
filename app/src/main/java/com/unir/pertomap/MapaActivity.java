package com.unir.pertomap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    LatLng minhaPosicao;
    Marker marcadorSelecionado;
    String categoria;
    String API_KEY = "9ccd324a9bab424ab2170993eda14948";
    private static final int LOCATION_PERMISSION_REQUEST = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mapa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        verificarPermissaoLocalizacao();
        categoria = getIntent().getStringExtra("categoria");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.btnDistancia).setOnClickListener(v -> {
            if (marcadorSelecionado != null) {
                float[] res = new float[1];
                Location.distanceBetween(
                        minhaPosicao.latitude, minhaPosicao.longitude,
                        marcadorSelecionado.getPosition().latitude,
                        marcadorSelecionado.getPosition().longitude,
                        res
                );
                Toast.makeText(this,
                        "Distância: " + (int)res[0] + " metros",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private void verificarPermissaoLocalizacao() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST
            );
        } else {
            // Permissão já concedida
            iniciarLocalizacao();
        }
    }

    private void iniciarLocalizacao() {

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {
                        atualizarMapaComLocalizacao(location);
                    } else {
                        // Se lastLocation for null, pede atualização ativa
                        solicitarAtualizacaoLocalizacao(fusedLocationClient);
                    }
                });
    }

    private void atualizarMapaComLocalizacao(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        minhaPosicao = new LatLng(latitude, longitude);

        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(minhaPosicao)
                .title("Minha localização"));

        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(minhaPosicao, 15)
        );
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void solicitarAtualizacaoLocalizacao(FusedLocationProviderClient fusedLocationClient) {

        LocationRequest.Builder locationRequest = new LocationRequest.Builder(LOCATION_PERMISSION_REQUEST);
        locationRequest.setPriority(102);
        locationRequest.setIntervalMillis(2000);
        locationRequest.setMinUpdateIntervalMillis(1000);

        fusedLocationClient.requestLocationUpdates(
                locationRequest.build(),
                new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {

                        if (locationResult == null) return;

                        Location location = locationResult.getLastLocation();
                        atualizarMapaComLocalizacao(location);

                        // Remove atualizações após obter a localização
                        fusedLocationClient.removeLocationUpdates(this);
                    }
                },
                Looper.getMainLooper()
        );
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,
                        "Permissão concedida",
                        Toast.LENGTH_SHORT).show();

                iniciarLocalizacao();

            } else {
                Toast.makeText(this,
                        "Permissão de localização é necessária para usar o app",
                        Toast.LENGTH_LONG).show();
            }
        }
    }



    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        FusedLocationProviderClient loc =
                LocationServices.getFusedLocationProviderClient(this);

        loc.getLastLocation().addOnSuccessListener(l -> {
            minhaPosicao = new LatLng(l.getLatitude(), l.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaPosicao,14));
            carregarLugares();
        });

        mMap.setOnMarkerClickListener(marker -> {
            marcadorSelecionado = marker;
            return false;
        });
    }

    void carregarLugares() {
        String url = "https://api.geoapify.com/v2/places?"
                + "categories=" + categoria
                + "&filter=circle:" + minhaPosicao.longitude + "," + minhaPosicao.latitude + ",3000"
                + "&bias=proximity:" + minhaPosicao.longitude + "," + minhaPosicao.latitude
                + "&limit=20"
                + "&apiKey=" + API_KEY;

        StringRequest req = new StringRequest(Request.Method.GET, url,
                r -> {
                    try {
                        JSONObject json = new JSONObject(r);
                        JSONArray features = json.getJSONArray("features");

                        for (int i=0;i<features.length();i++){
                            JSONObject prop = features.getJSONObject(i)
                                    .getJSONObject("properties");

                            double lat = prop.getDouble("lat");
                            double lon = prop.getDouble("lon");
                            String nome = prop.optString("name","Local");

                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,lon))
                                    .title(nome));
                        }
                    } catch (Exception e){e.printStackTrace();}
                }, e -> e.printStackTrace());

        Volley.newRequestQueue(this).add(req);
    }
}