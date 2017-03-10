package com.example.deanguo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.example.deanguo.myapplication.RecommendModel.*;

/**
 * Created by deanguo on 1/4/17.
 */

public class RecommendPage extends BaseSignUpFragment implements Listener{

    private RecyclerView recyclerView;
    private View skip;
    private View addAll;

    private RecommendAdapter adapter;

    public RecommendPage(SignUpViewModel model) {
        super(model);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        skip = rootView.findViewById(R.id.skip_btn);
        addAll = rootView.findViewById(R.id.add_all_btn);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adapter = new RecommendAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadInvitations();
    }

    private void loadInvitations() {
        List<FriendRequest> a = new ArrayList<>();
        a.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));

        List<FriendRequest> b = new ArrayList<>();
        b.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));
        b.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));
        b.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));
        b.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));
        b.add(new FriendRequest(new User("AHAHAHAH", "1243252355", "aa")));

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(new FriendRequest(new User("AAA", "1243252355", "aa")));
        friendRequestList.add(new FriendRequest(new User("BBB", "1513535", "aa")));
        friendRequestList.add(new FriendRequest(new User("CCC", "63234", "aa")));
        friendRequestList.add(new FriendRequest(new User("DDD", "678821", "aa")));
        friendRequestList.add(new FriendRequest(new User("FFF", "334255", "aa")));
        friendRequestList.add(new FriendRequest(new User("EEE", "11111111", "aa")));
        adapter.setFriendRequests(RecommendAdapter.TYPE_INVITED_YOU, a);
        adapter.setFriendRequests(RecommendAdapter.TYPE_RECOMMEND, b);
        adapter.setFriendRequests(RecommendAdapter.TYPE_INVITE, friendRequestList);

    }

    @Override
    public int getPageIndex() {
        return STEP_RECOMMEND;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public boolean isShowKeyBoard() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.recommend_page;
    }

    @Override
    public void initView() {

    }

    @Override
    public void clearView() {

    }

    @Override
    public void onContinuePress() {

    }

    @Override
    public void onAdd(FriendRequest friendRequest) {

    }
}
