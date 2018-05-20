package com.kira.kiralibrary.view.pinnedHeaderListView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

import com.kira.kiralibrary.view.XListView.XListViewFooter;

import java.util.ArrayList;


public class UpLoadPinnedHeaderListView extends ListView implements
		OnScrollListener {

	private OnScrollListener mOnScrollListener;

	public static interface PinnedSectionedHeaderAdapter {
		public boolean isSectionHeader(int position);

		public int getSectionForPosition(int position);

		public View getSectionHeaderView(int section, View convertView,
										 ViewGroup parent);

		public int getSectionHeaderViewType(int section);

		public int getCount();

	}

	private PinnedSectionedHeaderAdapter mAdapter;
	private View mCurrentHeader;
	private int mCurrentHeaderViewType = 0;
	private float mHeaderOffset;
	private boolean mShouldPin = true;
	private int mCurrentSection = 0;
	private int mWidthMode;
	private int mHeightMode;

	private XListViewFooter mFooterView;
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
	private int mScrollBack;
	private final static int SCROLLBACK_FOOTER = 1;
	private final static int SCROLL_DURATION = 400; // scroll back duration
	private Scroller mScroller; // used for scroll back
	private boolean mEnablePullRefresh = true;
	private float mLastY = -1; // save event y
	private int mTotalItemCount;
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull

	// at bottom, trigger
	// load more.

	public UpLoadPinnedHeaderListView(Context context) {
		super(context);
		super.setOnScrollListener(this);
		// init footer view
		mScroller = new Scroller(context, new DecelerateInterpolator());
		mFooterView = new XListViewFooter(context);
	}

	public UpLoadPinnedHeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setOnScrollListener(this);
		// init footer view
		mScroller = new Scroller(context, new DecelerateInterpolator());
		mFooterView = new XListViewFooter(context);
	}

	public UpLoadPinnedHeaderListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		super.setOnScrollListener(this);
		// init footer view
		mScroller = new Scroller(context, new DecelerateInterpolator());
		mFooterView = new XListViewFooter(context);
	}

	public void setPinHeaders(boolean shouldPin) {
		mShouldPin = shouldPin;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		mCurrentHeader = null;
		mAdapter = (PinnedSectionedHeaderAdapter) adapter;
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}

	}

	/**
	 * enable or disable pull up load more feature.
	 * 
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		// setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (loadMoreListener != null) {
			loadMoreListener.OnLoadingMore();
		}
	}

	/**
	 * ���ظ������ݻص��ӿ�
	 */
	public interface OnLoadingMoreLinstener {
		/**
		 * ���ظ������ݻص������������������
		 */
		void OnLoadingMore();
	}

	public OnLoadingMoreLinstener loadMoreListener;

	public void setLoadingMoreListener(OnLoadingMoreLinstener listener) {
		this.loadMoreListener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mEnablePullRefresh || mEnablePullLoad) {
			if (mLastY == -1) {
				mLastY = ev.getRawY();
			}

			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaY = ev.getRawY() - mLastY;
				mLastY = ev.getRawY();
				if (getLastVisiblePosition() == mTotalItemCount - 1
						&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
					// last item, already pulled up or want to pull up.
					updateFooterHeight(-deltaY / OFFSET_RADIO);
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mLastY = -1; // reset
				if (getLastVisiblePosition() == mTotalItemCount - 1) {
					// invoke load more.
					if (mEnablePullLoad
							&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
						startLoadMore();
					}
					resetFooterHeight();
				}
				break;
			default:
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			mFooterView.setBottomMargin(mScroller.getCurrY());
			postInvalidate();

		}
		super.computeScroll();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mTotalItemCount = totalItemCount;
		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}

		if (mAdapter == null || mAdapter.getCount() == 0 || !mShouldPin
				|| (firstVisibleItem < getHeaderViewsCount())) {
			mCurrentHeader = null;
			mHeaderOffset = 0.0f;
			for (int i = firstVisibleItem; i < firstVisibleItem
					+ visibleItemCount; i++) {
				View header = getChildAt(i);
				if (header != null) {
					header.setVisibility(VISIBLE);
				}
			}
			return;
		}

		firstVisibleItem -= getHeaderViewsCount();

		int section = mAdapter.getSectionForPosition(firstVisibleItem);
		int viewType = mAdapter.getSectionHeaderViewType(section);
		mCurrentHeader = getSectionHeaderView(section,
				mCurrentHeaderViewType != viewType ? null : mCurrentHeader);
		ensurePinnedHeaderLayout(mCurrentHeader);
		mCurrentHeaderViewType = viewType;

		mHeaderOffset = 0.0f;

		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
			if (mAdapter.isSectionHeader(i)) {
				View header = getChildAt(i - firstVisibleItem);
				float headerTop = header.getTop();
				float pinnedHeaderHeight = mCurrentHeader.getMeasuredHeight();
				header.setVisibility(VISIBLE);
				if (pinnedHeaderHeight >= headerTop && headerTop > 0) {
					mHeaderOffset = headerTop - header.getHeight();
				} else if (headerTop <= 0) {
					header.setVisibility(INVISIBLE);
				}
			}
		}

		invalidate();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
		// �����ײ����Զ����أ��ж�listview�Ѿ�ֹͣ�������������ӵ���Ŀ����adapter����Ŀ
		if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {

		} else if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
				|| scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

			if (getLastVisiblePosition() == (getCount() - 1)) {
				Log.e("Sticky", "--�϶������--");
				if (loadMoreListener != null) {
					loadMoreListener.OnLoadingMore();
				}
			}
		}

	}

	private View getSectionHeaderView(int section, View oldView) {
		boolean shouldLayout = section != mCurrentSection || oldView == null;

		View view = mAdapter.getSectionHeaderView(section, oldView, this);
		if (shouldLayout) {
			// a new section, thus a new header. We should lay it out again
			ensurePinnedHeaderLayout(view);
			mCurrentSection = section;
		}
		return view;
	}

	private void ensurePinnedHeaderLayout(View header) {
		if (header.isLayoutRequested()) {
			int widthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
					mWidthMode);

			int heightSpec;
			ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
			if (layoutParams != null && layoutParams.height > 0) {
				heightSpec = MeasureSpec.makeMeasureSpec(layoutParams.height,
						MeasureSpec.EXACTLY);
			} else {
				heightSpec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
			}
			header.measure(widthSpec, heightSpec);
			header.layout(0, 0, header.getMeasuredWidth(),
					header.getMeasuredHeight());
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mAdapter == null || !mShouldPin || mCurrentHeader == null)
			return;
		int saveCount = canvas.save();
		canvas.translate(0, mHeaderOffset);
		canvas.clipRect(0, 0, getWidth(), mCurrentHeader.getMeasuredHeight()); // needed
		// for
		// <
		// HONEYCOMB
		mCurrentHeader.draw(canvas);
		canvas.restoreToCount(saveCount);
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
	}

	public void setOnItemClickListener(
			OnItemClickListener listener) {
		super.setOnItemClickListener(listener);
	}

	public static abstract class OnItemClickListener implements
			AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int rawPosition, long id) {
			SectionedBaseAdapter adapter;
			if (adapterView.getAdapter().getClass()
					.equals(HeaderViewListAdapter.class)) {
				HeaderViewListAdapter wrapperAdapter = (HeaderViewListAdapter) adapterView
						.getAdapter();
				adapter = (SectionedBaseAdapter) wrapperAdapter
						.getWrappedAdapter();
			} else {
				adapter = (SectionedBaseAdapter) adapterView.getAdapter();
			}
			int section = adapter.getSectionForPosition(rawPosition);
			int position = adapter.getPositionInSectionForPosition(rawPosition);

			if (position == -1) {
				onSectionClick(adapterView, view, section, id);
			} else {
				onItemClick(adapterView, view, section, position, id);
			}
		}

		public abstract void onItemClick(AdapterView<?> adapterView, View view,
				int section, int position, long id);

		public abstract void onSectionClick(AdapterView<?> adapterView,
				View view, int section, long id);
	}

	private ArrayList<View> mFooterViews;

	@Override
	public void addFooterView(View v) {
		super.addFooterView(v);
		if (mFooterViews == null) {
			mFooterViews = new ArrayList<View>();
		}
		mFooterViews.add(v);
	}

	@Override
	public boolean removeFooterView(View v) {
		if (super.removeFooterView(v)) {
			mFooterViews.remove(v);
			return true;
		}
		return false;
	}

	public View getFooterView() {
		return mFooterView;
	}
}
