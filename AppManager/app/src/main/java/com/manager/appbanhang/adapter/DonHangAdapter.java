package com.manager.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.manager.appbanhang.Interface.ItemClickListener;
import com.manager.appbanhang.model.DonHang;
import com.manager.appbanhang.model.EventBus.DonHangEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> list;

    public DonHangAdapter(Context context, List<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DonHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.txtdonhang.setText("Đơn hàng: " +donHang.getId());
        holder.trangthai.setText(trangThaiDon(donHang.getTrangthai()));
        holder.diachi.setText("Địa chỉ: "+donHang.getDiachi());
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rcChitiet.getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());

        //adapter
        ChiTietAdapter chiTietAdapter  = new ChiTietAdapter(context,donHang.getItem());
        holder.rcChitiet.setLayoutManager(layoutManager);
        holder.rcChitiet.setAdapter(chiTietAdapter);
        holder.rcChitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick){
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        });



    }

    private  String trangThaiDon(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lý";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đã hủy";
                break;
        }

        return result;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang, trangthai, diachi;
        RecyclerView rcChitiet;
        ItemClickListener listener;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            rcChitiet = itemView.findViewById(R.id.recycleview_chitiet);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view, getAdapterPosition(),true);
            return false;
        }
    }
}
