package com.neteasenews.module.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neteasenews.R;
import com.neteasenews.common.util.ImageUtil;
import com.neteasenews.module.news.bean.NewsList;

import java.util.List;

/**
 * @author Yuan
 * @time 2016/7/27  20:59
 * @desc ${TODD}
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<NewsList> mData;

    public RvAdapter(List<NewsList> data) {
        mData = data;
    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_list, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, int position) {
        NewsList detail = mData.get(position);
        ImageUtil.loadImg(holder.icon,detail.getImgsrc());
        holder.title.setText(detail.getTitle());
        String source = detail.getSource();
        if (source != null) {
            holder.souce.setText(source.replace("#", "").replace("$", ""));
        }
        int replyCount = detail.getReplyCount();
        holder.reply.setText(replyCount < 10000 ? replyCount + "跟帖" : String.format("%.1f万跟帖", replyCount / 10000f));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;
        private TextView souce;
        private TextView reply;
        private TextView toTop;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_hot_icon);
            title = (TextView) itemView.findViewById(R.id.tv_hot_title);
            souce = (TextView) itemView.findViewById(R.id.tv_hot_source);
            reply = (TextView) itemView.findViewById(R.id.tv_hot_reply);
            toTop = (TextView) itemView.findViewById(R.id.tv_hot_toTop);
        }
    }
}
