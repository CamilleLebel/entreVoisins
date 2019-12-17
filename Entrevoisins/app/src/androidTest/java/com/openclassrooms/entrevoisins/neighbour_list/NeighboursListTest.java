
package com.openclassrooms.entrevoisins.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.Utils.SharedPreference;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_details.NeighbourDetailsActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private SharedPreference sharedPreference;
    private List<Neighbour> favorites;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class, true, false);

    @Before
    public void setUp() {

//        assertThat(mActivity, notNullValue());
        sharedPreference = new SharedPreference();
        Context targetContext = getInstrumentation().getTargetContext();

        favorites = sharedPreference.getFavorites(targetContext);
        if(favorites.size() > 0){
            sharedPreference.removeFavorite(targetContext, favorites.get(0));
        }

        mActivity = mActivityRule.getActivity();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        mActivityRule.launchActivity(new Intent());
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        mActivityRule.launchActivity(new Intent());
        // Given : We remove the element at position 2
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.list_neighbours))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighboursList_displayAction_shouldDisplayNeighbour() {
        mActivityRule.launchActivity(new Intent());
        // When perform a click start neighbour activity
        Intents.init();
        onView(allOf(withId(R.id.list_neighbours), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        intended(hasComponent(NeighbourDetailsActivity.class.getName()));
    }

    @Test
    public void neighbourFavoritesFragment_multipleAction_shouldDisplayFavorites() throws InterruptedException {

        mActivityRule.launchActivity(new Intent());

        //click on One Of Neighbour, here on position 5
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(5, click()));


        // click on favorite Button, the FloatingButton
        onView(withId(R.id.fabfav)).perform(click());

        // goback
        onView(allOf(withId(R.id.details_content), isDisplayed())).perform(pressBack());

        Thread.sleep(300);

        //Go to Neighbour Favorite Fragment on screen
        onView(allOf(withId(R.id.main_content), isDisplayed())).perform(swipeLeft());

        //Need to lose time, because it is done too much faster on the test and the system can't have time to stabilise the list on
        Thread.sleep(300);

        //page is Displayed, also without this line I can't do the next line
        onView(withId(R.id.container)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_fav_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));


        // Verify if the name is the good one
        onView(withId(R.id.title_cardview)).check(matches(withText("Sylvain")));
    }
}