package com.college.collegeconnect.adapters;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.collegeconnect.database.entity.DownloadEntity;
import com.college.collegeconnect.datamodels.Constants;
import com.college.collegeconnect.R;
import com.college.collegeconnect.datamodels.SaveSharedPreference;
import com.college.collegeconnect.datamodels.NotesReports;
import com.college.collegeconnect.datamodels.Upload;
import com.college.collegeconnect.viewmodels.DownloadNotesViewModel;
import com.college.collegeconnect.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements Filterable {

    private final Context context;
    private final ArrayList<Upload> noteslist;
    private final ArrayList<Upload> noteslistfull;
    private EditText answer;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DownloadNotesViewModel downloadNotesViewModel;
    String name;

    public NotesAdapter(Context context, ArrayList<Upload> noteslist, DownloadNotesViewModel downloadNotesViewModel) {
        this.context = context;
        this.noteslist = noteslist;
        this.downloadNotesViewModel = downloadNotesViewModel;
        noteslistfull = new ArrayList<>(noteslist);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notes, parent, false);
        return new NotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //Set Values in card
        final Upload notes = noteslist.get(position);
        holder.title.setText(notes.getName());
        holder.noOfDown.setText("No. of Downloads: " + notes.getDownload());
        name = notes.getName();

        ArrayList<String> selectedTags = new ArrayList<>();

        //Tags Recycler View
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        TagsAdapter recyclerAdapter = new TagsAdapter(context, selectedTags);
        holder.recyclerView.setAdapter(recyclerAdapter);

        final ArrayList<String> finalSelectedTags = selectedTags;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                DownloadManager downloadmanager = (DownloadManager) context.
                        getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(notes.getUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, "Downloads/", notes.name + ".pdf");

                 downloadmanager.enqueue(request);


                DownloadEntity downloadEntity = new DownloadEntity(notes.getName(),"dzed","zde");
                downloadNotesViewModel.addDownload(downloadEntity);

                String fileUri =DownloadManager.COLUMN_LOCAL_URI;

                File mFile = new File(DownloadManager.COLUMN_LOCAL_URI);
                Toast.makeText(context,"yesssssssss 222222222222222222",Toast.LENGTH_LONG).show();


                String fileName = mFile.getAbsolutePath();
                downloadNotesViewModel.addDownload(downloadEntity);
                    // openfile(fileName, notes.getTimestamp());

                //                    downloadfile(notes);
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReferenceFromUrl(notes.getUrl());
//                StorageReference  islandRef = storageRef.child("/notes/GLSI-A/");
//
//                File rootPath = new File(Environment.getExternalStorageDirectory(), notes.getName());
//                if(!rootPath.exists()) {
//                    rootPath.mkdirs();
//                }
//
//                final File localFile = new File(rootPath,notes.getName()+".pdf");
//
//                islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        Log.e("firebase ",";local tem file created  created " +localFile.toString());
//                        //  updateDb(timestamp,localFile.toString(),position);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Log.e("firebase ",";local tem file not created  created " +exception.toString());
//                        Toast.makeText(context,exception.toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
                    int downloads = notes.getDownload() + 1;
                    Upload upload = new Upload(notes.getName(),

                            notes.getBranch(),
                            downloads, notes.getUrl(), notes.getTimestamp());
                    DatabaseReference mDatabaseReference = FirebaseUtil.getDatabase().getReference(Constants.DATABASE_PATH_UPLOADS);
                    mDatabaseReference.child(notes.getTimestamp() + "").setValue(upload);
                    Log.d("upload", "onClick: download");



            }
        });

        final ArrayList<String> finalSelectedTags1 = selectedTags;
        final ArrayList<String> finalSelectedTags2 = selectedTags;
        final ArrayList<String> finalSelectedTags3 = selectedTags;
        final ArrayList<String> finalSelectedTags4 = selectedTags;
        final ArrayList<String> finalSelectedTags5 = selectedTags;
        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.notes_overflow, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.report: {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
                                final View view = inflater.inflate(R.layout.layout_dialog_report, null);

                                builder.setView(view)
                                        .setTitle("State Your Concern")
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                answer = view.findViewById(R.id.answer);
                                final AlertDialog dialog = builder.create();
                                dialog.show();
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String text = answer.getText().toString();
                                        if (text.isEmpty())
                                            answer.setError("Please enter your problem");
                                        else if (text.length() < 20)
                                            answer.setError("Minimum 20 characters required");
                                        else {
                                            submitReport(text, notes.getTimestamp());
                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }
                            break;


                        }

                        return true;
                    }
                });
                popup.getMenu().findItem(R.id.details).setVisible(false);
                popup.show();
            }
        });

    }

    public void downloadfile(Upload notes) {

//        DownloadManager downloadmanager = (DownloadManager) context.
//                getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(notes.getUrl());
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalFilesDir(context, "download/", notes.name + "pdf");
//
//        downloadmanager.enqueue(request);


        String url = notes.getUrl();
        String name = notes.getName();
        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(notes.getUrl());

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, "download/", notes.name + "pdf");
        request.allowScanningByMediaScanner();
        final long id = downloadmanager.enqueue(request);
        Toast.makeText(context, "Downloading..... 2 Please Wait!", Toast.LENGTH_LONG).show();
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Cursor c = downloadmanager.query(new DownloadManager.Query().setFilterById(id));
                Toast.makeText(context,"yesssssssss ",Toast.LENGTH_LONG).show();

                if (c != null) {
                    try {
                        Toast.makeText(context,"yesssssssss ",Toast.LENGTH_LONG).show();

                        String fileUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        Toast.makeText(context,"yesssssssss 111111111111111111",Toast.LENGTH_LONG).show();

                        File mFile = new File(Uri.parse(fileUri).getPath());
                        Toast.makeText(context,"yesssssssss 222222222222222222",Toast.LENGTH_LONG).show();


                        String fileName = mFile.getAbsolutePath();
                        DownloadEntity downloadEntity = new DownloadEntity(notes.getName(),"dzed","zde");
                        downloadNotesViewModel.addDownload(downloadEntity);
                   //     openfile(fileName, notes.getTimestamp());
                    } catch (Exception e) {
                        Log.e("error", "Could not open the downloaded file");
                        Toast.makeText(context,"noooooooooooooooo",Toast.LENGTH_LONG).show();

                    }
                }
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
//
//    public void openfile(String path) {
////        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(path));
////        Log.d("Upload", "openfile:uri being sent in intent "+uri+"\n Actual path: "+uri);
////        context.getApplicationContext().grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Intent intent = new Intent(context, PdfViewerActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("file",path);
////        intent.setAction(Intent.ACTION_VIEW);
////        intent.putExtra(Intent.EXTRA_STREAM, uri);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Log.d("Upload", "openfile: " + path);
////        intent.setDataAndType(uri, "application/pdf");
//        ((Activity) context).startActivityForResult(intent,95);
//    }
//    public void openfile(String path, Long timeStamp) {
////        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(path));
////        Log.d("Upload", "openfile:uri being sent in intent "+uri+"\n Actual path: "+uri);
////        context.getApplicationContext().grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Intent intent = new Intent(context, PdfViewerActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("file",path);
//        bundle.putLong("timestamp",timeStamp);
//        intent.putExtras(bundle);
////        intent.setAction(Intent.ACTION_VIEW);
////        intent.putExtra(Intent.EXTRA_STREAM, uri);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        Log.d("Upload", "openfile: " + path);
////        intent.setDataAndType(uri, "application/pdf");
//        ((Activity) context).startActivityForResult(intent,95);
//    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, noOfDown, unit;
        ImageButton report;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.authorname);
            report = itemView.findViewById(R.id.reportButton);
            noOfDown = itemView.findViewById(R.id.download);
            recyclerView = itemView.findViewById(R.id.tagsRecycler);
            unit = itemView.findViewById(R.id.unitText);
        }
    }

    @Override
    public Filter getFilter() {
        return notesfilter;
    }

    private Filter notesfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Upload> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(noteslistfull);
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (Upload item : noteslistfull) {
                    if (item.getName().toLowerCase().contains(filterpattern) ) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            noteslist.clear();
            noteslist.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public void submitReport(String text, long timeStamp) {
        DatabaseReference databaseReference = FirebaseUtil.getDatabase().getReference("NotesReports");
        NotesReports notesReports = new NotesReports(SaveSharedPreference.getUserName(context), text, timeStamp);
        databaseReference.child(System.currentTimeMillis() + "").setValue(notesReports);
//        Toast.makeText(context, text+" "+timeStamp, Toast.LENGTH_SHORT).show();
    }

}
