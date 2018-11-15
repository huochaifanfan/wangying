package com.wytianxiatuan.wytianxia.view.base;

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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.BaseBean;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.UiUtils;

import org.xutils.common.Callback;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class BaseFragment extends BaseLazyFragment{
    protected Callback.Cancelable cancelable;
    private PermissionsResultListener mListener;
    protected int mRequestCode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**请求失败的回调*/
    public void analysisFailer(String info) {
//		if (info != null && !"".equals(info))getToastdialog(getActivity(),info);
        getToastdialog(getActivity(),Constants.serverError);
        hideLoading();
    }
    public void httpError(String error) {
        getToastdialog(getActivity(), Constants.timeOut);
        hideLoading();
    }
    public void getDataFailer(BaseBean bean){
        if (bean != null){
			String info = bean.getInfo();
			if (info != null && !"".equals(info)) getToastdialog(getActivity(),info);
//            wholeJudge(bean.getStatus(),bean.getInfo());
        }
        hideLoading();
    }
    public void getToastdialog(Context context, String hintContent) {
        if (context != null) {
            if (hintContent != null && !"".equals(hintContent)) Toast.makeText(context, hintContent, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        if (cancelable != null && !cancelable.isCancelled()) cancelable.cancel();
        super.onDestroy();
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
        if (getActivity() != null && ! getActivity().isFinishing()){
            Dialog dialog = new Dialog(getActivity());
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
            if (dialog != null) dialog.show();
            return dialog;
        }else {
            Log.i("finish","this is finish");
        }
        return null;
    }
    /**
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
            if (ContextCompat.checkSelfPermission(getActivity() , perssion)!= PackageManager.PERMISSION_GRANTED){
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
            if (hasInvitePerssion()){
                /**用户点击了拒绝不再提示 提示用户去设置界面打开权限*/
                settingPerssion();
            }else {
                BaseFragment.this.requestPermissions(perssions , requestCode);
            }
        }
    }
    private void settingPerssion(){
        final Dialog dialog = showDialogFrame(R.layout.perssion_dialog, Gravity.CENTER,R.drawable.dialog_background, UiUtils.dipToPx(getActivity() , 295));
        ImageView imageClose = (ImageView) dialog.findViewById(R.id.image_close);
        Button btnSure = (Button) dialog.findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), null);
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
    private boolean hasInvitePerssion(){
        SharedPreferences sp = getActivity().getSharedPreferences("perssion",Context.MODE_PRIVATE);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("权限").setMessage(des).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseFragment.this.requestPermissions(perssions ,requestCode);
                /**记录用户是否再次申请过权限*/
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
        SharedPreferences sp = getActivity().getSharedPreferences("perssion",Context.MODE_PRIVATE);
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity() , perssion)){
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
     *
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
    protected void call(String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }
    private Dialog dialog;
    private GifImageView imageLoad;
    private GifDrawable gifDrawable;
    public void showLoading(Context context){
        dialog = showDialogFrame(R.layout.loadind_dialog,Gravity.CENTER,0,UiUtils.dipToPx(context ,60));
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
}
