package com.wolf.android.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;

/**
 * <p>Description: 程序崩溃处理类</p>
 * Created by wzd on 2016/10/31.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * 日志文件文件名 ,当此文件超过3M时，将重新写入压缩文件
     */
    public static final String LOG_FILE_NAME = "crash.log";
    /**
     * 日志压缩文件文件名，即使日志源文件生成后的文件
     */
    public static final String ZIP_LOG_FILE_NAME = "crash.zip";
    /**
     * 日志文件最大为3M
     */
    public static final int MAX_LOG_FILE_SIZE = 3 * 1024 * 1024;
    /**
     * Debug Log tag
     */
    public static final String TAG = "CrashHandler";
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    @SuppressWarnings("unused")
    private static final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    static Boolean Lock = false;
    static String msg;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx 当前环境上下文对象
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log4JUtil.error("uncaught exeption!!!");
        handleException(ex);
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
            Log4JUtil.info("system handle...");
        } else {
            // Sleep一会后结束程序
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log4JUtil.info("kill Process...");
            // TODO 崩溃后程序处理
//            Intent intent = new Intent(
//                    GlobalData.globalContext.getApplicationContext(),
//                    SplashActivity.class);
//            PendingIntent restartIntent = PendingIntent.getActivity(
//                    GlobalData.globalContext.getApplicationContext(), 0,
//                    intent, Intent.FLAG_ACTIVITY_NEW_TASK);
//            // 退出程序
//            AlarmManager mgr = (AlarmManager) GlobalData.globalContext
//                    .getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//                    restartIntent); // 1秒钟后重启应用
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 保存奔溃日志,若日志文件小于1M直接保存，否则将现有的日志保存到备份日志文件，将新日志写到原日志文件
     *
     * @param s 奔溃信息
     */
    private static void dumpString(String s, Context context) {
        try {
            boolean flag = true; // 追回写入文件
            Log4JUtil.warn("正在保存崩溃日志文件...");
            Log4JUtil.warn(s);
            String path = FileUtil.getPath_Space(context);
            if (path != null) {
                File zipFile = new File(path + File.separator
                        + ZIP_LOG_FILE_NAME);
                long fileSize = FileUtil.getFileSizes(zipFile);
                byte[] bytes = (new Date() + "\r\n" + s + "\r\n----------------------------------------\r\n\r\n")
                        .getBytes();
                // 向原日志文件中写入日志
                if (fileSize + bytes.length > MAX_LOG_FILE_SIZE) {
                    flag = false; // 原日志文件改为覆盖写入
                }
                if (FileUtil.writeSDCardByBytes(bytes,
                        new SevenZipCompressAdapter(), flag, path,
                        ZIP_LOG_FILE_NAME) < 1) {
                    Log4JUtil.error("写入崩溃日志失败...");
                } else {
                    Log4JUtil.warn("成功写入崩溃日志...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex 异常对象
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        ex.printStackTrace();
        msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // ZzyUtil.showToast(mContext, "程序出错啦:" + msg, false);
                // ToastUtil.showShortToast(mContext, "很抱歉,程序出现异常,即将退出.");
                dumpString(AppHelper.dumpThrowable(ex), mContext);
                Looper.loop();
                Lock = false;
            }

        }.start();
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx 当前环境上下文对象
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(cr);
                cr.delete();// 删除已发送的报告
            }
        }
    }

    private void postReport(File file) {
        // 使用HTTP Post 发送错误报告到服务器
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
        // 教程来提交错误报告
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx 当前环境上下文对象
     * @return 错误报告文件名数组
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx 当前环境上下文对象
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
