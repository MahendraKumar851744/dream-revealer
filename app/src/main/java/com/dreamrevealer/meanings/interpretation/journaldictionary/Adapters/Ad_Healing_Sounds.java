package com.dreamrevealer.meanings.interpretation.journaldictionary.Adapters;

import static com.dreamrevealer.meanings.interpretation.journaldictionary.util.Constant.BASE_URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.Healing_dao;
import com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.healing_database;
import com.dreamrevealer.meanings.interpretation.journaldictionary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.text.SimpleDateFormat;
public class Ad_Healing_Sounds extends RecyclerView.Adapter<Ad_Healing_Sounds.CategoryViewHolder> {

    Context context;
    ArrayList<Healing> items;

    ProgressBar progressBar;

    static Healing healing_item;

    static int DOWNLOADING_STATE = 0;
    public Ad_Healing_Sounds(Context context, ArrayList<Healing> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.lay_healing_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_title.setText(items.get(position).getTitle());
        holder.desc.setText("Size: "+items.get(position).getSize());
        Glide.with(context).load(BASE_URL +  items.get(position).getImage()).into(holder.iv1);
        holder.playNow.setOnClickListener(view -> {

            if(DOWNLOADING_STATE == 0){
                DOWNLOADING_STATE = 1;
                progressBar = holder.progressBar;
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String randomChars = UUID.randomUUID().toString().substring(0, 5);
                String uniqueFileName = timeStamp + "_" + randomChars + ".mp3";
                healing_item = items.get(position);
                startDownload(BASE_URL+items.get(position).getUrl(),uniqueFileName);
            }
            




        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,desc;
        ImageView iv1;
        CardView playNow;
        ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            iv1 = itemView.findViewById(R.id.iv1);
            desc = itemView.findViewById(R.id.desc);
            playNow = itemView.findViewById(R.id.playNow);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

    private void startDownload(String download_file_path,String fileName) {
        progressBar.setVisibility(View.VISIBLE);
        File myDir = context.getFilesDir();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new DownloadAudioTask(download_file_path, fileName,myDir));
    }

    private class DownloadAudioTask implements Runnable {
        private String download_file_path;
        private String fileName;
        private File saveDirectory;

        DownloadAudioTask(String download_file_path, String fileName, File saveDirectory) {
            this.download_file_path = download_file_path;
            this.fileName = fileName;
            this.saveDirectory = saveDirectory;
        }

        @Override
        public void run() {
            int downloadedSize = 0;
            int totalSize = 0;

            try {
                URL url = new URL(download_file_path);
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // connect
                urlConnection.connect();

                File file = new File(saveDirectory, fileName);
                FileOutputStream fileOutput = new FileOutputStream(file);

                // Stream used for reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                // this is the total size of the file which we are downloading
                totalSize = urlConnection.getContentLength();
                Log.d("AUDIO-FILE","Total Audio File Size"+totalSize);

                // runOnUiThread(new Runnable() {
                // public void run() {
                // pb.setMax(totalSize);
                // }
                // });

                // create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0;

                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    // update the progressbar //
                    // runOnUiThread(new Runnable() {
                    // public void run() {
                    // pb.setProgress(downloadedSize);
                    // float per = ((float)downloadedSize/totalSize) * 100;
                    // cur_val.setText("Downloaded " + downloadedSize + "KB / " +
                    // totalSize + "KB (" + (int)per + "%)" );
                    // }
                    // });
                }
                // close the output stream when complete //
                fileOutput.close();
                ((Activity) context).runOnUiThread(new Runnable() {
                 public void run() {
                     DOWNLOADING_STATE = 0;
                     healing_item.setOurl(fileName);
                     healing_database healing_database = com.dreamrevealer.meanings.interpretation.journaldictionary.Databases.healing.healing_database.getDbInstance(context);
                     Healing_dao dao = healing_database.dao();
                     dao.insert(healing_item);
                     if(progressBar!=null){
                         progressBar.setVisibility(View.GONE);
                     }

                 }
                 });

            } catch (final MalformedURLException e) {
                download_failed("Error : MalformedURLException " + e);
                e.printStackTrace();
            } catch (final IOException e) {
                download_failed("Error : IOException " + e);
                e.printStackTrace();
            } catch (final Exception e) {
                download_failed("Error : Please check your internet connection " + e);
            }
        }


    }

    private void download_failed(String e){
        Toast.makeText(context, "Download Failed!", Toast.LENGTH_SHORT).show();
        if(progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }
        DOWNLOADING_STATE = 0;
        Log.d("AUDIO-FILE",e);
    }



}
