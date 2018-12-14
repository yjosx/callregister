package pow.jie.test.callregister;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<CallLogInfo> myRecords;
    private MyItemClickListener itemOnClickListener;

    public RecyclerAdapter(List<CallLogInfo> myCallRecords) {
        this.myRecords = myCallRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.phone_info, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, itemOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CallLogInfo callRecords = myRecords.get(position);
        if (callRecords.getName() == null)
            holder.name.setText(callRecords.getNumber());
        else
            holder.name.setText(callRecords.getName());
        holder.time.setText(callRecords.getDate());
    }

    @Override
    public int getItemCount() {
        return myRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, time;
        private MyItemClickListener myItemClickListener;

        ViewHolder(@NonNull View itemView, MyItemClickListener myItemClickListener) {
            super(itemView);
            this.myItemClickListener = myItemClickListener;
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tv_number);
            time = itemView.findViewById(R.id.tv_time);
        }

        @Override

        public void onClick(View v) {
            if (myItemClickListener != null) {
                myItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setItemOnClickListener(MyItemClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }
}
