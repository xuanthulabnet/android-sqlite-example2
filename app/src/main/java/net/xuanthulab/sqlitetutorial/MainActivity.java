package net.xuanthulab.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int RESULT_PRODUCT_ACTIVITY = 1;
    ArrayList<Product> listProduct;
    ProductListViewAdapter productListViewAdapter;
    ListView listViewProduct;
    ProductDbHelper productDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productDbHelper = new ProductDbHelper(this);


        listProduct = new ArrayList<>();
        loadDbProduct();


        productListViewAdapter = new ProductListViewAdapter(listProduct);
        listViewProduct = findViewById(R.id.listproduct);
        listViewProduct.setAdapter(productListViewAdapter);


        //Thêm dữ liệu
        findViewById(R.id.addbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("isupdate", false);
                intent.setClass(MainActivity.this, EditProduct.class);
                startActivityForResult(intent, RESULT_PRODUCT_ACTIVITY);


            }
        });

        //Lắng nghe bắt sự kiện một phần tử danh sách được chọn, mở Activity để soạn thảo phần tử
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) productListViewAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("isupdate", true);
                intent.putExtra("idproduct", product.productID);
                intent.setClass(MainActivity.this, EditProduct.class);
                startActivityForResult(intent, RESULT_PRODUCT_ACTIVITY);
            }
        });

    }


    private void loadDbProduct() {
        listProduct.clear();
        listProduct.addAll(productDbHelper.getAllProducts());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_PRODUCT_ACTIVITY:
                //Khi đóng Activity EditProduct thì nạp lại dữ liệu
                loadDbProduct();
                productListViewAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }


}
