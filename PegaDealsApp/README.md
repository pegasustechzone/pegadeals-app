# PegaDeals Android WebView App

This is a native Android WebView application for https://pegadeals.store, designed to provide a fully native app-like experience.

## Features Implemented:
- **Immersive Mode**: Full-screen experience with hidden status and navigation bars.
- **No Browser UI**: Removed loading bars, spinners, and browser-type loaders.
- **Smooth Transitions**: Fade-in animation on page load to avoid white flashes.
- **Native Behavior**: 
  - Disabled pull-to-refresh.
  - Disabled zooming (pinch and double-tap).
  - Disabled overscroll glow and hidden scrollbars.
  - Disabled long-press context menus and text selection.
- **Navigation**: 
  - All links (internal and external) open within the app.
  - Hardware back button support for WebView navigation.
- **Performance**: Hardware acceleration enabled and persistent session/cookie management.

## How to Build:
1. Open this project in **Android Studio**.
2. Wait for Gradle sync to complete.
3. Click on `Build` > `Build Bundle(s) / APK(s)` > `Build APK(s)`.
4. The generated APK will be located in `app/build/outputs/apk/debug/app-debug.apk`.

## Project Structure:
- `app/src/main/java/com/pegadeals/app/MainActivity.java`: Main logic for WebView configuration and behavior.
- `app/src/main/res/layout/activity_main.xml`: Layout containing the WebView.
- `app/src/main/AndroidManifest.xml`: App configuration and permissions.
