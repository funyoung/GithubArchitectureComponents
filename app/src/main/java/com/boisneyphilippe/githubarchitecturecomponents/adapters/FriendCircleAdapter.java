package com.boisneyphilippe.githubarchitecturecomponents.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.Constants;
import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.beans.FriendCircleBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.OtherInfoBean;
import com.boisneyphilippe.githubarchitecturecomponents.beans.UserBean;
import com.boisneyphilippe.githubarchitecturecomponents.interfaces.OnItemClickPopupMenuListener;
import com.boisneyphilippe.githubarchitecturecomponents.interfaces.OnPraiseOrCommentClickListener;
import com.boisneyphilippe.githubarchitecturecomponents.span.TextMovementMethod;
import com.boisneyphilippe.githubarchitecturecomponents.utils.Utils;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.CommentOrPraisePopupWindow;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.MessagePanelView;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.NineGridView;
import com.boisneyphilippe.githubarchitecturecomponents.widgets.VerticalCommentWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.ielse.imagewatcher.ImageWatcher;

import java.util.ArrayList;
import java.util.List;


/**
 * @author KCrason
 * @date 2018/4/27
 */
public class FriendCircleAdapter extends RecyclerView.Adapter<FriendCircleAdapter.BaseViewHolder>
        implements OnItemClickPopupMenuListener {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<FriendCircleBean> mFriendCircleBeans;

    private RequestOptions mRequestOptions;

    private int mAvatarSize;

    private DrawableTransitionOptions mDrawableTransitionOptions;

    private CommentOrPraisePopupWindow mCommentOrPraisePopupWindow;

    private OnPraiseOrCommentClickListener mOnPraiseOrCommentClickListener;

    private ImageWatcher mImageWatcher;

    public FriendCircleAdapter(Context context, ImageWatcher imageWatcher) {
        this.mContext = context;
        this.mImageWatcher = imageWatcher;
        this.mAvatarSize = context.getResources().getDimensionPixelSize(R.dimen.circle_stream_avatar_size);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRequestOptions = new RequestOptions().centerCrop();
        this.mDrawableTransitionOptions = DrawableTransitionOptions.withCrossFade();
        if (context instanceof OnPraiseOrCommentClickListener) {
            this.mOnPraiseOrCommentClickListener = (OnPraiseOrCommentClickListener) context;
        }
    }

    public void setFriendCircleBeans(List<FriendCircleBean> friendCircleBeans) {
        this.mFriendCircleBeans = friendCircleBeans;
        notifyDataSetChanged();
    }

    public void addFriendCircleBeans(List<FriendCircleBean> friendCircleBeans) {
        if (friendCircleBeans != null) {
            if (mFriendCircleBeans == null) {
                mFriendCircleBeans = new ArrayList<>();
            }
            this.mFriendCircleBeans.addAll(friendCircleBeans);
            notifyItemRangeInserted(mFriendCircleBeans.size(), friendCircleBeans.size());
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD) {
            return new OnlyWordViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_only_word, parent, false));
        } else if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL) {
            return new WordAndUrlViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_url, parent, false));
        } else if (viewType == Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES) {
            return new WordAndImagesViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_images, parent, false));
        } else if (Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_HEADER == viewType) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_circle_header, parent, false));
        } else if (Constants.FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE == viewType) {
            return new MessageViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_circle_message, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder != null && mFriendCircleBeans != null && position < mFriendCircleBeans.size()) {
            FriendCircleBean friendCircleBean = mFriendCircleBeans.get(position);
            makeUserBaseData(holder, friendCircleBean, position);
            if (holder instanceof OnlyWordViewHolder) {
                OnlyWordViewHolder onlyWordViewHolder = (OnlyWordViewHolder) holder;
            } else if (holder instanceof WordAndUrlViewHolder) {
                WordAndUrlViewHolder wordAndUrlViewHolder = (WordAndUrlViewHolder) holder;
                wordAndUrlViewHolder.layoutUrl.setOnClickListener(v -> Toast.makeText(mContext, "You Click Layout Url", Toast.LENGTH_SHORT).show());
            } else if (holder instanceof WordAndImagesViewHolder) {
                WordAndImagesViewHolder wordAndImagesViewHolder = (WordAndImagesViewHolder) holder;
                wordAndImagesViewHolder.nineGridView.setOnImageClickListener((position1, view) ->
                {
                    SparseArray<ImageView> imageGroupList = new SparseArray<>();
                    int i = 0;
                    for (ImageView imageView : wordAndImagesViewHolder.nineGridView.getImageViews()) {
                        imageGroupList.put(i++, imageView);
                    }
                    List<Uri> urlList = new ArrayList<>();
                    for (String str : friendCircleBean.getImageUrls()) {
                        urlList.add(Uri.parse(str));
                    }
                    mImageWatcher.show((ImageView) view, imageGroupList,  urlList);
                });
                wordAndImagesViewHolder.nineGridView.setAdapter(new NineImageAdapter(mContext, mRequestOptions,
                        mDrawableTransitionOptions, friendCircleBean.getImageUrls()));
            } else {
                holder.bind(friendCircleBean, position);
            }
        }
    }


    private void makeUserBaseData(BaseViewHolder baseViewHolder, FriendCircleBean friendCircleBean, int position) {
        if (!(baseViewHolder instanceof BaseFriendCircleViewHolder)) {
            return;
        }

        BaseFriendCircleViewHolder holder = (BaseFriendCircleViewHolder) baseViewHolder;
        holder.txtContent.setText(friendCircleBean.getContentSpan());
        setContentShowState(holder, friendCircleBean);
        holder.txtContent.setOnLongClickListener(v -> {
            Utils.showPopupMenu(mContext, this, position, v);
            return true;
        });

        UserBean userBean = friendCircleBean.getUserBean();
        if (userBean != null) {
            holder.txtUserName.setText(userBean.getUserName());
            Glide.with(mContext).load(userBean.getUserAvatarUrl())
                    .apply(mRequestOptions.override(mAvatarSize, mAvatarSize))
                    .transition(mDrawableTransitionOptions)
                    .into(holder.imgAvatar);
        }

        OtherInfoBean otherInfoBean = friendCircleBean.getOtherInfoBean();

        if (otherInfoBean != null) {
            holder.txtSource.setText(otherInfoBean.getSource());
            holder.txtPublishTime.setText(otherInfoBean.getTime());
        }

        if (friendCircleBean.isShowPraise() || friendCircleBean.isShowComment()) {
            holder.layoutPraiseAndComment.setVisibility(View.VISIBLE);
            if (friendCircleBean.isShowComment() && friendCircleBean.isShowPraise()) {
                holder.viewLine.setVisibility(View.VISIBLE);
            } else {
                holder.viewLine.setVisibility(View.GONE);
            }
            if (friendCircleBean.isShowPraise()) {
                holder.txtPraiseContent.setVisibility(View.VISIBLE);
                holder.txtPraiseContent.setText(friendCircleBean.getPraiseSpan());
            } else {
                holder.txtPraiseContent.setVisibility(View.GONE);
            }
            if (friendCircleBean.isShowComment()) {
                holder.verticalCommentWidget.setVisibility(View.VISIBLE);
                holder.verticalCommentWidget.addComments(friendCircleBean.getCommentBeans(), false);
            } else {
                holder.verticalCommentWidget.setVisibility(View.GONE);
            }
        } else {
            holder.layoutPraiseAndComment.setVisibility(View.GONE);
        }

        holder.imgPraiseOrComment.setOnClickListener(v -> {
            if (mContext instanceof Activity) {
                if (mCommentOrPraisePopupWindow == null) {
                    mCommentOrPraisePopupWindow = new CommentOrPraisePopupWindow(mContext);
                }
                mCommentOrPraisePopupWindow
                        .setOnPraiseOrCommentClickListener(mOnPraiseOrCommentClickListener)
                        .setCurrentPosition(position);
                if (mCommentOrPraisePopupWindow.isShowing()) {
                    mCommentOrPraisePopupWindow.dismiss();
                } else {
                    mCommentOrPraisePopupWindow.showPopupWindow(v);
                }
            }
        });

        holder.txtLocation.setOnClickListener(v -> Toast.makeText(mContext, "You Click Location", Toast.LENGTH_SHORT).show());
    }

    private void setContentShowState(BaseFriendCircleViewHolder holder, FriendCircleBean friendCircleBean) {
        if (friendCircleBean.isShowCheckAll()) {
            holder.txtState.setVisibility(View.VISIBLE);
            setTextState(holder, friendCircleBean.isExpanded());
            holder.txtState.setOnClickListener(v -> {
                if (friendCircleBean.isExpanded()) {
                    friendCircleBean.setExpanded(false);
                } else {
                    friendCircleBean.setExpanded(true);
                }
                setTextState(holder, friendCircleBean.isExpanded());
            });
        } else {
            holder.txtState.setVisibility(View.GONE);
            holder.txtContent.setMaxLines(Integer.MAX_VALUE);
        }
    }

    private void setTextState(BaseFriendCircleViewHolder holder, boolean isExpand) {
        if (isExpand) {
            holder.txtContent.setMaxLines(Integer.MAX_VALUE);
            holder.txtState.setText("收起");
        } else {
            holder.txtContent.setMaxLines(4);
            holder.txtState.setText("全文");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mFriendCircleBeans.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mFriendCircleBeans == null ? 0 : mFriendCircleBeans.size();
    }


    @Override
    public void onItemClickCopy(int position) {
        Toast.makeText(mContext, "已复制", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickCollection(int position) {
        Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
    }


    static class WordAndImagesViewHolder extends BaseFriendCircleViewHolder {

        NineGridView nineGridView;

        public WordAndImagesViewHolder(View itemView) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
        }
    }


    static class WordAndUrlViewHolder extends BaseFriendCircleViewHolder {

        LinearLayout layoutUrl;

        public WordAndUrlViewHolder(View itemView) {
            super(itemView);
            layoutUrl = itemView.findViewById(R.id.layout_url);
        }
    }

    static class OnlyWordViewHolder extends BaseFriendCircleViewHolder {

        public OnlyWordViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(FriendCircleBean friendCircleBean, int position) {
        }
    }

    static class BaseFriendCircleViewHolder extends BaseViewHolder {
        public VerticalCommentWidget verticalCommentWidget;
        public TextView txtUserName;
        public View viewLine;
        public TextView txtPraiseContent;
        public ImageView imgAvatar;
        public TextView txtSource;
        public TextView txtPublishTime;
        public ImageView imgPraiseOrComment;
        public TextView txtLocation;
        public TextView txtContent;
        public TextView txtState;
        public LinearLayout layoutPraiseAndComment;

        public BaseFriendCircleViewHolder(View itemView) {
            super(itemView);
            verticalCommentWidget = itemView.findViewById(R.id.vertical_comment_widget);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            txtPraiseContent = itemView.findViewById(R.id.praise_content);
            viewLine = itemView.findViewById(R.id.view_line);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtSource = itemView.findViewById(R.id.txt_source);
            txtPublishTime = itemView.findViewById(R.id.txt_publish_time);
            imgPraiseOrComment = itemView.findViewById(R.id.img_click_praise_or_comment);
            txtLocation = itemView.findViewById(R.id.txt_location);
            txtContent = itemView.findViewById(R.id.txt_content);
            txtState = itemView.findViewById(R.id.txt_state);
            layoutPraiseAndComment = itemView.findViewById(R.id.layout_praise_and_comment);
            txtPraiseContent.setMovementMethod(new TextMovementMethod());
        }
    }

    static class HeaderViewHolder extends BaseViewHolder {
        private ImageView coverImageView;
        private ImageView avatarImageView;
        private TextView nameTextView;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.header_cover);
            avatarImageView = itemView.findViewById(R.id.header_avatar);
            nameTextView = itemView.findViewById(R.id.header_name);
        }

        @Override
        public void bind(FriendCircleBean friendCircleBean, int position) {
        }
    }

    static class MessageViewHolder extends BaseViewHolder {
        private MessagePanelView messagePanelView;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messagePanelView = itemView.findViewById(R.id.circle_message);
        }

        @Override
        public void bind(FriendCircleBean friendCircleBean, int position) {
        }
    }
}
