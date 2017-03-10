package com.example.deanguo.myapplication;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.deanguo.myapplication.databinding.RecommendHeaderItemBinding;
import com.example.deanguo.myapplication.databinding.RecommendItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deanguo on 1/11/17.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_INVITED_YOU = 0;
    public static final int TYPE_RECOMMEND = 1;
    public static final int TYPE_INVITE = 2;

    public static final int HEADER_TYPE = 3;
    public static final int FRIEND_REQUEST_TYPE = 4;

    private RecommendModel.Listener listener;
    private Context context;

    private List<FriendRequest> invitedYouFriendRequestList;// invited you
    private List<FriendRequest> inviteFriendRequestList; // invite
    private List<FriendRequest> recommendFriendRequestList; // recommend

    private List<Object> mData;

    public void setData(List<Object> mData) {
        this.mData = mData;
    }

    public void setFriendRequests(int type, List<FriendRequest> requests) {
        if (!checkList(requests)) {
            return;
        }
        switch (type) {
            // invited you
            case TYPE_INVITED_YOU:
                invitedYouFriendRequestList = requests;
                break;
            case TYPE_RECOMMEND:
                recommendFriendRequestList = requests;
                break;
            case TYPE_INVITE:
                inviteFriendRequestList = requests;
                break;
        }

        mData.clear();
        // invited you
        if (checkList(invitedYouFriendRequestList)) {

            String title = context.getString(R.string.sign_up_recommend_invited_you_title);
            int count = invitedYouFriendRequestList.size();
            mData.add(new RecommendTitle(title, count));

            for (FriendRequest friendRequest : invitedYouFriendRequestList) {
                mData.add(friendRequest);
            }
        }

        // recommend
        if (checkList(recommendFriendRequestList)) {
            String title = context.getString(R.string.sign_up_recommend_recommend_title);
            int count = recommendFriendRequestList.size();
            mData.add(new RecommendTitle(title, count));
            for (FriendRequest friendRequest : recommendFriendRequestList) {
                mData.add(friendRequest);
            }
        }

        // invited you
        if (checkList(inviteFriendRequestList)) {
            String title = context.getString(R.string.sign_up_recommend_invite_title);
            int count = inviteFriendRequestList.size();
            mData.add(new RecommendTitle(title, count));
            for (FriendRequest friendRequest : inviteFriendRequestList) {
                mData.add(friendRequest);
            }
        }

        notifyDataSetChanged();
    }

    public boolean checkList(List<FriendRequest> requests) {
        return requests != null && requests.size() > 0;
    }

    public RecommendAdapter(Context context, RecommendModel.Listener listener) {
        this.context = context;
        this.listener = listener;
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding;

        switch (viewType) {
            case HEADER_TYPE:
                binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.recommend_header_item,
                        parent,
                        false);
                return new RecommendHeaderHolder((RecommendHeaderItemBinding) binding);
            case FRIEND_REQUEST_TYPE:
                binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.recommend_item,
                        parent,
                        false);
                return new RecommendViewHolder((RecommendItemBinding) binding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object obj = mData.get(position);
        // title
        if (holder instanceof RecommendHeaderHolder) {
            RecommendHeaderHolder recommendHeaderHolder = (RecommendHeaderHolder) holder;
            if (recommendHeaderHolder.binding.getViewModel() == null) {
                RecommendHeaderViewModel model = new RecommendHeaderViewModel((RecommendTitle) obj);
                recommendHeaderHolder.binding.setViewModel(model);
            }
        }
        // friend request
        else if (holder instanceof RecommendViewHolder) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            if (recommendViewHolder.binding.getViewModel() == null) {
                RecommendModel model = new RecommendModel((FriendRequest) obj, listener);
                recommendViewHolder.binding.setViewModel(model);
                recommendViewHolder.binding.executePendingBindings();
            } else {
                FriendRequest friendRequest = (FriendRequest) obj;
                recommendViewHolder.binding.getViewModel().setFriendRequest(friendRequest);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mData.get(position);
        if (o instanceof RecommendTitle) {
            return HEADER_TYPE;
        } else {
            return FRIEND_REQUEST_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {

        public final RecommendItemBinding binding;

        public RecommendViewHolder(RecommendItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class RecommendTitle {
        String title;
        int count;

        public RecommendTitle(String title, int count) {
            this.title = title;
            this.count = count;
        }
    }

    public class RecommendHeaderViewModel {

        public final ObservableField<String> headerText = new ObservableField<>();
        public RecommendHeaderViewModel(RecommendTitle recommendTitle) {
            String count = recommendTitle.count > 1 ? " ("+recommendTitle.count+")" : "";
            String title = recommendTitle.title;
            this.headerText.set(title+count);
        }
    }

    public class RecommendHeaderHolder extends RecyclerView.ViewHolder {

        public final RecommendHeaderItemBinding binding;

        public RecommendHeaderHolder(RecommendHeaderItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }

}
