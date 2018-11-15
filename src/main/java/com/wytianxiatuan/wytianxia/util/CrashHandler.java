package com.wytianxiatuan.wytianxia.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 捕捉未处理的异常事件
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler{
	// CrashHandler 实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的 Context 对象
	private Context mContext;
	// 系统默认的 UncaughtException 处理类
	private UncaughtExceptionHandler mHandler;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	private StringBuffer sb = new StringBuffer();
	/** 保证只有一个 CrashHandler 实例 */
	private CrashHandler() {
	}
	/** 获取 CrashHandler 实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}
	/**
	 * 初始化
	 * @param context
	 */
	public void init(Context context){
		this.mContext = context;
		// 获取系统默认的 UncaughtException 处理器
		mHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该 CrashHandler 为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 退出程序,注释下面的重启启动程序代码
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
			// 重新启动程序，注释上面的退出程序
//			Intent intent = new Intent();
//			intent.setClass(mContext, MainActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
//			mContext.startActivity(intent);
		}
	}
	/**
	 * 用户是否自己处理了异常
	 * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
	 * @param ex
	 * @return 如果处理了该异常信息；否则返回 false
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		//显示异常信息
		new Thread(){
			public void run() {
				Looper.prepare();
				if (mContext != null) {
					Toast.makeText(mContext, "很抱歉，程序异常，即将退出", Toast.LENGTH_LONG).show();
				}
				Looper.loop();
			}
		}.start();
		//收集设备参数信息
		collectDeviceInfo(mContext);
		// 将错误信息保存日志文件
		saveCrashInfo2File(ex);
		//将错误的信息自动发送邮件
		sendMail();
		return true;
	}
	/**
	 * 将错误信息保存到文件
	 * @param ex
	 * @reutrn 返回文件名称,便于将文件传送到服务器
	 */
	@SuppressLint("SdCardPath")
	private String saveCrashInfo2File(Throwable ex) {
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" +value +"\n");
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while(cause != null){
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String time = formatter.format(new Date());
			String fileName = "crash-" + time + ".txt";
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = "/sdcard/wytianxia/crash/";
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 搜集设备信息
	 * @param context
	 */
	private void collectDeviceInfo(Context context) {
		PackageManager manager = context.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo
					(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (info != null) {
				String versionName = info.versionName == null ?"null":info.versionName;
				String versionCode = info.versionCode+"";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				infos.put(field.getName(), field.get(null).toString());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 自动发送邮件
	 * 该功能待定
	 */
	private void sendMail(){
		new Runnable() {
			@Override
			public void run() {
				String time = formatter.format(new Date());
				sb.append("\n"+"出错时间："+time);
			}
		};
	}
}
