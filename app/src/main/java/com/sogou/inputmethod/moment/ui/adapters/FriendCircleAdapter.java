package com.sogou.inputmethod.moment.ui.adapters;

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
import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.sogou.inputmethod.moment.ui.beans.CircleHeaderBean;
import com.sogou.inputmethod.moment.ui.beans.CircleItemBean;
import com.sogou.inputmethod.moment.ui.beans.CircleMessageBean;
import com.sogou.inputmethod.moment.ui.beans.CirclePostBean;
import com.sogou.inputmethod.moment.ui.beans.OtherInfoBean;
import com.sogou.inputmethod.moment.ui.beans.UserBean;
import com.sogou.inputmethod.moment.ui.interfaces.OnItemClickPopupMenuListener;
import com.sogou.inputmethod.moment.ui.interfaces.OnPraiseOrCommentClickListener;
import com.sogou.inputmethod.moment.ui.span.TextMovementMethod;
import com.sogou.inputmethod.moment.ui.RenderUtil;
import com.sogou.inputmethod.moment.Todo;
import com.sogou.inputmethod.moment.utils.Utils;
import com.sogou.inputmethod.moment.ui.widgets.CommentOrPraisePopupWindow;
import com.sogou.inputmethod.moment.ui.widgets.MessagePanelView;
import com.sogou.inputmethod.moment.ui.widgets.NineGridView;
import com.sogou.inputmethod.moment.ui.widgets.VerticalCommentWidget;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.ielse.imagewatcher.ImageWatcher;

