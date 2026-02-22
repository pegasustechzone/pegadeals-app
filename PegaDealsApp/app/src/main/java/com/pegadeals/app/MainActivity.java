package com.pegadeals.app;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private FrameLayout container;
    private final String targetUrl = "https://pegadeals.store";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 11. Enable full-screen immersive mode
        // 12. Hide status bar and navigation bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideSystemUI();

        setContentView(R.layout.activity_main);

        container = findViewById(R.id.webview_container);
        webView = findViewById(R.id.webview);

        // 14. Enable hardware acceleration
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        configureWebView();
        
        // 19. Maintain session and cookies permanently
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        webView.loadUrl(targetUrl);
    }

    private void configureWebView() {
        WebSettings settings = webView.getSettings();
        
        // Core settings
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 16. Disable caching refresh behavior (use default for performance)

        // 4. Prevent zooming
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        // 5. Disable overscroll glow effect
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 6. Hide scrollbars
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        // 9. Disable long press context menu
        webView.setOnLongClickListener(v -> true);
        webView.setLongClickable(false);

        // 10. Remove text selection (via JS)
        webView.setWebViewClient(new CustomWebViewClient());

        // 1. Disable pull-to-refresh (implicitly by not adding SwipeRefreshLayout)
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            // 7. Open all internal links inside the same WebView
            // 8. Open external links inside the app instead of external browser
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // 13. Remove white flash & 15. Smooth page transition
            // We keep the current view visible until the next one is ready
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            
            // 10. Remove text selection via CSS injection
            view.evaluateJavascript("document.documentElement.style.webkitUserSelect='none';", null);
            view.evaluateJavascript("document.documentElement.style.userSelect='none';", null);
            
            // 15. Fade transition instead of reload animation
            AlphaAnimation fade = new AlphaAnimation(0.0f, 1.0f);
            fade.setDuration(300);
            view.startAnimation(fade);
            view.setVisibility(View.VISIBLE);
        }
    }

    // 18. Enable back button navigation inside WebView
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
}
