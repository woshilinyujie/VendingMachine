package com.hangzhou.hezi.vendingmachine.MUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;
import org.java_websocket.WebSocket;

import rx.functions.Action1;
import ua.naiksoftware.stomp.LifecycleEvent;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.client.StompClient;
import ua.naiksoftware.stomp.client.StompMessage;

/**
 * Created by Administrator on 2019/3/24.
 */

public class WsManager {
    private static WsManager mInstance;
    private final String TAG = "Socket";

    /**
     * WebSocket config
     */
    private Context context;
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String DEF_URL = "ws://101.37.246.24:10109/websocket";

    private WsStatus mStatus;
    private boolean isDown=false;
    private StompClient mStompClient;

    private WsManager() {
    }

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    mInstance = new WsManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.context=context;
        createStompClient();
        registerStompTopic();
    }


    private void createStompClient() {
        mStompClient = Stomp.over(WebSocket.class, DEF_URL);
        mStompClient.connect();
        mStompClient.lifecycle().subscribe(new WsAction());
    }
    //订阅消息
    private void registerStompTopic() {
        String id = PrefUtils.getString(context, "id", "");
        mStompClient.topic("/queue/message/"+id).subscribe(new Action1<StompMessage>() {
            @Override
            public void call(StompMessage stompMessage) {
                Log.e(TAG, "call: " + stompMessage.getPayload());
                String s = stompMessage.getPayload().toString();
                EventBus.getDefault().post("socket-"+s);
            }
        });

    }


    class  WsAction implements Action1<LifecycleEvent> {

        @Override
        public void call(LifecycleEvent lifecycleEvent) {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.e(TAG, "连接成功");
                    setStatus(WsStatus.CONNECT_SUCCESS);
                    cancelReconnect();//连接成功的时候取消重连,初始化连接次数
                    break;

                case ERROR:
                    Log.e(TAG, "连接错误");
                    setStatus(WsStatus.CONNECT_FAIL);
                    break;
                case CLOSED:
                    Log.e(TAG, "断开连接");
                    if(!isDown){
                        setStatus(WsStatus.CONNECT_FAIL);
                        reconnect();//连接断开的时候调用重连方法
                    }
                    break;
            }
        }
    }


    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    private WsStatus getStatus() {
        return mStatus;
    }

    public void disconnect(boolean b) {
        isDown=b;
        if (mStompClient != null)
            mStompClient.disconnect();
    }

    public enum WsStatus {
        CONNECT_SUCCESS,//连接成功
        CONNECT_FAIL,//连接失败
        CONNECTING;//正在连接
    }


    private Handler mHandler = new Handler();

    private int reconnectCount = 0;//重连次数
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔


    public void reconnect() {
        if (!isNetConnect()) {
            reconnectCount = 0;
            Log.e(TAG, "重连失败网络不可用");
            return;
        }

        if (mStompClient != null &&

                getStatus() != WsStatus.CONNECTING) {//不是正在重连状态

            reconnectCount++;
            setStatus(WsStatus.CONNECTING);

            long reconnectTime = minInterval;
            if (reconnectCount > 3) {
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }
            Log.e(TAG, "准备开始第"+reconnectCount+"次重连,重连间隔"+reconnectTime+" -- url:"+DEF_URL);
            mHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }


    private Runnable mReconnectTask = new Runnable() {

        @Override
        public void run() {
            try {
                createStompClient();
                registerStompTopic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void cancelReconnect() {
        reconnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }


    private boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
