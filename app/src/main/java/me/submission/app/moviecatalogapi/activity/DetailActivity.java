package me.submission.app.moviecatalogapi.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.database.FavoriteHelper;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

public class DetailActivity extends AppCompatActivity {
    public static final String DETAIL_ACTIVITY = "detail_activity";

    private Boolean isItFavorite = false;
    private FavoriteHelper favoriteHelper;
    private Menu menu;

    private int Id;
    private String Type;
    private String photoMvTv;

    ImageView poster;
    TextView overview;
    TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        poster = findViewById(R.id.second_img);
        titleName = findViewById(R.id.second_title);
        overview = findViewById(R.id.second_desc);

        CataloguePojo pojo = getIntent().getParcelableExtra(DETAIL_ACTIVITY);
        photoMvTv = pojo.getPhotoMovie();
        Id = pojo.getId();
        Type = pojo.getType();

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(pojo.getTitleMovie());
        }

        overview.setText(pojo.getDescMovie());
        titleName.setText(pojo.getTitleMovie());
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185"+pojo.getPhotoMovie())
                .into(poster);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        if (Type.equals("movie")){
            favoriteHelper.open();
            isItFavorite = favoriteHelper.checkDataMovieExistOrNot(Id);
            favoriteHelper.close();
        }
        if (Type.equals("tv show")){
            favoriteHelper.open();
            isItFavorite = favoriteHelper.checkDataTvshowExistOrNot(Id);
            favoriteHelper.close();
        }
        setfIcon();
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        if (menuItem.getItemId() == R.id.menu_star) {
            CataloguePojo pojo = getIntent().getParcelableExtra(DETAIL_ACTIVITY);
            if (isItFavorite) {
                if (Type.equals("movie")) {
                    favoriteHelper.open();
                    favoriteHelper.deleteCataloguePojoMv(Id);
                    favoriteHelper.close();
                    FavoriteHelper.updateMovieWidget(this);
                }
                if (Type.equals("tv show")) {
                    favoriteHelper.open();
                    favoriteHelper.deleteCataloguePojoTv(Id);
                    favoriteHelper.close();
                }
                Snackbar.make(getWindow().getDecorView(), pojo.getTitleMovie() + " "
                        + getString(R.string.favorite_removed), Snackbar.LENGTH_LONG).show();
            } else {
                if (Type.equals("movie")) {
                    CataloguePojo favMv = new CataloguePojo();
                    favMv.setId(Id);
                    favMv.setDescMovie(overview.getText().toString());
                    favMv.setPhotoMovie(photoMvTv);
                    favMv.setTitleMovie(titleName.getText().toString());
                    favoriteHelper.open();
                    favoriteHelper.insertCataloguePojoMv(favMv);
                    favoriteHelper.close();
                    FavoriteHelper.updateMovieWidget(this);
                }
                if (Type.equals("tv show")) {
                    CataloguePojo favTv = new CataloguePojo();
                    favTv.setId(Id);
                    favTv.setDescTV(overview.getText().toString());
                    favTv.setPhotoTv(photoMvTv);
                    favTv.setTitleTv(titleName.getText().toString());
                    favoriteHelper.open();
                    favoriteHelper.insertCataloguePojoTv(favTv);
                    favoriteHelper.close();
                }
                Snackbar.make(getWindow().getDecorView(), pojo.getTitleMovie() + " "
                        + getString(R.string.favorite_added), Snackbar.LENGTH_LONG).show();
            }
            isItFavorite = !isItFavorite;
            setfIcon();
            return true;
        }
        finish();
        return true;
    }

    private void setfIcon(){
        if (isItFavorite) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable
                    .ic_star_gold_24dp));
        } else {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable
                    .ic_star_border_white_24dp));
        }
    }
}