package project.yabut.testapplication3;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by adria on 14-Aug-17.
 */

public class SampleCategoryHandler {
    private SampleCard sampleCard;
    private ArrayList<SampleCategory> sampleCategories = new ArrayList<SampleCategory>();

    public SampleCategoryHandler(SampleCard sampleCard) {
        this.sampleCard = sampleCard;
    }

//    public void addItem() {
//        SampleCategory sampleCategory = new SampleCategory();
//        sampleCategories.add(sampleCategories.size()-1, sampleCategory);
//        saveToSampleCard();
//    }
//
//    public void removeItem(SampleCategory sampleCategory) {
//        sampleCategories.remove(sampleCategories.indexOf(sampleCategory));
//        saveToSampleCard();
//    }
//
//    public void updateView(LinearLayout linearLayout) {
//        // Load the categories from the sampleCard
//        sampleCategories = sampleCard.getSampleCategories();
//        for (SampleCategory item : sampleCategories) {
//            linearLayout.addView(item.getRelativeLayout());
//        }
//    }
//
//    public void saveToSampleCard() {
//        sampleCard.setSampleCategories(sampleCategories);
//    }
}
