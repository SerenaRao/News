package com.neteasenews.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.neteasenews.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * User: yxfang
 * Date: 2016-07-18
 * Time: 09:43
 * ------------- Description -------------
 * YXRecyclerView
 * ---------------------------------------
 */
public class YRecyclerView extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnLoadingListener mLoadingListener;
    private OnItemClickListener mItemClickListener;

    private YXRecyclerViewAdapter mRecyclerViewAdapter;

    private View mHeaderView = new View(getContext());
    private View mFooterView;
    private int mHeaderCount;

    private boolean isRefreshLoading = false;
    private boolean isMoreLoading = false;

    // default Recycler's mode
    private Mode mMode = Mode.BOTH;

    public YRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public YRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setOnTouchListener((v, event) -> isRefreshing());
        addView(mRecyclerView, new LayoutParams(-1, -1));
        setProgressColors(R.color.holo_orange_light, R.color.holo_orange_dark, R.color.holo_purple);
    }

    /**
     * show the loading view
     */
    public void showLoadingView() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set the LayoutManager
     *
     * @param layoutManager
     */
    public YRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    /**
     * return the RecyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public YRecyclerView setMode(@NonNull Mode mode) {
        mMode = mode;
        return this;
    }

    /**
     * set the adapter
     *
     * @param adapter
     */
    public YRecyclerView setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mRecyclerViewAdapter = new YXRecyclerViewAdapter(adapter);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }
        return this;
    }

    public YRecyclerView setProgressColors(int... colors) {
        setColorSchemeResources(colors);
        return this;
    }

    /**
     * add the headerView for the RecyclerView
     *
     * @param view
     */
    public YRecyclerView addHeaderView(View view) {
        mHeaderView = view;
        mHeaderCount++;
        return this;
    }

    /**
     * add the loading more footer when the Mode == Mode.BOTH
     */
    public YRecyclerView addFooterView(View view) {
        if (mMode == Mode.BOTH) {
            mFooterView = view;
            mFooterView.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * set the OnLoadingListener
     *
     * @param listener
     */
    public YRecyclerView setLoadingListener(OnLoadingListener listener) {
        if (listener != null) {
            mLoadingListener = listener;

            setOnRefreshListener(() -> {
                isRefreshLoading = true;
                mLoadingListener.onRefreshLoading();
            });

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && !isRefreshLoading && !isMoreLoading) {
                        int lastVisibleItemPosition;

                        // LinearLayoutManager
                        if (mLayoutManager instanceof LinearLayoutManager) {
                            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                        }
                        // GridLayoutManager
                        else if (mLayoutManager instanceof GridLayoutManager) {
                            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                        }
                        // StaggeredGridLayoutManager
                        else {
                            int[] into = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(into);
                            lastVisibleItemPosition = getLastItemPosition(into);
                        }

                        if (mLayoutManager.getChildCount() > 0
                                && lastVisibleItemPosition >= mLayoutManager.getItemCount() - 2
                                && mLayoutManager.getItemCount() > mLayoutManager.getChildCount()) {
                            if (mFooterView != null && mFooterView.getVisibility() == View.GONE) {
                                mFooterView.setVisibility(View.VISIBLE);
                            }

                            isMoreLoading = true;
                            mLoadingListener.onMoreLoading();
                        }
                    }
                }
            });
        }
        return this;
    }

    public YRecyclerView setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    private int getLastItemPosition(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public boolean isRefreshLoading() {
        return isRefreshLoading;
    }

    /**
     * to call when the data is loaded complete
     */
    public void loadComplete() {
        setRefreshing(false);

        isRefreshLoading = false;
        isMoreLoading = false;

        mRecyclerViewAdapter.notifyDataSetChanged();

        if (mMode == Mode.BOTH) {
            mFooterView.setVisibility(View.GONE);
        }
    }

    /**
     * RecyclerView loading listener
     * onRefreshLoading() is to pull refresh
     * onMoreLoading() is to load more
     */
    public interface OnLoadingListener {
        void onRefreshLoading();

        void onMoreLoading();
    }

    private class YXRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter adapter;

        public static final int TYPE_HEADER = -1;
        public static final int TYPE_ITEM = 0;
        public static final int TYPE_FOOTER = 1;

        public YXRecyclerViewAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeaderView(position) || isFooterView(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeaderView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        /**
         * check the position is headerView
         *
         * @param position
         * @return
         */
        private boolean isHeaderView(int position) {
            return position < mHeaderCount;
        }

        /**
         * check the position is footerView
         *
         * @param position
         * @return
         */
        private boolean isFooterView(int position) {
            return position + mHeaderCount >= getItemCount();
        }

        @Override
        public int getItemViewType(int position) {
            if (isFooterView(position)) {
                return TYPE_FOOTER;
            } else if (isHeaderView(position)) {
                return TYPE_HEADER;
            }
            int adjPosition = position - 1;
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new ViewHolder(mFooterView);
            } else if (viewType == TYPE_HEADER) {
                return new ViewHolder(mHeaderView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (isHeaderView(position)) {
                return;
            }

            final int adjPosition = position - mHeaderCount;
            int adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                holder.itemView.setOnClickListener(v -> {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(null, holder.itemView, adjPosition);
                    }
                });
                adapter.onBindViewHolder(holder, adjPosition);
            }

        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + 2;
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /**
     * RecyclerView Mode
     */
    public enum Mode {
        PULL_TO_REFRESH /*pull refresh*/, BOTH /*pull refresh and load more*/
    }

    /**
     * custom item click listener
     */
    public interface OnItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position);
    }

}
