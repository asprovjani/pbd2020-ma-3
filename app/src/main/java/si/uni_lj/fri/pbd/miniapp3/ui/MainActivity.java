package si.uni_lj.fri.pbd.miniapp3.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.adapter.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int TABS_N = 2;


    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set application toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //configure TabLayout
        configureTabLayout();
    }

    private void configureTabLayout() {
        tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        SectionsPagerAdapter spAdapter = new SectionsPagerAdapter(MainActivity.this, TABS_N);
        viewPager.setAdapter(spAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.searchTab);
                        break;
                    case 1:
                        tab.setText(R.string.favoriteTab);
                }
            }
        }).attach();
    }
}
