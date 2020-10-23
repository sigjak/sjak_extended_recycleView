package com.example.sjak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<String> movieList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateList();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



 /*       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieList.add("Plastic Man");
                movieList.add("Junk man");
                movieList.add("Slut");
                movieList.add("Wanker");

                recyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    List<String> archivedMovies = new ArrayList<>();
    String deletedMovie = null;

    ItemTouchHelper.SimpleCallback simpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();


                    switch (direction) {
                        case ItemTouchHelper.LEFT:
                            deletedMovie = movieList.get(position);
                            movieList.remove(position);
                            recyclerAdapter.notifyItemRemoved(position);
                            Snackbar.make(recyclerView, deletedMovie, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            movieList.add(position, deletedMovie);
                                            recyclerAdapter.notifyItemInserted(position);
                                        }
                                    }).show();
                            break;
                        case ItemTouchHelper.RIGHT:
                            String movieName = movieList.get(position);
                            archivedMovies.add(movieName);
                            movieList.remove(position);
                            recyclerAdapter.notifyItemRemoved(position);
                            Snackbar.make(recyclerView, movieName + " archived", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            movieList.add(position, movieName);
                                            archivedMovies.remove(archivedMovies.lastIndexOf(movieName));
                                            recyclerAdapter.notifyItemInserted(position);
                                        }
                                    }).show();
                            break;
                    }
                }

                @Override
                public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {

                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                            isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                            .addSwipeLeftActionIcon(R.drawable.delete)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green))
                            .addSwipeRightActionIcon(R.drawable.archive).create().decorate();

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem searcItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searcItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void populateList() {

        movieList = new ArrayList<>();
        movieList.add("thor");
        movieList.add("Ragnarok");
        movieList.add("Iron Man");
        movieList.add("Ant man");
        movieList.add("Spider");
        movieList.add("Superman");
        movieList.add("thor");
        movieList.add("Ragnarok");
        movieList.add("Iron Man");
        movieList.add("Ant man");
        movieList.add("Spider");
        movieList.add("Superman");
        movieList.add("thor");
        movieList.add("Ragnarok");
        movieList.add("Iron Man");
        movieList.add("Ant man");
        movieList.add("Spider");
        movieList.add("Superman");
    }
}