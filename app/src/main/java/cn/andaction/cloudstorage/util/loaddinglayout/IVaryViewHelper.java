package cn.andaction.cloudstorage.util.loaddinglayout;

import android.content.Context;
import android.view.View;

/**
 * User: Geek_Soledad(wuhaiyang@andthink.cn)
 * Date: 2016-01-04
 * Time: 20:40
 * Description:
 * FIXME
 */
public interface IVaryViewHelper {

    public View getCurrentLayout();

    public void restoreView();

    public void showLayout(View view);

    public View inflate(int layoutId);

    public Context getContext();

    public View getView();

    public void removeAllViews();


}