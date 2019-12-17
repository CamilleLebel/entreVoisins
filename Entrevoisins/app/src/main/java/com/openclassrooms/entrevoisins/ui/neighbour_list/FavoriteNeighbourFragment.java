package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.Utils.ItemClickSupport;
import com.openclassrooms.entrevoisins.Utils.SharedPreference;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_details.NeighbourDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class FavoriteNeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> favorites;
    private RecyclerView favoriteList;

    private MyFavoriteNeighbourRecyclerViewAdapter adapter;

    private SharedPreference sharedPreference;


    /**
     * Create and return a new instance
     * @return @{@link FavoriteNeighbourFragment}
     */
    public static FavoriteNeighbourFragment newInstance() {
        FavoriteNeighbourFragment fragment = new FavoriteNeighbourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        sharedPreference = new SharedPreference();

        Log.i("DEBUG", "onCreate favorite fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_neighbour_list, container, false);
        Context context = view.getContext();

        favorites = sharedPreference.getFavorites(context);

        favoriteList = (RecyclerView) view;
        favoriteList.setLayoutManager(new LinearLayoutManager(context));
        favoriteList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        if(favorites == null){
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {
            if (favorites.size() == 0) {
                Toast.makeText(context, "no favorites", Toast.LENGTH_SHORT).show();
            }
            if (favorites != null){
                initList();
            }
        }

        this.configureOnClickRecyclerView();
        return view;
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(favoriteList, R.layout.fragment_neighbour)
                .setOnItemClickListener((recyclerView, position, v) -> {

                    Neighbour currentNeighbour = adapter.getNeighbour(position);

                    Intent intent = new Intent(getActivity(), NeighbourDetailsActivity.class);
                    intent.putExtra("current_id", currentNeighbour.getId());
                    intent.putExtra("current_name", currentNeighbour.getName());
                    intent.putExtra("current_avatar", currentNeighbour.getAvatarUrl());

                    startActivity(intent);

                });
    }

    /**
     * Init the List of neighbours
     */
    public void initList() {
        favorites = sharedPreference.getFavorites(getContext());
        adapter = new MyFavoriteNeighbourRecyclerViewAdapter(favorites);
        favoriteList.setAdapter(adapter);

        Log.i("DEBUG", "initList favorite fragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        Log.i("DEBUG", "onStart favorite fragment");

        favorites = sharedPreference.getFavorites(getContext());
        if(favorites == null){
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {
            if (favorites.size() == 0) {
                Toast.makeText(getContext(), "no favorites", Toast.LENGTH_SHORT).show();
            }
            if (favorites != null){
                initList();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        Log.i("DEBUG", "onStop favorite fragment");
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        sharedPreference.removeFavorite(getContext(), event.neighbour);
        initList();

        Log.i("DEBUG", "subscribe neighbour deleted");
    }

    public void showAlert(String title, String message) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }
}
