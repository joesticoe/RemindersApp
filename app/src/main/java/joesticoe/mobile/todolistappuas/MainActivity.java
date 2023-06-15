package joesticoe.mobile.todolistappuas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemTodoList> itemTodoLists = new ArrayList<>();

    private String todo, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_todolist);

        rv_Adapter rv_adapter = new rv_Adapter(MainActivity.this, MainActivity.this.itemTodoLists);

        recyclerView.setAdapter(rv_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean terkoneksi = networkInfo != null && networkInfo.isConnected();

        if(terkoneksi){
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Sabar data masih di proses broo....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            String url = "https://mgm.ub.ac.id/todo.php";

                            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                            StringRequest request = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @SuppressLint("NotifyDataSetChanged")
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray jsonArray = new JSONArray(response);
                                                Log.d("json", jsonArray.toString());
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                    int id = jsonObject.getInt("id");
                                                    todo = jsonObject.getString("what");
                                                    time = jsonObject.getString("time");
                                                    ItemTodoList item = new ItemTodoList(id, todo, time);
                                                    itemTodoLists.add(item);
                                                }
                                                rv_adapter.notifyDataSetChanged();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    });
                            requestQueue.add(request);

                        }
                    });
                }
            }).start();

        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Kamu Ga Punya Internet!")
                    .setMessage("Beli Paketan Dulu Biar Ada Internet")
                    .setCancelable(false)
                    .setNegativeButton("Coba Lagi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();

        }



    }
}