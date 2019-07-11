package com.study.recycler_view.view;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.recycler_view.R;

import java.util.List;

/**
 * Created by BJM on 2016-09-13.
 */
public class CalendarYearAdapter extends RecyclerView.Adapter<CalendarYearAdapter.ViewHolder> {
    int mStart;
    int mEnd;
    int mSize;
    int mPosition;
    boolean mIsYear = true;

    public CalendarYearAdapter(int start, int end) {
        mStart = start;
        mEnd = end;
        mSize = mEnd - mStart + 1;
    }

    @Override
    public CalendarYearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendarview_item_calendar_year, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CalendarYearAdapter.ViewHolder holder, int position) {
        String text = mIsYear ? (mStart + position + "년") : (position + 1 + "월");
        holder.tvText.setText(text);
        setSelectedStyle(holder.tvText, mPosition == position);
    }

    @Override
    public void onBindViewHolder(CalendarYearAdapter.ViewHolder holder, int position, List<Object> payloads) {
        // setSelectedPosition() 에서 notifyItemRangeChanged() 호출시에 레이아웃 강조 설정 변경 (버벅이지 않도록 텍스트 내용은 변경하지 않도록 함)
        if (payloads.size() != 0) {
            setSelectedStyle(holder.tvText, mPosition == position);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    /**
     * 레이아웃 강조 설정 변경
     */
    public void setSelectedStyle(TextView textView, boolean selected) {
        textView.setTextColor(selected ? 0xFF25A0FF : 0xFF000000);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selected ? 23 : 17);
    }

    /**
     * 중앙에 위치한 레이아웃은 강조 되도록 설정
     */
    public void setSelectedPosition(int position) {
        if (mPosition != position) {
            int selectedPosition = mPosition;
            mPosition = position;
            if (position > selectedPosition) {
                notifyItemRangeChanged(selectedPosition, position, true);
            } else {
                notifyItemRangeChanged(position, selectedPosition, true);
            }
        }
    }

    public void setYear(boolean isYear) {
        mIsYear = isYear;
        notifyDataSetChanged();
    }

    public boolean isYear() {
        return mIsYear;
    }

    @Override
    public int getItemCount() {
        return mIsYear ? mSize : 12;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvText;

        public ViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tv_txt);
        }
    }
}
