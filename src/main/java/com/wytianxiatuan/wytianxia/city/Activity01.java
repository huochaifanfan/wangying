package com.wytianxiatuan.wytianxia.city;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wytianxiatuan.wytianxia.R;
import com.wytianxiatuan.wytianxia.bean.LocationBean;
import com.wytianxiatuan.wytianxia.util.Constants;
import com.wytianxiatuan.wytianxia.util.LocationUtils;
import com.wytianxiatuan.wytianxia.view.base.BaseActivity;
import com.wytianxiatuan.wytianxia.view.base.PermissionsResultListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Activity01 extends BaseActivity implements OnScrollListener, PermissionsResultListener,LocationUtils.LocationResultListener {
	private BaseAdapter adapter;
	private ResultListAdapter resultListAdapter;
	private ListView personList;
	private ListView resultList;
	private TextView overlay;
	private MyLetterListView letterListView; // A-Z listview
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	private Handler handler;
	private OverlayThread overlayThread;
	private ArrayList<City> allCity_lists;
	private ArrayList<City> city_lists;
	private ArrayList<City> city_hot;
	private ArrayList<City> city_result;
	private ArrayList<String> city_history;
	private EditText sh;
	private TextView tv_noresult;
	private String currentCity;
	private int locateProcess = 1;
	private boolean isNeedFresh;
	private DatabaseHelper helper;
	private ImageView imageViewBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		personList = (ListView) findViewById(R.id.list_view);
		allCity_lists = new ArrayList<>();
		city_hot = new ArrayList<>();
		city_result = new ArrayList<>();
		city_history = new ArrayList<>();
		locationCurrentCity();
		resultList = (ListView) findViewById(R.id.search_result);
		sh = (EditText) findViewById(R.id.sh);
		tv_noresult = (TextView) findViewById(R.id.tv_noresult);
		imageViewBack = (ImageView) findViewById(R.id.imageView_back);
		imageViewBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activity01.this.finish();
			}
		});
		helper = new DatabaseHelper(this);
		sh.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString() == null || "".equals(s.toString())) {
					letterListView.setVisibility(View.VISIBLE);
					personList.setVisibility(View.VISIBLE);
					resultList.setVisibility(View.GONE);
					tv_noresult.setVisibility(View.GONE);
				} else {
					city_result.clear();
					letterListView.setVisibility(View.GONE);
					personList.setVisibility(View.GONE);
					getResultCityList(s.toString());
					if (city_result.size() <= 0) {
						tv_noresult.setVisibility(View.VISIBLE);
						resultList.setVisibility(View.GONE);
					} else {
						tv_noresult.setVisibility(View.GONE);
						resultList.setVisibility(View.VISIBLE);
						resultListAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexer = new HashMap<>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		isNeedFresh = true;
		personList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= 2) {
//					Toast.makeText(getApplicationContext(), allCity_lists.get(position-2).getName(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("city", allCity_lists.get(position - 2).getName());
					Activity01.this.setResult(500, intent);
					Activity01.this.finish();
				}
			}
		});
		locateProcess = 1;
		personList.setAdapter(adapter);
		personList.setOnScrollListener(this);
		resultListAdapter = new ResultListAdapter(this, city_result);
		resultList.setAdapter(resultListAdapter);
		resultList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(getApplicationContext(), city_result.get(position).getName(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("city", city_result.get(position).getName());
				Activity01.this.setResult(500, intent);
				Activity01.this.finish();
			}
		});
		initOverlay();
		cityInit();
		hotCityInit();
		hisCityInit();
		setAdapter(allCity_lists, city_hot, city_history);
	}

	/**
	 * 定位当前城市
	 */
	private void locationCurrentCity() {
		performRequestPermissions("网赢天下请求定位权限", new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.ACCESS_FINE_LOCATION}, 200, this);
	}

	public void InsertCity(String name) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from recentcity where name = '"
				+ name + "'", null);
		if (cursor.getCount() > 0) {
			db.delete("recentcity", "name = ?", new String[]{name});
		}
		db.execSQL("insert into recentcity(name, date) values('" + name + "', " + System.currentTimeMillis() + ")");
		db.close();
	}


	private void cityInit() {
//		City city = new City("定位", "0"); // ��ǰ��λ����
//		allCity_lists.add(city);
//		city = new City("最近", "1"); // ������ʵĳ���
//		allCity_lists.add(city);
//		city = new City("热门", "2"); // ���ų���
//		allCity_lists.add(city);
//		city = new City("全部", "3"); // ȫ������
//		allCity_lists.add(city);
		city_lists = getCityList();
		allCity_lists.addAll(city_lists);
	}

	/**
	 * ���ų���
	 */
	public void hotCityInit() {
		City city = new City("北京市", "2");
		city_hot.add(city);
		city = new City("杭州市", "2");
		city_hot.add(city);
		city = new City("广州市", "2");
		city_hot.add(city);
		city = new City("上海市", "2");
		city_hot.add(city);
		city = new City("南京市", "2");
		city_hot.add(city);
		city = new City("深圳市", "2");
		city_hot.add(city);
	}

	private void hisCityInit() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from recentcity order by date desc limit 0, 3", null);
		while (cursor.moveToNext()) {
			city_history.add(cursor.getString(1));
		}
		cursor.close();
		db.close();
	}

	@SuppressWarnings("unchecked")
	private ArrayList<City> getCityList() {
		DBHelper dbHelper = new DBHelper(this);
		ArrayList<City> list = new ArrayList<City>();
		try {
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from city", null);
			City city;
			while (cursor.moveToNext()) {
				city = new City(cursor.getString(1), cursor.getString(2));
				list.add(city);
			}
			cursor.close();
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(list, comparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	private void getResultCityList(String keyword) {
		DBHelper dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery(
					"select * from city where name like \"%" + keyword
							+ "%\" or pinyin like \"%" + keyword + "%\"", null);
			City city;
			Log.e("info", "length = " + cursor.getCount());
			while (cursor.moveToNext()) {
				city = new City(cursor.getString(1), cursor.getString(2));
				city_result.add(city);
			}
			cursor.close();
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(city_result, comparator);
	}

	/**
	 * a-z����
	 */
	@SuppressWarnings("rawtypes")
	Comparator comparator = new Comparator<City>() {
		@Override
		public int compare(City lhs, City rhs) {
			String a = lhs.getPinyi().substring(0, 1);
			String b = rhs.getPinyi().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}
		}
	};

	private void setAdapter(List<City> list, List<City> hotList, List<String> hisCity) {
		adapter = new ListAdapter(this, list, hotList, hisCity);
		personList.setAdapter(adapter);
	}
	private boolean isLocation;
	@Override
	public void onPermissionGranted() {
		isLocation = true;
		if (Constants.currentCity == null) {
			LocationUtils locationUtils = LocationUtils.getInstance(getApplicationContext(), this);
			locationUtils.getLocation();
		}else {
			currentCity = Constants.currentCity;
			if (adapter != null)adapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onDestroy() {
		LocationUtils.getInstance(getApplicationContext(),this).stopLocation();
		super.onDestroy();
	}

	@Override
	public void onPermissionDenied() {
		currentCity = "未定位到当前城市";
		if (adapter != null) adapter.notifyDataSetChanged();
	}

	@Override
	public void locationResult(LocationBean locationBean) {
		if (locationBean != null){
			currentCity = locationBean.getCity();
			if (adapter != null) adapter.notifyDataSetChanged();
		}

	}

	private class ResultListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<City> results = new ArrayList<City>();
		public ResultListAdapter(Context context, ArrayList<City> results) {
			inflater = LayoutInflater.from(context);
			this.results = results;
		}

		@Override
		public int getCount() {
			return results.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.name.setText(results.get(position).getName());
			return convertView;
		}

		class ViewHolder {
			TextView name;
		}
	}

	public class ListAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<City> list;
		private List<City> hotList;
		private List<String> hisCity;
		final int VIEW_TYPE = 3;

		public ListAdapter(Context context, List<City> list, List<City> hotList, List<String> hisCity) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			this.context = context;
			this.hotList = hotList;
			this.hisCity = hisCity;
			alphaIndexer = new HashMap<>();
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = getAlpha(list.get(i).getPinyi());
				// 上一个汉语拼音首字母，如果不存在为" "
				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1)
						.getPinyi()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(list.get(i).getPinyi());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE;
		}

		@Override
		public int getItemViewType(int position) {
			return position < 2 ? position : 2;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		ViewHolder holder;
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TextView city;
			int viewType = getItemViewType(position);
			if (viewType == 0) {
				convertView = inflater.inflate(R.layout.frist_list_item, null);
				city = (TextView) convertView.findViewById(R.id.lng_city);
				city.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isLocation) {
							Intent intent = new Intent();
							intent.putExtra("city", currentCity);
							Activity01.this.setResult(500, intent);
							Activity01.this.finish();
						}
					}
				});
				city.setText(currentCity);
			} else if (viewType == 1) {
				convertView = inflater.inflate(R.layout.recent_city, null);
				GridView hotCity = (GridView) convertView.findViewById(R.id.recent_city);
				hotCity.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent();
						intent.putExtra("city",city_hot.get(position).getName());
						Activity01.this.setResult(500 ,intent);
						Activity01.this.finish();
					}
				});
				hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
				TextView hotHint = (TextView) convertView.findViewById(R.id.recentHint);
				hotHint.setText("热门城市");
			} else if (viewType == 2) {/**城市列表*/
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.list_item, null);
					holder = new ViewHolder();
					holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
					holder.name = (TextView) convertView.findViewById(R.id.name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (position >=2){
					holder.name.setText(list.get(position-2).getName());
					String currentStr = getAlpha(list.get(position-2).getPinyi());
					String previewStr = (position - 3) >= 0 ? getAlpha(list.get(position - 3).getPinyi()) : " ";
					if (!previewStr.equals(currentStr)) {
						holder.alpha.setVisibility(View.VISIBLE);
						holder.alpha.setText(currentStr);
					} else {
						holder.alpha.setVisibility(View.GONE);
					}
				}
			}
			return convertView;
		}
		private class ViewHolder {
			TextView alpha;
			TextView name;
		}
	}
	class HotCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<City> hotCitys;
		public HotCityAdapter(Context context, List<City> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}
		@Override
		public int getCount() {
			return hotCitys.size();
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.hot_city_item, null);
			TextView city = (TextView) convertView.findViewById(R.id.tv_content);
			city.setText(hotCitys.get(position).getName());
			return convertView;
		}
	}
	private boolean mReady;
	/**初始化汉语拼音首字母弹出提示框*/
	private void initOverlay() {
		mReady = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}
	private boolean isScroll = false;
	private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {
		@Override
		public void onTouchingLetterChanged(final String s) {
			isScroll = false;
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position+2);
				overlay.setText(s);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				/**延迟一秒后执行，让overlay为不可见*/
				handler.postDelayed(overlayThread, 1000);
			}
		}
	}

	/**设置overlay不可见*/
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}
	}

	/**获得汉语拼音首字母*/
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		/**正则表达式，判断首字母是否是英文字母*/
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "最近";
		} else if (str.equals("2")) {
			return "热门";
		} else if (str.equals("3")) {
			return "全部";
		} else {
			return "#";
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_TOUCH_SCROLL
				|| scrollState == SCROLL_STATE_FLING) {
			isScroll = true;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (!isScroll) {
			return;
		}
		if (mReady) {
			String text;
			String name = allCity_lists.get(firstVisibleItem).getName();
			String pinyin = allCity_lists.get(firstVisibleItem).getPinyi();
//			if (firstVisibleItem < 4) {
//				text = name;
//			} else {
			text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
//			}
			overlay.setText(text);
			overlay.setVisibility(View.VISIBLE);
			handler.removeCallbacks(overlayThread);
			/**延迟一秒后执行，让overlay为不可见*/
			handler.postDelayed(overlayThread, 1000);
		}
	}
}