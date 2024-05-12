package com.manager.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.manager.appbanhang.model.Item;
import com.manager.appbanhang.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHoler> {
    Context context;
    List<Item> itemList;

    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);

        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        Item item = itemList.get(position);
        holder.txtTen.setText(item.getTensp()+"");
        holder.txtSoluong.setText("Số lượng sản phẩm: "+item.getSoluong());
        //holder.txtGia.setText(" "+item.getGiasp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText("Giá: "+decimalFormat.format(Double.parseDouble(item.getGiasp())) + "VNĐ");
        if (item.getHinhanh().contains("http"))  {
            Glide.with(context).load(item.getHinhanh()).into(holder.imageChitiet);
        }
        else  {
            String hinh = Utils.BASE_URL + "images/" + item.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imageChitiet);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHoler extends RecyclerView.ViewHolder {
        ImageView imageChitiet;
        TextView txtTen,txtSoluong,txtGia;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
            imageChitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtTen = itemView.findViewById(R.id.item_tenspchitiet);
            txtSoluong = itemView.findViewById(R.id.item_soluongchitiet);
            txtGia = itemView.findViewById(R.id.item_giaspchitiet);
        }
    }
}
