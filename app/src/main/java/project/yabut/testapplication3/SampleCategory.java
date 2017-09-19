package project.yabut.testapplication3;

import android.widget.RelativeLayout;

/**
 * Created by adria on 09-Aug-17.
 */

public class SampleCategory {

    private RelativeLayout relativeLayout;
    private String category;
    private String data;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }
}
