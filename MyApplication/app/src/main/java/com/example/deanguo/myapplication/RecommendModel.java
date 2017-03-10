package com.example.deanguo.myapplication;

import android.databinding.BaseObservable;
import android.view.View;

/**
 * Created by deanguo on 1/11/17.
 */

public class RecommendModel extends BaseObservable {

    boolean isAdded = false;
    private FriendRequest friendRequest;
    private Listener listener;

    public RecommendModel(FriendRequest friendRequest, Listener listener) {
        this.friendRequest = friendRequest;
        this.listener = listener;
    }
//
//    @BindingAdapter({"imageUrl"})
//    public static void loadImage(ImageView imageView, RecommendModel viewModel) {
//
////        ImageViewUtil.loadAvatar(imageView, viewModel.getAvatar(), viewModel.getUsername());
//    }

    public float getButtonAplha() {
        float a = friendRequest.isAdd ? 0.2f : 1f;
        return a;
    }


    public boolean isSendButtonVisible() {
        return friendRequest.isAdd;
    }

    public boolean getEnableAplha() {
        return isAdded ? false : true;
    }


    public int getRecommendVisible() {
        return friendRequest == null ? View.GONE : View.VISIBLE;
    }

    public String getAvatar() {
        return friendRequest == null ? "" : friendRequest.target.avatar;
    }

    public String getUsername() {
        return friendRequest == null ? "" : friendRequest.target.name;
    }

    public String getPhoneNumber() {
        return friendRequest == null ? "" : friendRequest.target.phoneNumber;
    }

    public void add(View view) {
        friendRequest.isAdd = true;
        notifyChange();
//        view.setAlpha(0.2f);
//        view.setEnabled(false);
        if (listener != null) {
            listener.onAdd(friendRequest);
        }
    }

    public void setFriendRequest(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
        notifyChange();
    }

    public interface Listener {
        void onAdd(FriendRequest friendRequest);
    }

}
