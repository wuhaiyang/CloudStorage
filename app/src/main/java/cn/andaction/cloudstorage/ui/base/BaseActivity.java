package cn.andaction.cloudstorage.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.andaction.cloudstorage.R;
import cn.andaction.cloudstorage.util.SmartBarUtils;
import cn.andaction.cloudstorage.util.loaddinglayout.VaryViewHelperController;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2016-01-04
 * Time: 20:33
 * Description:
 * FIXME
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG_LOG = "";

    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    protected Toolbar mToolbar;

    protected Context mContext;

    private VaryViewHelperController mVaryViewHelperController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (toggleOverridePendingTransition()) {

            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        mContext = this;

        Bundle extras = getIntent().getExtras();

        if (null != extras) {
            handleBundleExtras();
        }


        SmartBarUtils.hide(getWindow().getDecorView());

        setTranslucentStatus(isApplyStatusBarTranslucency());

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initApi();
        initViews();
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        bindAttr();
    }

    protected void initApi() {
    }

    public void showLoadding(String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        mVaryViewHelperController.showLoadding(msg);
    }

    public void showSuccessLayout() {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        mVaryViewHelperController.showSuccessView();
    }


    protected void bindAttr() {

    }

    private View getLoadingTargetView() {
        return null;
    }

    /**
     * 不用注解模式了 注解机制实现是反射  反射其实是一个耗时操作 ： findView and bindAction
     */
    protected void initViews() {

    }

    protected abstract int getContentViewLayoutID();

    private boolean isApplyStatusBarTranslucency() {
        return true;
    }

    protected void handleBundleExtras() {

    }

    private TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    /**
     * 启动当前acitivity是否需要动画支持
     *
     * @return
     */
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 以下两个方法暂时先屏蔽掉
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isFinish)
            finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isFinish)
            finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (null != mVaryViewHelperController) {
            mVaryViewHelperController.removeAllView();
            mVaryViewHelperController = null;
        }

        super.onDestroy();
    }
}