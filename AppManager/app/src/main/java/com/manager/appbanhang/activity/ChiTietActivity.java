package com.manager.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.manager.appbanhang.activity.GioHangActivity;
import com.manager.appbanhang.model.GioHang;
import com.manager.appbanhang.model.SanPhamMoi;
import com.manager.appbanhang.utils.Utils;

import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.paperdb.Paper;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp,giasp,mota;
    Button btnThem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamMoi sanPhamMoi;
    // điếm số lương trong giỏ hàng
    NotificationBadge badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        // đơn hàng
        initControl();// cấu trúc điều khiển
    }
    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themGioHang();
                Paper.book().write("giohang", Utils.manggiohang);
            }
        });
    }
    private void themGioHang() {
        //kiểm tra đơn hàng có lớn hơn 0
        if(Utils.manggiohang.size() > 0) {
            boolean flag = false;
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());// biến cha
            for (int i = 0;i < Utils.manggiohang.size();i++) {
                if (Utils.manggiohang.get(i).getIdsp() == sanPhamMoi.getId()) {
                    Utils.manggiohang.get(i).setSoluong(soluong + Utils.manggiohang.get(i).getSoluong());
                    long gia = Long.parseLong( sanPhamMoi.getGiasp()) * Utils.manggiohang.get(i).getSoluong();//lấy giá tại đó mà nó chọn nhân với
                    // số lượng sản phẩm
                    Utils.manggiohang.get(i).setGiasp(gia);// gán giá sản phẩm được thay đổi
                    flag = true;

                }
            }
            if (flag == false) {
                long gia = Long.parseLong( sanPhamMoi.getGiasp()) * soluong;// lấy giá của sản phẩm nhân với số lượng sản phẩm
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        }
        else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());//lấy số lượng sản phẩm
            //long gia = Long.parseLong( sanPhamMoi.getGiasp()) * soluong;// lấy giá của sản phẩm nhân với số lượng sản phẩm
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(Long.parseLong( sanPhamMoi.getGiasp()));
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }

        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }

    private void initData() {
        // có thể hiểu là lấy thông tin chi tiết của sản phẩm trong màn hình item đổ ra màn hình chuyển đổi
        //màn hình mà click vào thì hiện lên thông tin của sản phẩm
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");//nhận dữ liệu
        tensp.setText(sanPhamMoi.getTensp());
        mota.setText(sanPhamMoi.getMota());
        if(sanPhamMoi.getHinhanh().contains("http")){
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imghinhanh);
        }else {
            String hinh = Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá:" + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())) + " VNĐ");

        //chọn số lượng sản phẩm
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};// số lượng tối đa là 10 sản phẩm
        ArrayAdapter<Integer> adapterspin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spinner.setAdapter(adapterspin);
    }

    // ánh xạ cho giá trị của các biến
    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        imghinhanh = findViewById(R.id.imgchitiet);
        btnThem = findViewById(R.id.btnthemvaogiohang);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toobar);
        badge = findViewById(R.id.menu_sl);
        // chuyển màn hình
        FrameLayout frameLayout = findViewById(R.id.framegiohang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gioHang= new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));// nếu giỏ hàng mà khác null thì số lượng trong đó vẫn hiện thị
        }
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // cập nhật lại số lượng sản phẩm hiện trong giỏ hàng
        if (Utils.manggiohang != null) {
            int totalItem = 0;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));// nếu giỏ hàng mà khác null thì số lượng trong đó vẫn hiện thị
        }
    }
}