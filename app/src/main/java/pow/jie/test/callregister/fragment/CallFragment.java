package pow.jie.test.callregister.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pow.jie.test.callregister.CallLogInfo;
import pow.jie.test.callregister.R;
import pow.jie.test.callregister.RecyclerAdapter;

public class CallFragment extends Fragment {
    View baseView;
    Context context;
    List<CallLogInfo> callLogInfos=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_call, container, false);
        this.context = this.getActivity();
        RecyclerView recyclerView = baseView.findViewById(R.id.rv_call);
        callLogInfos = readCallLog(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(callLogInfos);
        recyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(new RecyclerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle(callLogInfos.get(position).getName()).setMessage("")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("拨号", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                call(position);
                            }
                        }).setNegativeButton("短信", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                message(position);
                            }
                        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "取消了呢", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alertDialog.show();
            }
        });
        return baseView;
    }

    //读取通话记录
    public static List<CallLogInfo> readCallLog(Context context) {
        ContentResolver resolver = context.getContentResolver();
        List<CallLogInfo> callLogInfos = new ArrayList<>();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{
                CallLog.Calls.CACHED_NAME, // 姓名
                CallLog.Calls.NUMBER, // 号码
                CallLog.Calls.DATE,   // 日期
                CallLog.Calls.TYPE    // 类型：来电、去电、未接
        };
        Cursor cursor = resolver.query
                (uri, projection, "type = ?", new String[]{"2"}, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            callLogInfos.add(new CallLogInfo(name, number, date, type));
        }
        cursor.close();
        return callLogInfos;
    }

    private void call(int position) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            String number = callLogInfos.get(position).getNumber();
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void message(int position) {
        try {
            Uri uri = Uri.parse("smsto:" + callLogInfos.get(position).getNumber());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

}
