package cn.andaction.cloudstorage.ui;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cn.andaction.cloudstorage.R;
import cn.andaction.cloudstorage.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initApi() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
       /* mNavigationView.setNavigationItemSelectedListener(this);
        mMainNavigationHeader = new MainNavigationHeader(this, mNavigationView);
        mMainNavigationHeader.bindData();*/

        mViewPager.setOffscreenPageLimit(0);

        mTabLayout.addTab(mTabLayout.newTab());
      /*  LazyFragmentAdapter adapter = new LazyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);*/
    }
    @Override
    protected void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle("FuckAlert");
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }
}
