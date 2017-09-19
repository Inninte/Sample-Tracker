package project.yabut.testapplication3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

/**
 * Created by adria on 26-Jul-17.
 */

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleCardTwoViewHolder> {

    private ArrayList<SampleCard> mDataSet;
    private Context mContext;
    // Helps save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    // Provide a suitable constructor (depends on the kind of dataset)
    public SampleAdapter(Context context, ArrayList<SampleCard> myDataset) {
        mContext = context;
        mDataSet = myDataset;
    }


    public static class SampleCardTwoViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        RelativeLayout expandButton;
        RelativeLayout extendedSampleInfo;
        TextView expandButtonText;
        TextView addItemButton;
        EditText sampleName;
        EditText sampleStart;
        EditText sampleDaysLeft;
        EditText sampleChangeAt;

        SampleCardTwoViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.dialysis_card_view);
            expandButton = (RelativeLayout) itemView.findViewById(R.id.expand_button);
            extendedSampleInfo = (RelativeLayout) itemView.findViewById(R.id.extension_sample_view);
            expandButtonText = (TextView) itemView.findViewById(R.id.expand_text);
            addItemButton = (TextView) itemView.findViewById(R.id.add_item);
            sampleName = (EditText) itemView.findViewById(R.id.sample_name);
            sampleStart = (EditText) itemView.findViewById(R.id.sample_start);
            sampleDaysLeft = (EditText) itemView.findViewById(R.id.sample_days_left);
            sampleChangeAt = (EditText) itemView.findViewById(R.id.sample_change_at);
        }
    }

    public SampleCardTwoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_card_2, parent, false);
        // set the view's size, margins, paddings and layout parameters
        SampleCardTwoViewHolder dcvh = new SampleCardTwoViewHolder(v);
        return dcvh;
    }

    @Override
    public void onBindViewHolder(final SampleAdapter.SampleCardTwoViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // - also setup any listeners you want
        final SampleCard di = mDataSet.get(position);
        final SampleCategoryHandler sch = new SampleCategoryHandler(di);

        //Set up the onClick listeners
        holder.expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.extendedSampleInfo.getVisibility() == View.VISIBLE) {
                    holder.expandButtonText.setText("More Info");
                    collapse(holder.extendedSampleInfo);
                } else {
                    holder.expandButtonText.setText("Less Info");
                    expand(holder.extendedSampleInfo);
                }
            }
        });

        holder.sampleName.setText(mDataSet.get(position).getSampleName());
        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
        holder.sampleName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                di.setSampleName(holder.sampleName.getText().toString());
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                holder.sampleName.setFocusable(false);
                holder.sampleName.setFocusableInTouchMode(true);
                return false;
            }
        });
        holder.sampleStart.setText(mDataSet.get(position).getSampleStarted());
        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
        holder.sampleStart.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                di.setSampleStarted(holder.sampleStart.getText().toString());
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                holder.sampleStart.setFocusable(false);
                holder.sampleStart.setFocusableInTouchMode(true);
                return false;
            }
        });
        holder.sampleDaysLeft.setText(mDataSet.get(position).getSampleDaysLeft());
        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
        holder.sampleDaysLeft.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                di.setSampleDaysLeft(holder.sampleDaysLeft.getText().toString());
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                holder.sampleDaysLeft.setFocusable(false);
                holder.sampleDaysLeft.setFocusableInTouchMode(true);
                return false;
            }
        });
        holder.sampleChangeAt.setText(mDataSet.get(position).getSampleChangeAt());
        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
        holder.sampleChangeAt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                di.setSampleChangeAt(holder.sampleChangeAt.getText().toString());
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                holder.sampleChangeAt.setFocusable(false);
                holder.sampleChangeAt.setFocusableInTouchMode(true);
                return false;
            }
        });
    }


    // Comment the above code and uncomment all the code below to use sample_card.xml as the layout and
    // change the extension of the class to RecyclerView.Adapter<SampleAdapter.SampleCardTwoViewHolder>


    //    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class SampleCardViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout linearLayout;
//        EditText sampleName;
//        EditText sampleStart;
//        EditText sampleDaysLeft;
//        EditText sampleChangeAt;
//
//        SampleCardViewHolder(View itemView) {
//            super(itemView);
//            linearLayout = (LinearLayout) itemView.findViewById(R.id.dialysis_card_view);
//            sampleName = (EditText) itemView.findViewById(R.id.sample_name);
//            sampleStart = (EditText) itemView.findViewById(R.id.sample_start);
//            sampleDaysLeft = (EditText) itemView.findViewById(R.id.sample_days_left);
//            sampleChangeAt = (EditText) itemView.findViewById(R.id.sample_change_at);
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public SampleCardViewHolder onCreateViewHolder(ViewGroup parent,
//                                                   int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.sample_card, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        SampleCardViewHolder dcvh = new SampleCardViewHolder(v);
//        return dcvh;
//    }

//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(final SampleCardViewHolder holder, int position) {
//
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        // - also setup any listeners you want
//        final SampleCard di = mDataSet.get(position);
//        holder.sampleName.setText(mDataSet.get(position).getSampleName());
//        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
//        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
//        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
//        holder.sampleName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                di.setSampleName(holder.sampleName.getText().toString());
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                holder.sampleName.setFocusable(false);
//                holder.sampleName.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//        holder.sampleStart.setText(mDataSet.get(position).getSampleStart());
//        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
//        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
//        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
//        holder.sampleStart.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                di.setSampleStart(holder.sampleStart.getText().toString());
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                holder.sampleStart.setFocusable(false);
//                holder.sampleStart.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//        holder.sampleDaysLeft.setText(mDataSet.get(position).getSampleDaysLeft());
//        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
//        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
//        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
//        holder.sampleDaysLeft.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                di.setSampleDaysLeft(holder.sampleDaysLeft.getText().toString());
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                holder.sampleDaysLeft.setFocusable(false);
//                holder.sampleDaysLeft.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//        holder.sampleChangeAt.setText(mDataSet.get(position).getSampleChangeAt());
//        // This onEditorActionListener listens for the press of the Done (checkmark) button on the keyboard
//        // Upon seeing that, it edits the contents of the SampleCard object appropriately.  If the done
//        // button is NOT pressed, then the contents of the SampleCard object will NOT change.
//        holder.sampleChangeAt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                di.setSampleChangeAt(holder.sampleChangeAt.getText().toString());
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                holder.sampleChangeAt.setFocusable(false);
//                holder.sampleChangeAt.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // Add item

    public void addItem(SampleCard di) {
        mDataSet.add(di);
        notifyItemInserted(mDataSet.size());
    }

    // Remove item
    public void removeItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataSet.size());
    }

    // These following classes are for animations (taken from Stack Overflow)
    public static void expand(final View v) {
        v.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RecyclerView.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}