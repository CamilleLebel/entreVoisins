package com.openclassrooms.entrevoisins.ui.neighbour_list;

import com.openclassrooms.entrevoisins.Utils.SharedPreference;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FavoriteNeighbourFragmentTest {

    private ListNeighbourActivity activity;
    private SharedPreference sharedPreference;
    private NeighbourApiService apiService;

    @Before
    public void setUp() throws Exception {
        apiService = DI.getNewInstanceApiService();
        sharedPreference = mock(SharedPreference.class);
    }

    @Test
    public void neighbourFavoriteListWithFavorites(){
        List<Neighbour> neighbours = apiService.getNeighbours();
        List<Neighbour> expectedNeighbours = sharedPreference.getFavorites(activity);
        assertFalse(expectedNeighbours.contains(neighbours));
    }
}