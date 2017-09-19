package project.yabut.testapplication3;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adria on 31-Jul-17.
 * Handles storage of deleted items and the re-addition of the items to the main array
 * When calling the undo function, remember to notify the adapter that the data set has changed ((adapter.notifyDataSetChanged()).
 */

public class UndoHandler {

    // This is the array in the Main Activity.  Be very careful when modifying it!!!!
    private ArrayList<SampleCard> sampleCardDataSet;
    // Useful for Toast messages!
    private Context context;
    // This array will hold the objects that were deleted
    private ArrayList<SampleCard> discardedSampleCardDataSet = new ArrayList<SampleCard>();
    // This array will hold the position the deleted object was in (to return it to the list in a cooler fashion :) )
    private ArrayList<Integer> sampleInfoPreviousSpot = new ArrayList<Integer>();

    /**
     * Link a data set (works because ArrayList will get passed by reference)
     * @param context
     * @param sampleCardDataSet your main data set
     */
    public UndoHandler(Context context, ArrayList<SampleCard> sampleCardDataSet) {
        this.context = context;
        this.sampleCardDataSet = sampleCardDataSet;
    }

    public void storeToUndoHandler(SampleCard sample, int originalPosition) {
        discardedSampleCardDataSet.add(sample);
        sampleInfoPreviousSpot.add(originalPosition);
    }

    /**
     * Undo the deletion of an object.  This undoes the latest deletion up until the array storing deleted items is empty
     * You MUST call adapter.notifyDataSetChanged() after calling undo or else the app will crash
     */
    public void undo() {
        if (!discardedSampleCardDataSet.isEmpty() && !sampleInfoPreviousSpot.isEmpty()) {
            // Get the previous item
            SampleCard item = discardedSampleCardDataSet.get(discardedSampleCardDataSet.size() - 1);
            // Discard it from the temporary data set
            discardedSampleCardDataSet.remove(discardedSampleCardDataSet.size() - 1);
            // Get its position previous
            int position = sampleInfoPreviousSpot.get(sampleInfoPreviousSpot.size() - 1);
            // Discard position from temporary data set
            sampleInfoPreviousSpot.remove(sampleInfoPreviousSpot.size() - 1);
            // Add it to the array
            sampleCardDataSet.add(position, item);
        } else {
            Toast.makeText(context, "Nothing left to undo!", Toast.LENGTH_SHORT).show();
        }
    }
}
