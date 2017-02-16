package com.example.weile.materialdesignexa.ui.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.activity.DoubanMomentDetailActivity;
import com.example.weile.materialdesignexa.base.BaseFrameFragment;
import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by weile on 2017/1/16.
 */
public class Fragment_doubandetail extends BaseFrameFragment<DoubanDetailPresenter,
        DoubanDetailModel> implements DoubanDetailContract.View {
    private int postid;
    private String title,url;
    @Bind(R.id.detail_collapslayout)
    CollapsingToolbarLayout mColl;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.iv_detailpic)
    ImageView mDetailPic;
   /* @Bind(R.id.tv_detailcontent)
    TextView mMainContent;*/
    @Bind(R.id.webview)
    WebView mWebview;

    @Override
    protected boolean isneedani() {
        return false;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_doubanmomentdetail;
    }

    public static Fragment_doubandetail newInstance() {
        return new Fragment_doubandetail();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        postid = args.getInt("postid");
        title = args.getString("title");
        url = args.getString("url");
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initView();
        getDate();
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebview.loadUrl(url);
                return true;
            }
        });
        mWebview.loadUrl(url);
    }


    private void initView() {
        mColl.setTitle(title);
        DoubanMomentDetailActivity activity = (DoubanMomentDetailActivity) getActivity();
        activity.setSupportActionBar(mToolBar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void getDate() {
        mPresenter.getDetail(String.valueOf(postid));
    }

    @Override
    public void refreshData(DoubanMomentDetailBean doubanMomentDetailBean) {
        if(doubanMomentDetailBean.thumbs.size()>0)
        Picasso.with(mContext).load(doubanMomentDetailBean.thumbs.get(0).large.url).into
                (mDetailPic);
//        mMainContent.setText(Html.fromHtml(doubanMomentDetailBean.content));
    }

}
