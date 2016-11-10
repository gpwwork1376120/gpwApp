package gpw.com.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.File;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.util.BitmapUtil;
import gpw.com.app.view.CircleImageView;
import gpw.com.app.view.SelectPicPopupWindow;

public class PersonalInfoActivity extends BaseActivity {

    private RelativeLayout rl_address;
    private RelativeLayout rl_head;
    private CircleImageView civ_head;
    private CircleImageView civ_head1;
    private TextView tv_tel;
    private TextView tv_tel1;
    private TextView tv_name;
    private TextView tv_name1;
    private TextView tv_sex;
    private ImageView iv_left_white;
    private SelectPicPopupWindow menuWindow;
    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    // 图片裁切标记

    private static final String IMAGE_FILE_NAME = "head.jpg";// 头像文件名称

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void findById() {
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        civ_head = (CircleImageView) findViewById(R.id.civ_head);
        civ_head1 = (CircleImageView) findViewById(R.id.civ_head1);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_tel1 = (TextView) findViewById(R.id.tv_tel1);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        iv_left_white = (ImageView) findViewById(R.id.iv_left_white);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        iv_left_white.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.rl_address:
                break;
            case R.id.rl_head:
                menuWindow = new SelectPicPopupWindow(PersonalInfoActivity.this, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.activity_personal_info), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    if (ContextCompat.checkSelfPermission(PersonalInfoActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PersonalInfoActivity.this,
                                new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    } else {
                        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //下面这句指定调用相机拍照后的照片存储的路径
                        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                        startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    }

                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    if (ContextCompat.checkSelfPermission(PersonalInfoActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PersonalInfoActivity.this,
                                new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    } else {
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 200: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 300: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getServletData();
                } else {
                    Toast.makeText(PersonalInfoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        String filename = BitmapUtil.getPath(this, uri);
        assert filename != null;
        uri = Uri.fromFile(new File(filename));
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);// 保留比例
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        System.out.println("start intent");
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri uri;
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                if (data == null) {
                    return;
                }
                uri = data.getData();
                if (uri != null) {
                    startPhotoZoom(uri);
                }

                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                uri = Uri.fromFile(temp);

                System.out.println(uri);
                if (uri != null) {
                    startPhotoZoom(uri);
                }

                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data == null) {
                    return;
                } else {
                    setPicToView(data);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setPicToView(Intent data) {

        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, bitmap);
            civ_head.setImageDrawable(drawable);
        }

    }
}
