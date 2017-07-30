package com.example.jerry.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jerry.nytimessearch.Article;
import com.example.jerry.nytimessearch.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Article article = (Article)getIntent().getSerializableExtra("article");
        WebView webView = (WebView) findViewById(R.id.wvArticle);

        //Article article = (Article) getIntent().getSerializableExtra("article");
        //Toast.makeText(this, article.getWebUrl().toString(), Toast.LENGTH_SHORT).show();



        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(article.getWebUrl());
    }

}
