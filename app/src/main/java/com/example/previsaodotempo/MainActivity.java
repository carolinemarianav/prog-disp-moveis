package com.example.previsaodotempo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.previsaodotempo.domain.WeatherCard;
import com.example.previsaodotempo.view.AboutActivity;
import com.example.previsaodotempo.view.CityScanActivity;
import com.example.previsaodotempo.view.MapFragment;
import com.example.previsaodotempo.view.WeatherListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        var weatherListFragment = new WeatherListFragment();
        var mapFragment = new MapFragment();

        adapter.addFragment(weatherListFragment, "Previsão do Tempo");
        adapter.addFragment(mapFragment, "Mapa");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, CityScanActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String woeid = data.getStringExtra("WOEID");

                if (woeid != null) {
                    refreshWeather(woeid);
                }
            }
        }
    }

    private void refreshWeather(String woeid) { // 457730 / 455827

        try {
            WeatherListFragment weatherListFragment = getSupportFragmentManager().getFragments().stream()
                    .filter(fragment -> fragment instanceof WeatherListFragment)
                    .map(fragment -> (WeatherListFragment) fragment)
                    .findFirst()
                    .orElse(null);

            if (weatherListFragment == null) {
                return;
            }

            weatherListFragment.fetchWeatherByWOEID(Integer.parseInt(woeid), (WeatherCard weatherCard) -> {
                if (!isDestroyed() && !isFinishing()) { // Verifica se a Activity ainda está ativa
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mapContainer, MapFragment.with(weatherCard.getCity(), this.getApplicationContext()))
                            .commitAllowingStateLoss(); // Use commitAllowingStateLoss para evitar o erro
                }
            });

        } catch (Exception ex) {
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {

            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
