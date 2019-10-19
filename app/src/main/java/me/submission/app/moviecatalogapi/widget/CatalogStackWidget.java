package me.submission.app.moviecatalogapi.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import me.submission.app.moviecatalogapi.R;

/**
 * Implementation of App Widget functionality.
 */
public class CatalogStackWidget extends AppWidgetProvider {

    static final String TOAST_ACTION = "me.submission.app.moviecatalogapi.TOAST_ACTION";
    static final String EXTRA_ITEM = "me.submission.app.moviecatalogapi.EXTRA_ITEM";

    public static final String UPDATE_WIDGET = "update_widget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.widget_catalog_stack);
        views.setRemoteAdapter(R.id.widget_stackview, intent);
        views.setEmptyView(R.id.widget_stackview, R.id.widget_emptyview);

        Intent toastIntent = new Intent(context, CatalogStackWidget.class);
        toastIntent.setAction(CatalogStackWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0,
                toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_stackview, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        if (intent.getAction().equals(TOAST_ACTION)){
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Favorite " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction().equals(UPDATE_WIDGET)){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                    CatalogStackWidget.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_stackview);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

