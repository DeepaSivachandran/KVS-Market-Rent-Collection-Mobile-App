package com.example.collectionreport;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Dowload_SQLite_DB {
    static DownloadManager manager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
   /* public static void ShareDBBackup(Activity activity, Context context){
        DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(context);
        try{
            dataBaseAdapter.open();
            String dbFilePath = "";
            //dbFilePath = dataBaseAdapter.udfnBackupdb(context);
            dataBaseAdapter.close();

            //String dbFileName = getFileName(dbFilePath);
            //dbFileName = dbFileName.replace(" ","_");\

            String[] s = new String[1];
            s[0] = dbFilePath;
            //String dbZIPFilePath = file + File.separator + dbFileName + ".zip";
            dbFilePath = dbFilePath.replace(" ","_");
            dbFilePath = dbFilePath.replace(".db",".zip");
            // create the zip
            zip(s, dbFilePath);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //File dbpath = context.getDatabasePath(DataBaseHelper.DB_NAME);
              //  ShareToAll_Above_Android7(activity, context, dbFilePath,dbpath);
            }
            else {
               // ShareToAll_Below_Android7(activity, dbFilePath);
            }

        }catch (Exception e){
            Log.d("ShareDBBackup ",e.getLocalizedMessage());
        }finally {
            if(dataBaseAdapter != null)
                dataBaseAdapter.close();
        }
    }*/


//----------------------------------------------------------------------------------------------


    private static int BUFFER_SIZE = 6 * 1024;
    public static void zip(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                }
                deleteFileInExternalStorage(new File(files[i]));
            }
        } finally {
            out.close();
        }
    }


//------------------------------------------------------------------------------------------------


    public static void deleteFileInExternalStorage(File deleteFile){
        if (deleteFile == null)
            return;

        if(deleteFile.delete()){
            Log.d("ShareDBBackup : ",deleteFile.toString() + " is deleted");
        }
    }


//-----------------------------------------------------------------------------------------------



    @SuppressLint("LongLogTag")
    public static  void ShareToAll_Above_Android7(Activity activity, Context context, String filePath,File db_file){

       // File sourceFile =  new File(Uri.decode(filePath));
        File sourceFile =  db_file;

        Uri uri = FileProvider.getUriForFile(context,  BuildConfig.APPLICATION_ID+".fileprovider" , new File(sourceFile.getPath()));

        String decodedURIString = Uri.decode(uri.toString());
        Uri decodedURI = Uri.parse(decodedURIString);

        Log.d("URI String : decodedURI ", decodedURI.toString());

        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(sourceFile).toString());
        String mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        intentShareFile.putExtra(Intent.EXTRA_STREAM, decodedURI );
        intentShareFile.setDataAndType(decodedURI ,mimeType);
       // intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intentShareFile, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        activity.startActivity(Intent.createChooser(intentShareFile,"Share to"));
       /* DownloadManager.Request request = new DownloadManager.Request(decodedURI);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, DataBaseHelper.DB_NAME);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
        request.allowScanningByMediaScanner();// if you want to be available from media players
        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);*/
    }

    public static void ShareToAll_Below_Android7(Activity activity, String filePath){
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        File fileInDir = new File(filePath);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(filePath)).toString());
        String mimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if(fileInDir.exists()) {
            intentShareFile.setType(mimeType);
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            activity.startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }
}
