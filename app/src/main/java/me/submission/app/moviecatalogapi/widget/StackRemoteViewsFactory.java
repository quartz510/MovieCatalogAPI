package me.submission.app.moviecatalogapi.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.database.FavoriteHelper;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.CONTENT_URI_MOVIE;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private FavoriteHelper favoriteHelper;
    private ArrayList<CataloguePojo> pojos = new ArrayList<>();
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        favoriteHelper = new FavoriteHelper(context);
        favoriteHelper.open();
        pojos = favoriteHelper.getCataloguePojoMv();
        favoriteHelper.close();
    }

    @Override
    public void onDataSetChanged() {
        favoriteHelper.open();
        pojos = favoriteHelper.getCataloguePojoMv();
        favoriteHelper.close();
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        widgetManager.notifyAppWidgetViewDataChanged(widgetManager.getAppWidgetIds(
                new ComponentName(context, StackWidgetService.class)
        ), R.id.widget_stackview);
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
        Log.d("tag", getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return pojos.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_catalog_item);
        try{
            Bitmap bitmap = Glide.with(context).asBitmap().load
                    ("https://image.tmdb.org/t/p/w185"+ pojos.get(i).getPhotoMovie()).
                    submit(185, 278).get();
            remoteViews.setImageViewBitmap(R.id.widget_item_imageview, bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putString(CatalogStackWidget.EXTRA_ITEM, pojos.get(i).getTitleMovie());
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.widget_item_imageview, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
