package com.ncl.nclcustomerservice.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.ncl.nclcustomerservice.R;

import java.io.File;
import java.util.List;

public class FilesPreviewAdapter extends RecyclerView.Adapter<FilesPreviewAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<FileDetails> filesList;
    private FileDetailsItemListner fileDetailsItemListner;

    /**
     * Instantiates a new Files preview adapter.
     *
     * @param context                the context
     * @param filesList              the files list
     * @param fileDetailsItemListner the string listner
     */
    public FilesPreviewAdapter(Context context, List<FileDetails> filesList
            , FileDetailsItemListner fileDetailsItemListner) {
        this.fileDetailsItemListner = fileDetailsItemListner;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(layoutInflater.inflate(R.layout.files_preview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final FileDetails fileDetails = filesList.get(position);
        if (position == 0) {
            holder.tvFileSize.setText("Purchase Order Image ");
        }
        if (position == 1) {
            holder.tvFileSize.setText("Complaints Image");
        }
        if (position == 2) {
            holder.tvFileSize.setText("Payment instrument Image");
        }
        if (position == 3) {
            holder.tvFileSize.setText("Transfer receipt Image");
        }
        Log.d("fffff->", fileDetails.filePath + "");
        if (fileDetails.filePath != null) {
            Picasso.with(context)
                    .load("file:///" + fileDetails.getFilePath())
                    .resize(50, 50)
                    /*.skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                    .error(R.drawable.ic_baseline_camera_alt_24)
                    .into(holder.ivFilePreview);
        } else if (fileDetails.filePathS3 != null && fileDetails.filePathS3.length() > 5) {
            Picasso.with(context)
                    .load(fileDetails.filePathS3)
                    .resize(50, 50)
                    /*.skipMemoryCache(true)
                     .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                    .error(R.drawable.ic_baseline_camera_alt_24)
                    .into(holder.ivFilePreview);
        } else {
            Picasso.with(context)
                    .load(R.drawable.ic_baseline_camera_alt_24)
                    .error(R.drawable.ic_baseline_camera_alt_24)
                    .into(holder.ivFilePreview);
        }
        holder.itemView.setOnClickListener(v -> {
            fileDetailsItemListner.onFileDetailsListner(fileDetails, position, true);
            //notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public void updateFile(int selectedPostion, File file) {
        FileDetails fileDetails = filesList.get(selectedPostion);
        fileDetails.setFileName(file.getName());
        fileDetails.setFilePath(file.getAbsolutePath());
        notifyItemChanged(selectedPostion);


    }

    /**
     * The type My view holder.
     */
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFilePreview;
        TextView tvFileSize;

        MyViewHolder(View itemView) {
            super(itemView);
            ivFilePreview = itemView.findViewById(R.id.iv_file_preview);
            tvFileSize = itemView.findViewById(R.id.tv_file_size);

        }
    }
}
