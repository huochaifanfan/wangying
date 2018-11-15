package com.wytianxiatuan.wytianxia.view.base;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.application.MainApplication;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.bean.WelcomeBean;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.LightStatusBarUtils;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import org.xutils.common.Callback;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class BaseActivity extends AppCompatActivity {
    private String url;
    private ProgressBar progressBar;
    private int leng;
    private File file;
    private InputStream input;
    protected Callback.Cancelable cancelable;
    private PermissionsResultListener mListener;
    private int mRequestCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUiStatus();
    }
    /**请求失败的回调*/
    public void analysisFailer(String info) {
//		if (info != null && !"".equals(info))getToastdialog(this,info);
        getToastdialog(this , Constants.serverError);
        hideLoading();
    }
    public void httpError(String error) {
        getToastdialog(this, Constants.timeOut);
        hideLoading();
    }
    public void getDataFailer(BaseBean bean){
        if (bean != null){
			String info = bean.getInfo();
			if (info != null && !"".equals(info)) getToastdialog(this,info);
        }
        hideLoading();
    }
    public void getToastdialog(Context context,String hintContent) {
        if (context != null) {
            if (hintContent != null && !"".equals(hintContent)) Toast.makeText(context, hintContent, Toast.LENGTH_SHORT).show();
        }
    }
    protected void setUiStatus(){
        LightStatusBarUtils.setLightStatusBar(this,true);
    }
    protected void call(String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
    /**
     * 自定义对话框
     * @param source
     * @param gravity
     * @param backgroundResource
     * @param width
     * @return
     */
    protected Dialog showDialogFrame(int source, int gravity, int backgroundResource, int width){
        if (this != null && !this.isFinishing()){
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(source);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            if (backgroundResource != 0) {
                window.setBackgroundDrawableResource(backgroundResource);
            } else {
                window.setBackgroundDrawable(null);
            }
            window.setGravity(gravity);
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = width;
            window.setAttributes(p);
            if (!this.isFinishing() && dialog != null) dialog.show();
            return dialog;
        }else {
            Log.i("finish","this is finish");
        }
        return null;
    }
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            getFileFromServerAPK(url, progressBar);
        }
    };
    /**
     * 从服务器获取文件
     * @param path
     * @param progressBar
     * @return
     */
    private File getFileFromServerAPK(String path, ProgressBar progressBar) {
        String pathAPK = path;
        try {
            // 创建连接
            getAPKFile0(progressBar, pathAPK);
            //获取用户授权
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = Environment.getExternalStorageDirectory();
                File dirs = dir.getAbsoluteFile();
                // 下载APK
                getAPKPath(progressBar, input, dirs);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this , "内存不足或存储卡没有安装好!" , Toast.LENGTH_SHORT).show();
                        downDialog.dismiss();
                        return;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        } // 进行安装APK
        if (file != null) {
            installApk(file);
        }
        return file;
    }
    /**
     * 网路下载APK;
     * @param progressbar
     * @param input
     * @param dirs
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void getAPKPath(ProgressBar progressbar, InputStream input, File dirs)
            throws Exception {
        file = new File(dirs + "/updata" + Constants.versionCode + ".apk");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(input);
        byte[] buffer = new byte[1024];
        int len;
        int total = 0;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            total += len;
            // 获取当前下载量
            progressbar.setProgress((int) ((total * 1.0 / leng) * 100));
        }
        downDialog.dismiss();
        fos.close();
        bis.close();
        input.close();
    }
    private void installApk(File file) {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = FileProvider.getUriForFile(this,"com.wytianxiatuan.wytianxia",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri,"application/vnd.android.package-archive");
            startActivity(intent);
        }
    }
    /**
     * 创建连接
     * @param progressBar
     * @param pathAPK
     */
    private void getAPKFile0(ProgressBar progressBar, String pathAPK) throws Exception {
        URL url = new URL(pathAPK);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.connect();
        // 获取到文件的大小
        leng = conn.getContentLength();
        progressBar.setMax(100);
        input = conn.getInputStream();
    }

    @Override
    protected void onDestroy() {
        if (cancelable != null && !cancelable.isCancelled()) cancelable.cancel();
        super.onDestroy();
    }
    protected Dialog msgDialog;
    protected TextView textToast;
    private ImageView imageViewDialog;
    protected String capther;
    /**
     * 显示验证码的弹框
     */
    protected void showMsgDialog(){
        msgDialog = showDialogFrame(R.layout.verfily_msg_dialog , Gravity.CENTER , R.drawable.dialog_background, UiUtils.dipToPx(this ,310));
        msgDialog.setCanceledOnTouchOutside(false);
        final EditText etMsg = (EditText) msgDialog.findViewById(R.id.et_msg);
        final TextView textCancel = (TextView) msgDialog.findViewById(R.id.text_cancel);
        textToast = (TextView) msgDialog.findViewById(R.id.text_toast);
        imageViewDialog = (ImageView) msgDialog.findViewById(R.id.image);
        final TextView btnSure = (TextView) msgDialog.findViewById(R.id.button_sure);
        Glide.with(this).load(Constants.captcha+ System.currentTimeMillis()).into(imageViewDialog);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msgDialog != null) {
                    msgDialog.cancel();
                }
            }
        });
        imageViewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClick();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etMsg.getText())){
                    capther = etMsg.getText().toString();
                    msgDialogClick();
                }else {
                    getToastdialog(BaseActivity.this,"请输入验证码");
                }
            }
        });
    }
    protected void imageClick(){
        Glide.with(BaseActivity.this).load(Constants.captcha+ System.currentTimeMillis()).into(imageViewDialog);
    }
    protected void msgDialogClick(){}
    /**
     *
     * @param descrption 首次权限申请被用户绝绝后的提示
     * @param perssions 要申请的权限数组
     * @param requestCode 请求码
     * @param listener 实现的接口
     */
    protected void performRequestPermissions(String descrption , String[] perssions , int requestCode ,
                                             PermissionsResultListener listener){
        if (perssions == null || perssions.length == 0){
            return;
        }
        this.mRequestCode = requestCode;
        this.mListener = listener;
        /**检查版本*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkEachSelfPermission(perssions)){
                /**没有权限 申请权限*/
                requestEachPermissions(descrption , perssions , requestCode);
            }else{
                /**已经获取了权限*/
                if (listener != null){
                    listener.onPermissionGranted();
                }
            }
        }else {
            listener.onPermissionGranted();
        }
    }

    /**
     * 检查每个权限是否申请
     * @param permissions
     * @return true 需要申请权限  false 不要申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions){
        for (String perssion:permissions){
            if (ContextCompat.checkSelfPermission(this , perssion)!= PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
    /**
     * 权限申请 申请之前判断是否需要再次申请权限
     * @param des
     * @param perssions
     * @param requestCode
     */
    private void requestEachPermissions(String des , String[] perssions,int requestCode){
        if (shouldShowRequestPermissionRationale(perssions)){
            /**需要再次进行权限的申请  弹出权限申请的对话框*/
            showRationaleDialog(des , perssions , requestCode);
        }else {
            /**第二次返回false时候是用户选择了不再提示*/
            if (hasInvitePerssion()){
                settingPerssion();
            }else {
                ActivityCompat.requestPermissions(this , perssions , requestCode);
            }
        }
    }
    private boolean hasInvitePerssion(){
        SharedPreferences sp = getSharedPreferences("perssion",Context.MODE_PRIVATE);
        boolean hasInvite;
        hasInvite = sp.getBoolean("perssion",false);
        return hasInvite;
    }
    /**
     * 弹出权限申请的对话框
     * @param des
     * @param perssions
     * @param requestCode
     */
    private void showRationaleDialog(String des , final String[] perssions , final int requestCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("权限").setMessage(des).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(BaseActivity.this , perssions ,requestCode);
                setPermissinAgain();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).show();
    }
    private void setPermissinAgain(){
        SharedPreferences sp = getSharedPreferences("perssion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("perssion",true);
        editor.commit();
    }
    /**
     * 再次用到权限时候判断是否需要再次申请权限
     * @param perssions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] perssions){
        for (String perssion:perssions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this , perssion)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查权限处理的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode){
            if (checkEachPermissionsGranted(grantResults)){
                /**权限被授予*/
                if (mListener != null){
                    mListener.onPermissionGranted();
                }
            }else {
                /**用户拒绝了权限*/
                mListener.onPermissionDenied();
            }
        }
    }
    /**
     * 检查回调结果
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void settingPerssion(){
        final Dialog dialog = showDialogFrame(R.layout.perssion_dialog, Gravity.CENTER,R.drawable.dialog_background, UiUtils.dipToPx(this , 295));
        ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                if (dialog != null) dialog.cancel();
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) dialog.cancel();
            }
        });
    }
    protected Dialog dialog;
    private GifImageView imageLoad;
    private GifDrawable gifDrawable;
    public void showLoading(Context context){
        dialog = showDialogFrame(R.layout.loadind_dialog,Gravity.CENTER,0,UiUtils.dipToPx(this ,60));
        dialog.setCanceledOnTouchOutside(false);
        imageLoad = (GifImageView) dialog.findViewById(R.id.gif_image);
        try {
            gifDrawable = new GifDrawable(getResources() , R.drawable.loading);
            imageLoad.setImageDrawable(gifDrawable);
        }catch (Exception e){}
        Window window = dialog.getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.dimAmount = 0;
        window.setAttributes(p);
    }
    public void hideLoading(){
        if (dialog != null){
            dialog.dismiss();
            imageLoad = null;
            gifDrawable = null;
        }
    }
    /**
     * 显示更新的状态
     */
    protected void showLoginDialog(String url){
        this.url = url;
        final WelcomeBean entity = Constants.welcomeBean;
        final Dialog upDateDialog = showDialogFrame(R.layout.new_version_dialog,Gravity.CENTER,0,UiUtils.dipToPx(this,335));
        upDateDialog.setCanceledOnTouchOutside(false);
        upDateDialog.setCancelable(false);
        TextView tvVersion = (TextView) upDateDialog.findViewById(R.id.tv_newVersion);
        TextView tvVersionSize = (TextView) upDateDialog.findViewById(R.id.tv_newVersionSize);
        TextView tvContent = (TextView) upDateDialog.findViewById(R.id.tv_content);
        Button btnUpdate = (Button) upDateDialog.findViewById(R.id.btn_update);
        TextView tvCancel = (TextView) upDateDialog.findViewById(R.id.tv_cancel);
        if (entity != null){
            tvVersion.setText("最新版本：V"+entity.getVersionCode());
            tvVersionSize.setText("最新版本大小："+entity.getSize());
            tvContent.setText(entity.getUpdateContent());
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**获取内存权限*/
                performRequestPermissions("更新版本需要读写您的内存权限", new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200, new PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted() {
                        showUpDialog();
                    }
                    @Override
                    public void onPermissionDenied() {
                        getToastdialog(BaseActivity.this ,"权限拒绝，请检查相关权限设置");
                    }
                });
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity!= null && "1".equals(entity.getIsForce())){
                    /**这是强制更新*/
                    if (upDateDialog != null && upDateDialog.isShowing()){
                        upDateDialog.cancel();
                    }
                    MainApplication.exitApp();
                }else {
                    if (upDateDialog != null && upDateDialog.isShowing()){
                        upDateDialog.cancel();
                    }
                }
            }
        });
        if (upDateDialog != null && !this.isFinishing()) upDateDialog.show();
    }
    private Dialog downDialog;
    /**
     * 下载数据
     */
    private void showUpDialog(){
        downDialog = showDialogFrame(R.layout.version_downloads,Gravity.CENTER ,R.drawable.dialog_background,UiUtils.dipToPx(this,300));
        downDialog.setCanceledOnTouchOutside(false);
        downDialog.setCancelable(false);
        progressBar = (ProgressBar) downDialog.findViewById(R.id.customer_progress);
        Constants.pool.submit(updateRunnable);
    }
}
