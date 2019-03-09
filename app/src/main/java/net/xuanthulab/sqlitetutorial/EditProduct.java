package net.xuanthulab.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProduct extends AppCompatActivity {
    boolean isupdate;
    int idproduct;
    EditText editName;
    EditText editPrice;
    Product product;

    ProductDbHelper productDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productDbHelper = new ProductDbHelper(this);

        Intent intent = getIntent();
        isupdate = intent.getBooleanExtra("isupdate", false);
        if (isupdate) {
            //Activity hoạt động biên tập dữ liệu Sản phẩm đã

            //Đọc sản phẩm

            idproduct = intent.getIntExtra("idproduct", 0);
            product = productDbHelper.getProductByID(idproduct);


            findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productDbHelper.deleteProductByID(idproduct);
                    finish();
                }
            });


        } else {
            //Activity nhâp dữ liệu thêm Sản phẩm mới

            product = new Product(0, "", 0);
            findViewById(R.id.deleteBtn).setVisibility(View.GONE);
            ((Button) findViewById(R.id.save)).setText("Tạo sản phẩm mới");
        }

        //Update to View
        editName = findViewById(R.id.nameproduct);
        editPrice = findViewById(R.id.priceproduct);


        editName.setText(product.name);
        editPrice.setText(product.price + "");

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.name = editName.getText().toString();
                product.price = Integer.parseInt(editPrice.getText().toString());

                if (isupdate) {
                    //Cập nhật
                    productDbHelper.updateProduct(product);
                } else {
                    //Tạo
                    productDbHelper.insertProduct(product);
                }
                finish();
            }
        });


    }
}
