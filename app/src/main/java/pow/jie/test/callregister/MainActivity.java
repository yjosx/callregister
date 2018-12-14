package pow.jie.test.callregister;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pow.jie.test.callregister.fragment.AnswerFragment;
import pow.jie.test.callregister.fragment.CallFragment;
import pow.jie.test.callregister.fragment.MissCallFragment;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private String[] titles = new String[]{"呼入电话", "呼出电话", "未接来电"};
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GainPermission();
        init();
    }

    //获取权限
    private void GainPermission() {
        //版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission
                    (MainActivity.this, android.Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission
                    (MainActivity.this, android.Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission
                    (MainActivity.this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED)) {
                // 若不为GRANTED(即为DENIED)则要申请权限了
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                android.Manifest.permission.READ_CONTACTS,
                                android.Manifest.permission.READ_CALL_LOG,
                                android.Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    // 用户权限 申请 的回调方法
    public void onRequestPermissionsResult
    (int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallFragment.readCallLog(this);
            } else {
                Toast.makeText(MainActivity.this, "授权被禁止", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void init() {
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.vp_content);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentList.add(new AnswerFragment());
        tabLayout.addTab(tabLayout.newTab().setText(titles[0]));
        fragmentList.add(new CallFragment());
        tabLayout.addTab(tabLayout.newTab().setText(titles[1]));
        fragmentList.add(new MissCallFragment());
        tabLayout.addTab(tabLayout.newTab().setText(titles[2]));

        tabLayout.addOnTabSelectedListener(this);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), titles, fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
