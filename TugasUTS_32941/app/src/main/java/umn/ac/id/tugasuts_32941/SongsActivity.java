package umn.ac.id.tugasuts_32941;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

public class  SongsActivity extends AppCompatActivity {

    private final LinkedList<String> songList =new LinkedList<>();
    private RecyclerView mRecyclerView;
    private String[] items;
    private String[] path_items;
    private SongAdapter myAdapter;
    private static boolean accepted = false;

    ListView allMusicList;
    ArrayAdapter<String> musicArrayAdapter;
    String songs[];
    ArrayList<File> musics;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button okbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_daftar_lagu);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CheckPermission();
        openPopUpWindow();
    }

    private void CheckPermission () {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                loadSong();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findSong (File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singlefile: files)
        {
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                arrayList.addAll(findSong(singlefile));
            }else{
                if(singlefile.getName().endsWith(".mp3")){
                    arrayList.add(singlefile);
                }
            }
        }
        return arrayList;
    }

    void loadSong(){
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        items = new String[mySongs.size()];
        for(int i = 0;i<mySongs.size();i++){
            items[i] = mySongs.get(i).toString();
            songList.add(items[i]);
        }
        myAdapter = new SongAdapter(this, songList);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void openPopUpWindow() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View Popup = getLayoutInflater().inflate(R.layout.activity_pop_up_window, null);
        okbtn = (Button) Popup.findViewById(R.id.btnok);

        dialogBuilder.setView(Popup);
        dialog = dialogBuilder.create();
        dialog.show();

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                accepted = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.halaman_daftar_lagu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuProfil) {
            Intent intentProfil2 = new Intent(SongsActivity.this, Profil2Activity.class);
            startActivity(intentProfil2);
        }

        if (id == R.id.menuLogout) {
            Intent intentLogout = new Intent(SongsActivity.this, MainActivity.class);
            startActivity(intentLogout);
        }

        return super.onOptionsItemSelected(item);
    }
}

