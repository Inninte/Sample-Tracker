package project.yabut.testapplication3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by adria on 30-Jul-17.
 * Handles saving my array of objects onto shared preferences and retrieving them again
 * When loading data, remember to notify the adapter that the data set has changed (adapter.notifyDataSetChanged())
 */

public class SaveAndLoad {
    /**
     * Saves the DialysisCardArray...not very flexible, but you can fix that later
     */
    public static void saveArraySampleCard(Context context, ArrayList<SampleCard> dataset) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        // Save array into a json
        Gson gson = new Gson();
        String json = gson.toJson(dataset);
        prefsEditor.putString("DialysisCardArray", json);
        prefsEditor.commit();
    }

    public static void loadArraySampleCard(Context context, ArrayList<SampleCard> dataset) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("DialysisCardArray", "");
        Type type = new TypeToken<ArrayList<SampleCard>>() {
        }.getType();
        // I remember reading somewhere that when I instantiate myDataSet, I need to keep the variable as
        // final.  This means I can't actually recreate the object on a load.  Thus, I make a temporary array and pull from there.
        // This will slow significantly on larger data sets though...
        ArrayList<SampleCard> tempArrayList = gson.fromJson(json, type);

        if (tempArrayList != null) {
            for (SampleCard item : tempArrayList) {
                dataset.add(item);
            }
            // Delete contents to save memory
            tempArrayList.clear();
        }
    }
}
