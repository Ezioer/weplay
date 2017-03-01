package com.example.weile.materialdesignexa.ui.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.example.weile.materialdesignexa.R;
import com.example.weile.materialdesignexa.activity.DoubanMomentDetailActivity;
import com.example.weile.materialdesignexa.base.BaseFrameFragment;
import com.example.weile.materialdesignexa.bean.DoubanMomentDetailBean;
import com.example.weile.materialdesignexa.widget.HtmlTextView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by weile on 2017/1/16.
 */
public class Fragment_doubandetail extends BaseFrameFragment<DoubanDetailPresenter,
        DoubanDetailModel> implements DoubanDetailContract.View {
    private int postid;
    private String title, url, picurl;
    @Bind(R.id.detail_collapslayout)
    CollapsingToolbarLayout mColl;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.iv_detailpic)
    ImageView mDetailPic;
    @Bind(R.id.tv_detailcontent)
    HtmlTextView mMainContent;
   /* @Bind(R.id.webview)
    WebView mWebview;*/

    @Override
    protected boolean isneedani() {
        return true;
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
        if (args.getString("picurl") != null) {
            picurl = args.getString("picurl");
        }
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        initView();
        if (picurl != null && !picurl.equals(""))
            Picasso.with(mContext).load(picurl).into
                    (mDetailPic);
        getData();
       /* mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebview.loadUrl(url);
                return true;
            }
        });
        mWebview.loadUrl(url);*/

    }


    private void initView() {
        mColl.setTitle(title);
        mColl.setCollapsedTitleTextColor(Color.WHITE);
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

    private void getData() {
        mPresenter.getDetail(String.valueOf(postid));
    }

    @Override
    public void refreshData(final DoubanMomentDetailBean doubanMomentDetailBean) {
        String content = doubanMomentDetailBean.content;
        if(doubanMomentDetailBean.photos.size()!=0) {
            for (int i = 0; i < doubanMomentDetailBean.photos.size(); i++) {
                content = content.replace("id=" + "\"" + doubanMomentDetailBean.photos.get(i).tag_name + "\"", "src=" + "\"" + doubanMomentDetailBean.photos.get(i).large.url + "\"");

            }
        }
        mMainContent.setHtmlFromString(content,false);
        /*if(doubanMomentDetailBean.thumbs.size()>0)
        Picasso.with(mContext).load(doubanMomentDetailBean.thumbs.get(0).large.url).into
                (mDetailPic);*/
//        final String content=doubanMomentDetailBean.content;
//        final String  source=content.replace("id=\"img_2\"","src="+"\""+doubanMomentDetailBean
// .photos.get(0).large.url+"\"");

        /*mMainContent.setText(Html.fromHtml(html, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(final String s) {
                Drawable drawable = null;
                Log.e("ssss", s.toString() + "");
                *//*ThreadTask.getInstance().executorOtherThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(s);
                            Drawable drawable1=Drawable.createFromStream(url.openStream(),"");
                            mList.put("picdrawable",drawable1);
                        } catch (MalformedURLException e) {
                        } catch (IOException e) {
                        }
                    }
                },ThreadTask.ThreadPeriod.PERIOD_HIGHT);*//*

                do {
                    if (mList.size() != 0) {
                        drawable = mList.get("picdrawable");
                        break;
                    }
                } while (true);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
                return drawable;
            }
        }, null));*/
    }
/*
    private String path;
    private HashMap<String, Drawable> mList = new HashMap<>();

    //获得网络图片的url
    private void getImageUrl(String url) {
        FutureTarget<File> futureTarget = Glide.with(mContext).load(url).downloadOnly(500, 500);
        try {
            File cache = futureTarget.get();
            path = cache.getAbsolutePath();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }*/
}
