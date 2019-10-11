package com.ext.attendance.modules.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ext.attendance.R;
import com.ext.attendance.modules.home.interfaces.OnEmployeeAttendanceClickedInterface;
import com.ext.attendance.modules.home.models.CurrentMonthAttendanceResponseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EmployeeAttendanceRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeAttendanceRecyclerViewAdapter.EmployeeViewHolder> {
    private Context context;
    private List<CurrentMonthAttendanceResponseModel.Data> mCurrentMonthDataList;
    public OnEmployeeAttendanceClickedInterface delegate;

    public EmployeeAttendanceRecyclerViewAdapter(Context context, List<CurrentMonthAttendanceResponseModel.Data> dataList) {
        this.context = context;
        this.mCurrentMonthDataList = dataList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_attendance_row_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        final CurrentMonthAttendanceResponseModel dataModel = new CurrentMonthAttendanceResponseModel();
        holder.currentDateTextView.setText("Current Date" + dataModel.getCurrentdate());
        holder.messageTextView.setText(dataModel.getMessage());

        final CurrentMonthAttendanceResponseModel.Data data = mCurrentMonthDataList.get(position);
        holder.checkInTextView.setText(data.getCheckinTime());
        holder.checkOutTextView.setText(data.getCheckoutTime());

        holder.mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onEmployeeAttendanceClicked(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCurrentMonthDataList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCheckIn)
        TextView checkInTextView;
        @BindView(R.id.tvCheckOut)
        TextView checkOutTextView;
        @BindView(R.id.tvCurrentDate)
        TextView currentDateTextView;
        @BindView(R.id.tvMessage)
        TextView messageTextView;
        @BindView(R.id.llMain)
        LinearLayout mainLinearLayout;
        private Unbinder unbinder;


        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(itemView);
        }
    }
}
