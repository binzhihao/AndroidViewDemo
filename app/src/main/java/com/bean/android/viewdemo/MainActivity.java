package com.bean.android.viewdemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bean.android.viewdemo.view.ViewFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.navigation_view);

        mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open_description, R.string.drawer_close_description){
            View mainContent = (View)findViewById(R.id.main_content);
            @Override
            public void onDrawerSlide(View view, float offset){
                mainContent.scrollTo(-(int)(mNavigationView.getWidth()*offset), 0);
                super.onDrawerSlide(view, offset);
            }
        };
        mDrawerToggle.syncState();  //?
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switchNavigation(menuItem.getItemId());
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        switchNavigation(R.id.navigation_item_view);
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        super.onDestroy();
    }

    public void switchNavigation(int id){
        switch (id){
            case R.id.navigation_item_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ViewFragment()).commit();
                break;
            case R.id.navigation_item_animation:
                break;
        }
    }
}
