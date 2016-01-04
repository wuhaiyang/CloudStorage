package cn.andaction.cloudstorage.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vdisk.android.VDiskAuthSession;
import com.vdisk.android.VDiskDialogListener;
import com.vdisk.net.exception.VDiskDialogError;
import com.vdisk.net.exception.VDiskException;
import com.vdisk.net.session.AccessToken;
import com.vdisk.net.session.AppKeyPair;
import com.vdisk.net.session.Session;

import cn.andaction.cloudstorage.R;
import cn.andaction.cloudstorage.ui.MainActivity;
import cn.andaction.cloudstorage.ui.base.BaseActivity;
import cn.andaction.cloudstorage.util.LogUtils;
import tyrantgit.explosionfield.ExplosionField;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2016-01-04
 * Time: 14:15
 * Description:
 * FIXME
 */
public class OAuth2Activity extends BaseActivity implements VDiskDialogListener {

    private TextView mTv_Auth;

    private ExplosionField mExplosionField;


    public static final String CONSUMER_KEY = "1544327106";
    public static final String CONSUMER_SECRET = "cc125370bbfa03d8817fbcd929c9037b";
    private static final String REDIRECT_URL = "http://vdisk.weibo.com/";


    private VDiskAuthSession mVDiskAuthSession;

    @Override
    protected void initApi() {
        AppKeyPair appKeyPair = new AppKeyPair(CONSUMER_KEY, CONSUMER_SECRET);
        mVDiskAuthSession = VDiskAuthSession.getInstance(this, appKeyPair, Session.AccessType.APP_FOLDER);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_oauth2;
    }

    @Override
    protected void initViews() {
        mTv_Auth = (TextView) findViewById(R.id.tv_auth);
    }

    @Override
    protected void bindAttr() {
        mExplosionField = ExplosionField.attach2Window(this);
        mTv_Auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oAuthLogic();
            }
        });
    }

    private void oAuthLogic() {
        mExplosionField.explode(mTv_Auth);

        mVDiskAuthSession.setRedirectUrl(REDIRECT_URL);
        mVDiskAuthSession.authorize(this, this);

        /**
         *  if you have logined jump to the MainActivity
         */
        if (mVDiskAuthSession.isLinked()) {
            readyGoThenKill(MainActivity.class, true);
        }

    }

    @Override
    public void onComplete(Bundle values) {
        if (null != values) {
            AccessToken token = (AccessToken) values.getSerializable(VDiskAuthSession.OAUTH2_TOKEN);
            mVDiskAuthSession.finishAuthorize(token);
            LogUtils.w("token-->" + token.mAccessToken + "; uid-->"
                    + token.mUid + "; expires-->" + token.mExpiresIn
                    + "; refreshToken-->" + token.mRefreshToken);
        }
        readyGoThenKill(MainActivity.class, true);
    }

    @Override
    public void onError(VDiskDialogError error) {
        Toast.makeText(getApplicationContext(),
                "Auth error : " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVDiskException(VDiskException exception) {
        Toast.makeText(getApplicationContext(),
                "Auth exception : " + exception.getMessage(), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(getApplicationContext(), "Auth cancel",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mExplosionField.clear();
        mExplosionField.destroyDrawingCache();
        mExplosionField = null;
        mVDiskAuthSession = null;
        super.onDestroy();
    }
}