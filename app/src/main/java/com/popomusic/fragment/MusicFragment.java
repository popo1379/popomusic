package com.popomusic.fragment;

        import android.content.Intent;
        import android.provider.ContactsContract;
        import android.support.v7.app.ActionBar;
        import android.support.v7.widget.CardView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;

        import com.popo.popomusic.R;
        import com.popomusic.activity.BaseActivity;
        import com.popomusic.activity.CollectActivity;
        import com.popomusic.activity.DownActivity;
        import com.popomusic.activity.JKActivity;
        import com.popomusic.activity.LocalMusicActivity;
        import com.popomusic.activity.SearchActivity;
        import com.popomusic.data.LocalMusicData;
        import com.popomusic.ui.bannerViewPager.BannerViewPager;
        import com.popomusic.ui.bannerViewPager.OnPageClickListener;
        import com.popomusic.ui.bannerViewPager.ViewPagerAdapter;
        import com.popomusic.util.LogUtils;
        import com.popomusic.util.UIcollector;

        import java.util.ArrayList;
        import java.util.List;

        import butterknife.BindView;
        import butterknife.OnClick;

/**
 * Created by popo on 2017/4/6 0006.
 */
public class MusicFragment extends BaseFragment {
    @BindView(R.id.Banner)
    BannerViewPager bannerViewPager;
    @BindView(R.id.RL_classify)
    RelativeLayout classifyRL;
    @BindView(R.id.RL_love)
    RelativeLayout loveRL;
    @BindView(R.id.RL_down)
    RelativeLayout downRL;
    @BindView(R.id.RL_English)
    RelativeLayout englishRL;
    @BindView(R.id.RL_HK)
    RelativeLayout hkRL;
    @BindView(R.id.RL_JP)
    RelativeLayout jpRL;
    @BindView(R.id.RL_minyao)
    RelativeLayout minyaoRL;
    @BindView(R.id.RL_korea)
    RelativeLayout koreaRL;
    @BindView(R.id.RL_native)
    RelativeLayout nativeRL;
    @BindView(R.id.RL_yaogun)
    RelativeLayout yaogunRL;
    @BindView(R.id.QQtop)
    CardView qqtopCV;

    private ImageView iv1,iv2,iv3,iv4,iv5;
    @Override
    protected View initView() {
        if (view == null) {
            view = View.inflate(UIcollector.getActivity(),R.layout.fragment_music, null);
        }
        return view;
    }

    @Override
    protected void initData() {
/**
 * 设置轮播图
 */
        iv1 = (ImageView) LayoutInflater.from(UIcollector.getActivity()).inflate(R.layout.banner_item,bannerViewPager,false);
        iv2 = (ImageView) LayoutInflater.from(UIcollector.getActivity()).inflate(R.layout.banner_item,bannerViewPager,false);
        iv3 = (ImageView) LayoutInflater.from(UIcollector.getActivity()).inflate(R.layout.banner_item,bannerViewPager,false);
        iv4 = (ImageView) LayoutInflater.from(UIcollector.getActivity()).inflate(R.layout.banner_item,bannerViewPager,false);
        iv5 = (ImageView) LayoutInflater.from(UIcollector.getActivity()).inflate(R.layout.banner_item,bannerViewPager,false);

        iv1.setImageResource(R.drawable.main_viewpager_pic1);
        iv2.setImageResource(R.drawable.main_viewpager_pic2);
        iv3.setImageResource(R.drawable.main_viewpager_pic3);
        iv4.setImageResource(R.drawable.main_viewpager_pic4);
        iv5.setImageResource(R.drawable.main_viewpager_pic5);
        final List<ImageView> mViews = new ArrayList<>();
        mViews.add(iv1);
        mViews.add(iv2);
        mViews.add(iv3);
        mViews.add(iv4);
        mViews.add(iv5);
        //实例化ViewPagerAdapter，第一个参数是View集合，第二个参数是页面点击监听器
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(mViews, new OnPageClickListener(){
            @Override
            public void onPageClick(View view, int position) {
                Toast("别敲了啥也没有");
            }
        });
//设置适配器
        bannerViewPager.setAdapter(mAdapter);

        nativeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UIcollector.getActivity(),LocalMusicActivity.class);
                startActivity(intent);
            }
        });
    }
    @OnClick({R.id.RL_korea,R.id.RL_JP,R.id.RL_yaogun,R.id.RL_English,R.id.RL_HK,R.id.RL_minyao,R.id.QQtop,R.id.classify,R.id.RL_down,R.id.RL_love})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RL_korea:
                Intent intent=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent);
                break;
            case R.id.RL_JP:
                Intent intent1=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent1);
                break;

            case R.id.RL_yaogun:
                Intent intent2=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent2);
                break;

            case R.id.RL_English:
                Intent intent3=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent3);
                break;
            case R.id.RL_HK:
                Intent intent4=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent4);
                break;
            case R.id.RL_minyao:
                Intent intent5=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent5);
                break;
            case R.id.QQtop:
                Intent intent6=new Intent(UIcollector.getActivity(),JKActivity.class);
                startActivity(intent6);
                break;
            case  R.id.classify:
                startActivity(new Intent(UIcollector.getActivity(),SearchActivity.class));

            case R.id.RL_down:
                startActivity(new Intent(UIcollector.getActivity(),DownActivity.class));

            case R.id.RL_love:
                startActivity(new Intent(UIcollector.getActivity(),CollectActivity.class));


        }
    }
}

