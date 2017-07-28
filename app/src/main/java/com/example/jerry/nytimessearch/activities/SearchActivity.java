package com.example.jerry.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.jerry.nytimessearch.Article;
import com.example.jerry.nytimessearch.ArticleArrayAdapter;
import com.example.jerry.nytimessearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;

     ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);


        setupViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        //return super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViews(){

        etQuery = (EditText) findViewById (R.id.etQuery);
        gvResults = (GridView) findViewById (R.id.gvResults);
        btnSearch = (Button) findViewById (R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this,articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);
                i.putExtra("article", article);
                startActivity(i);

            }
        });

    }





    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        //Toast.makeText(this, "Searching for" + query, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "198bc0cf37d3483692645939c3632343");
        params.put("page", 0);
        params.put("q", query);
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                //Log.d("DEBUG", response.toString());
                //System.out.println("Debug");
                JSONArray articleJsonResults = null;
                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");

                   adapter.addAll(Article.fromJSONArray(articleJsonResults));

                    //Log.d("DEBUG", response.toString());

                }catch(JSONException e){
                    e.printStackTrace();

                }
            }
        });
    }


}
