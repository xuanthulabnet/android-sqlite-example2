package net.xuanthulab.sqlitetutorial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "ProductDbHelper";
    private static final String DATABASE_NAME = "myproduct.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";


    public ProductDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này tự động gọi nếu storage chưa có DATABASE_NAME
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR (255) NOT NULL, " +
                "price DECIMAL DEFAULT (0)" +
                ")";

        db.execSQL(queryCreateTable);
    }

    //Phương thức này tự động gọi khi đã có DB trên Storage, nhưng phiên bản khác
    //với DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }


    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, price from product", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int productID = cursor.getInt(0);
            String productName = cursor.getString(1);
            int productPrice = cursor.getInt(2);

            products.add(new Product(productID, productName, productPrice));
            cursor.moveToNext();
        }

        cursor.close();

        return products;
    }


    public Product getProductByID(int ID) {
        Product product = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, price from product where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int productID = cursor.getInt(0);
            String productName = cursor.getString(1);
            int productPrice = cursor.getInt(2);
            product = new Product(productID, productName, productPrice);
        }
        cursor.close();
        return product;
    }

    void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE product SET name=?, price = ? where id = ?",
                new String[]{product.name, product.price + "", product.productID + ""});
    }

    void insertProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO product (name, price ) VALUES (?,?)",
                new String[]{product.name, product.price + ""});
    }

    void deleteProductByID(int ProductID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM product where id = ?", new String[]{String.valueOf(ProductID)});
    }


}
