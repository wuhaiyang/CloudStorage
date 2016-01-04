package cn.andaction.cloudstorage.util.loaddinglayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2016-01-04
 * Time: 20:42
 * Description:
 * FIXME
 */
public class VaryViewHelper implements IVaryViewHelper {


    private View mView;
    private ViewGroup mParentView;
    private int mViewIndex;
    private ViewGroup.LayoutParams mLayoutParams;
    private View mCurrentView;

    public VaryViewHelper(View view) {
        super();
        this.mView = view;
    }

    private void init() {
        mLayoutParams = mView.getLayoutParams();
        if (mView.getParent() != null) {
            mParentView = (ViewGroup) mView.getParent();
        } else {
            mParentView = (ViewGroup) mView.getRootView().findViewById(android.R.id.content);
        }
        int count = mParentView.getChildCount();
        for (int i = 0; i < count; i++) {
            if (mView == mParentView.getChildAt(i)) {
                mViewIndex = i;
                break;
            }
        }
        mCurrentView = mView;

    }

    @Override
    public View getCurrentLayout() {
        return mCurrentView;
    }

    @Override
    public void restoreView() {
        showLayout(mView);
    }

    @Override
    public void showLayout(View view) {
        if (null == mParentView) {
            init();
        }

        if (mParentView.getChildAt(mViewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
            mParentView.removeView(mCurrentView);
            mParentView.addView(view, mViewIndex, mLayoutParams);
        }
        this.mCurrentView = view;
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(mView.getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void removeAllViews() {
        if (null != mParentView) {
            mParentView.removeAllViews();
            mParentView = null;
        }
    }
}