package com.study.recycler_view.view;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by BJM on 2016-09-13.
 */
public abstract class JmItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {
    static final int NO_POSITION = -1;

    GestureDetector mGestureDetector;
    RecyclerView mRecyclerView;

    private int mTouchSlop;
    private float mPressDownX;                      // item 터치시 시작 x좌표
    private float mPressDownY;                      // item 터치시 시작 y좌표
    private int mPressDownPosition = NO_POSITION;      // item 터치시 시작 position
    private boolean mIsLongClick;

    public JmItemTouchListener() {
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e) {
        if (mRecyclerView == null) {
            initRecyclerView(recyclerView);
        }

        mGestureDetector.onTouchEvent(e);
        applyPressState(e);
        return false;
    }

    /**
     * RecyclerView 초기화
     */
    void initRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;

        // notification 시 View가 바뀌어도 pressed 상태를 유지시킴
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                refreshItemPressed();
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
            }
        });

        // 롱클릭 감지
        mGestureDetector = new GestureDetector(mRecyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {

                // 롱클릭 listener 실행
                if (mPressDownPosition != NO_POSITION) {
                    mIsLongClick = true;

                    int position = mPressDownPosition;
                    setItemPressed(position, false); // 롱클릭전 pressed 비활성화, 내부에서 mPressDownPosition이 변경되기 때문에 저장해서 사용
                    onLongClick(position);
                }
            }
        });

        mTouchSlop = ViewConfiguration.get(mRecyclerView.getContext()).getScaledTouchSlop();
    }

    /**
     * 터치 아이템의 pressed 상태 변경
     */
    void applyPressState(MotionEvent e) {

        int action = e.getAction();
        int position = getTouchItemPosition(e);
        if (position >= 0) {

            // 터치시
            if (action == MotionEvent.ACTION_DOWN) {

                boolean isScrolling = mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING;

                // 클릭 가능한 위치 (자식뷰가 없는곳을 터치 했을때), 스크롤중이 아닐때
                if (isClickableTouchPosition(e) && !isScrolling) {

                    // 초기화
                    mPressDownX = e.getRawX();
                    mPressDownY = e.getRawY();
                    mIsLongClick = false;

                    // 아이템 pressed true 로 변경
                    setItemPressed(position, true);
                }
            }

            // 터치상태가 아닐때 종료
            else if (mPressDownPosition == NO_POSITION) {
                return;
            }

            // 터치 이동시
            else if (action == MotionEvent.ACTION_MOVE) {

                int moveX = (int) Math.abs(mPressDownX - e.getRawX());
                int moveY = (int) Math.abs(mPressDownY - e.getRawY());
                boolean isSamePosition = mPressDownPosition == position;

                // 터치 item 위치가 변경되었거나 touchSlop 이상 이동했을때
                boolean isMoved = !isSamePosition || moveY > mTouchSlop || moveX > mTouchSlop;
                if (isMoved) {

                    // 아이템 pressed false 로 변경
                    setItemPressed(mPressDownPosition, false);
                }
            }

            // 터치 해제시
            else if (action == MotionEvent.ACTION_UP) {

                // 롱클릭 미 호출시에만 클릭 실행
                if (!mIsLongClick) {
                    onClick(mPressDownPosition);
                    mRecyclerView.playSoundEffect(SoundEffectConstants.CLICK);
                }

                // 아이템 pressed false 로 변경
                setItemPressed(mPressDownPosition, false);
            }

            // 기타 상황시
            else {
                // 아이템 pressed false 로 변경
                setItemPressed(mPressDownPosition, false);
            }
        }
    }

    /**
     * 해당 위치의 아이템의 pressed 상태변화
     */
    void setItemPressed(int position, boolean isPressed) {
        View pressedView = mRecyclerView.getLayoutManager().findViewByPosition(position);
        if (pressedView != null) {
            pressedView.setPressed(isPressed);
        }

        mPressDownPosition = isPressed ? position : NO_POSITION;
    }

    /**
     * 저장된 position을 이용해 pressed 상태 동기화
     */
    void refreshItemPressed() {
        if (mPressDownPosition == NO_POSITION) {
            return;
        }

        RecyclerView.LayoutManager lm = mRecyclerView.getLayoutManager();
        int childCount = lm.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = lm.getChildAt(i);
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(childView);
            if (holder != null) {
                boolean isPressed = holder.getAdapterPosition() == mPressDownPosition;
                childView.setPressed(isPressed);
            }
        }
    }

    /**
     * 터치된 위치의 item view 반환
     */
    View getTouchItemView(MotionEvent e) {
        return mRecyclerView.findChildViewUnder(e.getX(), e.getY());
    }

    /**
     * 클릭가능한 view 인지 확인. 버전별 분기처리
     */
    boolean isClickable(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return v.hasOnClickListeners();
        } else {
            return v.isClickable();
        }
    }

    /**
     * 터치된 위치의 item position 반환
     */
    int getTouchItemPosition(MotionEvent e) {
        return mRecyclerView.getChildAdapterPosition(getTouchItemView(e));
    }

    /**
     * 클릭가능한 item view 인지 반환 (자식뷰중 터치된 item이 없는지)
     */
    boolean isClickableTouchPosition(MotionEvent e) {
        View touchItemView = getTouchItemView(e);
        return !isClickable(touchItemView) && !hasTouchedChildView(touchItemView, (int) e.getRawX(), (int) e.getRawY());
    }

    /**
     * 자식뷰중 터치된 item이 있는지 확인
     */
    boolean hasTouchedChildView(View view, int touchX, int touchY) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int cnt = vg.getChildCount();
            for (int i = 0; i < cnt; i++) {
                View childView = vg.getChildAt(i);
                if (isClickable(childView) && childView.isShown() && isTouched(childView, touchX, touchY) || hasTouchedChildView(childView, touchX, touchY)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 뷰가 터치상태인지 확인
     */
    boolean isTouched(View view, int touchX, int touchY) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int right = left + view.getWidth();
        int top = location[1];
        int bottom = top + view.getHeight();

        return touchX >= left && touchX <= right && touchY >= top && touchY <= bottom;
    }

    public abstract void onClick(int position);

    public void onLongClick(int position) {
    }
}
