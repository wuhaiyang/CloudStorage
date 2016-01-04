package cn.andaction.cloudstorage.util.loaddinglayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.andaction.cloudstorage.R;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2016-01-04
 * Time: 20:52
 * Description:
 * FIXME
 */
public class VaryViewHelperController {

    private IVaryViewHelper mVaryViewHelper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(VaryViewHelper varyViewHelper) {
        this.mVaryViewHelper = varyViewHelper;
    }

    /**
     * include dataEmpty NetError LogicError ...
     *
     * @param listener
     * @param msg
     */
    public void showError(View.OnClickListener listener, String msg) {
        View inflate = mVaryViewHelper.inflate(R.layout.loadding_error_status);
        TextView textView = (TextView) inflate.findViewById(R.id.message_info);
        textView.setText(msg);

        if (null != listener) {
            inflate.setOnClickListener(listener);
        }
        mVaryViewHelper.showLayout(inflate);
    }

    public void showLoadding(String msg) {
        View layout = mVaryViewHelper.inflate(R.layout.loadding_status);
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        mVaryViewHelper.showLayout(layout);
    }

    public void showSuccessView() {
        mVaryViewHelper.restoreView();
    }

    public View getCurrentView() {
        return mVaryViewHelper.getCurrentLayout();
    }

    public void removeAllView() {
        if (null != mVaryViewHelper) {
            mVaryViewHelper.removeAllViews();
        }
    }


}