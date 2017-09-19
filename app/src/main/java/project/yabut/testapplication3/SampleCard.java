package project.yabut.testapplication3;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by adria on 30-Jul-17.
 * This is basically the class that holds the information for the Dialysis Card
 */

public class SampleCard {
    private String sampleName;
    private String sampleStarted;
    private String sampleDaysLeft;
    private String sampleChangeAt;
    private String sampleNote;
//    private ArrayList<SampleCategory> sampleCategories = new ArrayList<SampleCategory>();

     public SampleCard(String sampleName, String sampleStarted, String sampleDaysLeft, String sampleChangeAt) {
        this.sampleName = sampleName;
        this.sampleStarted = sampleStarted;
        this.sampleChangeAt = sampleChangeAt;
        this.sampleDaysLeft = sampleDaysLeft;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleNote() {
        return sampleNote;
    }

    public void setSampleNote(String sampleNote) {
        this.sampleNote = sampleNote;
    }

    public String getSampleStarted() {
        return sampleStarted;
    }

    public void setSampleStarted(String sampleStarted) {
        this.sampleStarted = sampleStarted;
    }

    public String getSampleDaysLeft() {
        return sampleDaysLeft;
    }

    public void setSampleDaysLeft(String sampleDaysLeft) {
        this.sampleDaysLeft = sampleDaysLeft;
    }

    public String getSampleChangeAt() {
        return sampleChangeAt;
    }

    public void setSampleChangeAt(String sampleChangeAt) {
        this.sampleChangeAt = sampleChangeAt;
    }

//    public ArrayList<SampleCategory> getSampleCategories() {
//        return sampleCategories;
//    }
//
//    public void setSampleCategories(ArrayList<SampleCategory> sampleCategories) {
//        this.sampleCategories = sampleCategories;
//    }
}
