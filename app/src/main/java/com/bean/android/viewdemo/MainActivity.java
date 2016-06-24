package com.bean.android.viewdemo;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.bean.android.banner.*;
import com.bean.android.viewdemo.view.ViewFragment;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CoordinatorLayout mCorLayout;
    private FloatingActionButton mFab;
    private View mMenuButton;
    private PopupMenu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

        }
        //((UiModeManager)getSystemService(Context.UI_MODE_SERVICE)).setNightMode(UiModeManager.MODE_NIGHT_YES);

        mCorLayout=(CoordinatorLayout)findViewById(R.id.main_content);

        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.navigation_view);

        mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open_description, R.string.drawer_close_description){
            @Override
            public void onDrawerSlide(View view, float offset){
                mCorLayout.scrollTo(-(int)(mNavigationView.getWidth()*offset), 0);
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

        mFab=(FloatingActionButton)findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCorLayout,R.string.app_name,Snackbar.LENGTH_LONG)
                        .setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO:add an action here
                            }
                        })
                        .show();
            }
        });

        switchNavigation(R.id.navigation_item_view);
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerToggle);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            // 通过反射获取方法令icon显示出来
            if (menu.getClass().getSimpleName().endsWith("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {}
            }
            // 可以对icon进行更改，比如有消息什么的。如果有更高的需求，建议toolbar放置一个ImageButton然后使用PopupWindow实现高度定制的菜单
            //menu.findItem(R.id.more).setIcon(R.drawable.mgi);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mode:
                if(!Constant.NIGHT) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Constant.NIGHT=true;
                    recreate();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Constant.NIGHT=false;
                    recreate();
                }
                break;
            case R.id.settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