import java.security.InvalidParameterException;
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

    private List<CircleItemBean> mCirclePostBeans;

    private RequestOptions mRequestOptions;

    private int mAvatarSize;

    private DrawableTransitionOptions mDrawableTransitionOptions;

    private CommentOrPraisePopupWindow mCommentOrPraisePopupWindow;

    private OnPraiseOrCommentClickListener mOnPraiseOrCommentClickListener;

    private ImageWatcher mImageWatcher;

    private final MessageViewHolder.OnMessageClickedListener listener = new MessageViewHolder.OnMessageClickedListener() {
        @Override
        public void onMessageClicked(long id) {
            Todo.onMessageClicked(id);

            int offset = Todo.DEFAULT_MESSAGE_OFFSET;
            for (int i = 0; i < mCirclePostBeans.size(); i++) {
                CircleItemBean itemBean = mCirclePostBeans.get(i);
                if (itemBean.getId() == id) {
                    offset = i;
                    break;
                }
            }
        }
    };

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

    public void setFriendCircleBeans(List<CircleItemBean> circlePostBeans) {
        this.mCirclePostBeans = circlePostBeans;
        notifyDataSetChanged();
    }

    public void addFriendCircleBeans(List<CircleItemBean> circlePostBeans) {
        if (circlePostBeans != null) {
            if (mCirclePostBeans == null) {
                mCirclePostBeans = new ArrayList<>();
            }
            this.mCirclePostBeans.addAll(circlePostBeans);
            notifyItemRangeInserted(mCirclePostBeans.size(), circlePostBeans.size());
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FriendCircleType.FRIEND_CIRCLE_TYPE_ONLY_WORD) {
            return new OnlyWordViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_only_word, parent, false));
        } else if (viewType == FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_URL) {
            return new WordAndUrlViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_url, parent, false));
        } else if (viewType == FriendCircleType.FRIEND_CIRCLE_TYPE_WORD_AND_IMAGES) {
            return new WordAndImagesViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_firend_circle_word_and_images, parent, false),
                    mImageWatcher, mRequestOptions, mDrawableTransitionOptions);
        } else if (FriendCircleType.FRIEND_CIRCLE_TYPE_HEADER == viewType) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_circle_header, parent, false));
        } else if (FriendCircleType.FRIEND_CIRCLE_TYPE_MESSAGE == viewType) {
            return new MessageViewHolder(listener, mLayoutInflater.inflate(R.layout.item_recycler_circle_message, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder != null && mCirclePostBeans != null && position < mCirclePostBeans.size()) {
            CircleItemBean circlePostBean = mCirclePostBeans.get(position);
            makeUserBaseData(holder, circlePostBean, position);
            holder.bind(circlePostBean, position);
        }
    }


    private void makeUserBaseData(BaseViewHolder baseViewHolder, CircleItemBean itemBean, int position) {
        if (!(baseViewHolder instanceof BaseFriendCircleViewHolder && itemBean instanceof CirclePostBean)) {
            return;
        }

        CirclePostBean circlePostBean = (CirclePostBean) itemBean;
        BaseFriendCircleViewHolder holder = (BaseFriendCircleViewHolder) baseViewHolder;
        holder.txtContent.setText(circlePostBean.getContentSpan());
        setContentShowState(holder, circlePostBean);
        holder.txtContent.setOnLongClickListener(v -> {
            Utils.showPopupMenu(mContext, this, position, v);
            return true;
        });

        UserBean userBean = circlePostBean.getUserBean();
        if (userBean != null) {
            holder.txtUserName.setText(userBean.getUserName());
            Glide.with(mContext).load(userBean.getUserAvatarUrl())
                    .apply(mRequestOptions.override(mAvatarSize, mAvatarSize))
                    .transition(mDrawableTransitionOptions)
                    .into(holder.imgAvatar);
        }

        OtherInfoBean otherInfoBean = circlePostBean.getOtherInfoBean();

        if (otherInfoBean != null) {
//            holder.txtSource.setText(otherInfoBean.getSource());
            holder.txtPublishTime.setText(otherInfoBean.getTime());
        }

        if (circlePostBean.isShowPraise() || circlePostBean.isShowComment()) {
            holder.layoutPraiseAndComment.setVisibility(View.VISIBLE);
            if (circlePostBean.isShowComment() && circlePostBean.isShowPraise()) {
                holder.viewLine.setVisibility(View.VISIBLE);
            } else {
                holder.viewLine.setVisibility(View.GONE);
            }
            if (circlePostBean.isShowPraise()) {
                holder.txtPraiseContent.setVisibility(View.VISIBLE);
                holder.txtPraiseContent.setText(circlePostBean.getPraiseSpan());
            } else {
                holder.txtPraiseContent.setVisibility(View.GONE);
            }
            if (circlePostBean.isShowComment()) {
                holder.verticalCommentWidget.setVisibility(View.VISIBLE);
                holder.verticalCommentWidget.addComments(circlePostBean.getCommentBeans(), false);
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

    private void setContentShowState(BaseFriendCircleViewHolder holder, CirclePostBean circlePostBean) {
        if (circlePostBean.isShowCheckAll()) {
            holder.txtState.setVisibility(View.VISIBLE);
            setTextState(holder, circlePostBean.isExpanded());
            holder.txtState.setOnClickListener(v -> {
                if (circlePostBean.isExpanded()) {
                    circlePostBean.setExpanded(false);
                } else {
                    circlePostBean.setExpanded(true);
                }
                setTextState(holder, circlePostBean.isExpanded());
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
        return mCirclePostBeans.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mCirclePostBeans == null ? 0 : mCirclePostBeans.size();
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
        private final NineGridView nineGridView;

        private final ImageWatcher mImageWatcher;
        private final RequestOptions mRequestOptions;
        private final DrawableTransitionOptions mDrawableTransitionOptions;

        public WordAndImagesViewHolder(View itemView, ImageWatcher mImageWatcher,
                                       RequestOptions requestOptions, DrawableTransitionOptions transitionOptions) {
            super(itemView);
            nineGridView = itemView.findViewById(R.id.nine_grid_view);
            this.mImageWatcher = mImageWatcher;
            this.mRequestOptions = requestOptions;
            this.mDrawableTransitionOptions = transitionOptions;
        }

        @Override
        public void bind(CircleItemBean itemBean, int position) {
            if (!(itemBean instanceof CirclePostBean)) {
                // unexpected case exception.
                return;
            }

            CirclePostBean circlePostBean = (CirclePostBean) itemBean;
            nineGridView.setOnImageClickListener((position1, view) -> {
                SparseArray<ImageView> imageGroupList = new SparseArray<>();
                int i = 0;
                for (ImageView imageView : nineGridView.getImageViews()) {
                    imageGroupList.put(i++, imageView);
                }
                List<Uri> urlList = new ArrayList<>();
                for (String str : circlePostBean.getImageUrls()) {
                    urlList.add(Uri.parse(str));
                }
                mImageWatcher.show((ImageView) view, imageGroupList,  urlList);
            });
            nineGridView.setAdapter(new NineImageAdapter(nineGridView.getContext(), mRequestOptions,
                    mDrawableTransitionOptions, circlePostBean.getImageUrls()));
        }
    }


    static class WordAndUrlViewHolder extends BaseFriendCircleViewHolder {
        LinearLayout layoutUrl;

        public WordAndUrlViewHolder(View itemView) {
            super(itemView);
            layoutUrl = itemView.findViewById(R.id.layout_url);
        }

        @Override
        public void bind(CircleItemBean itemBean, int position) {
            layoutUrl.setOnClickListener(v -> Toast.makeText(v.getContext(), "You Click Layout Url", Toast.LENGTH_SHORT).show());
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

        public void bind(CircleItemBean circlePostBean, int position) {
        }
    }

    static class BaseFriendCircleViewHolder extends BaseViewHolder {
        public VerticalCommentWidget verticalCommentWidget;
        public TextView txtUserName;
        public View viewLine;
        public TextView txtPraiseContent;
        public ImageView imgAvatar;
//        public TextView txtSource;
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
//            txtSource = itemView.findViewById(R.id.txt_source);
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
            coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Todo.showToast(coverImageView.getContext(), R.string.circle_cover_hint);
                }
            });
        }

        @Override
        public void bind(CircleItemBean circleItemBean, int position) {
            if (circleItemBean instanceof CircleHeaderBean) {
                CircleHeaderBean bean = (CircleHeaderBean) circleItemBean;
                RenderUtil.rounding(Glide.with(coverImageView), coverImageView, bean.getCover(), R.drawable.circle_header_cover, 0);
                RenderUtil.rounding(Glide.with(avatarImageView), avatarImageView, bean.getAvatar(), R.drawable.circle_header_avatar, R.dimen.circle_header_avatar_radius);
                nameTextView.setText(bean.getUserName());
            } else {
                throw new InvalidParameterException("expected CircleHeaderBean class but " + circleItemBean.getClass().getSimpleName());
            }
        }
    }

    static class MessageViewHolder extends BaseViewHolder {
        public interface OnMessageClickedListener {
            /**
             * 点开新消息
             * @param id 新消息id
             */
            void onMessageClicked(long id);
        }

        private final OnMessageClickedListener listener;
        private MessagePanelView messagePanelView;
        public MessageViewHolder(OnMessageClickedListener listener, @NonNull View itemView) {
            super(itemView);
            this.listener = listener;
            messagePanelView = itemView.findViewById(R.id.circle_message);
            messagePanelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CircleMessageBean bean = messagePanelView.getBean();
                    if (null != bean) {
                        Toast.makeText(messagePanelView.getContext(), "onMessage clicked.", Toast.LENGTH_LONG).show();
                        messagePanelView.consume();
                        if (null != listener) {
                            listener.onMessageClicked(bean.getMomentID());
                        }
                    }
                }
            });
        }

        @Override
        public void bind(CircleItemBean bean, int position) {
            if (bean instanceof CircleMessageBean) {
                CircleMessageBean messageBean = (CircleMessageBean) bean;
                messagePanelView.setBean(messageBean);
            } else {
                throw new InvalidParameterException("expected CircleMessageBean class but " + bean.getClass().getSimpleName());
            }
        }
    }
}