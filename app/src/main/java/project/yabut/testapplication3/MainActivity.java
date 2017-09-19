package project.yabut.testapplication3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlertDialog.Builder alertDialogAdd;
    private AlertDialog.Builder alertDialogNote;
    private EditText new_sample_note;
    private EditText new_sample_dialog_sample_name;
    private EditText new_sample_dialog_sample_start;
    private EditText new_sample_dialog_sample_days_left;
    private EditText new_sample_dialog_sample_change_at;
    private int edit_position;
    private View viewAddDialog;
    private View viewNoteDialog;
    private boolean add = false;
    private Paint p = new Paint();
    // This class will handle storing deleted objects
    private UndoHandler undoHandler;

    // Build Data Set
    ArrayList<SampleCard> myDataSet = new ArrayList<SampleCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        initDialogAdd();
        initDialogNote();
        // Instantiate the UndoHandler
        undoHandler = new UndoHandler(this.getApplicationContext(), myDataSet);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SaveAndLoad.saveArraySampleCard(this.getApplicationContext(), myDataSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveAndLoad.saveArraySampleCard(this.getApplicationContext(), myDataSet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load up an array if one doesn't exist
        if (myDataSet.isEmpty()) {
            SaveAndLoad.loadArraySampleCard(this.getApplicationContext(), myDataSet);
            // Remember to notify the adapter that the data set has changed!
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        // Set up the floating action button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewAddDialog();
                add = true;
                alertDialogAdd.setTitle("Add Sample");
                new_sample_dialog_sample_name.setText("");
                new_sample_dialog_sample_start.setText("");
                new_sample_dialog_sample_days_left.setText("");
                new_sample_dialog_sample_change_at.setText("");
                alertDialogAdd.show();
                // Brings up keyboard
                bringKeyboardUp(true);
            }
        });

        // RecyclerView stuff
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SampleAdapter(this.getApplicationContext(), myDataSet);
        mRecyclerView.setAdapter(mAdapter);
        // Make FAB disappear when scrolling
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }

            // This is basically just a minor bug fix.  Not that important
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    if (fab.getVisibility() == View.GONE) {
                        fab.show();
                    }
                }
            }
        });

        // Set up Click Listener for recycler view
        ItemClickSupport.addTo(mRecyclerView).

                setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        removeViewNoteDialog();
                        alertDialogNote.setTitle("Note");
                        // This is necessary so that the alertDialogNote builder knows where to save the text to
                        edit_position = position;
                        // If there is text in the sampleNote state, then set it.  If not, then to avoid
                        // null exceptions, we use the if/else statement
                        if (myDataSet.get(position).getSampleNote() == null) {
                            new_sample_note.setText("");
                            alertDialogNote.show();
                        } else {
                            new_sample_note.setText(myDataSet.get(position).getSampleNote());
                            alertDialogNote.show();
                        }
                    }
                });

        // Initialize the touch to swipe goodness
        initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                // Store the item being removed into the UndoHandler so it can be retrieved again
                undoHandler.storeToUndoHandler(myDataSet.get(position), position);
                myDataSet.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, myDataSet.size());
            }

            // Draws the rectangle as the thingy gets swiped away
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dx > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        // Size the rectangle by basing it off the itemView
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dx, (float) itemView.getBottom());
                        // Draw the rectangle background
                        c.drawRect(background, p);
                        // Get the image
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        // Create the rectangle that contains the image
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        // Draw the image
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dx, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_24dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
            }
        };
        // Attach the helper!
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void removeViewAddDialog() {
        if (viewAddDialog.getParent() != null) {
            ((ViewGroup) viewAddDialog.getParent()).removeView(viewAddDialog);
        }
    }

    private void removeViewNoteDialog() {
        if (viewNoteDialog.getParent() != null) {
            ((ViewGroup) viewNoteDialog.getParent()).removeView(viewNoteDialog);
        }
    }

    // Initialize the alert dialog for adding samples
    private void initDialogAdd() {
        alertDialogAdd = new AlertDialog.Builder(this);
        viewAddDialog = getLayoutInflater().inflate(R.layout.dialog_add_layout, null);
        alertDialogAdd.setView(viewAddDialog);
        alertDialogAdd.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (add) {
                    add = false;
                    SampleCard sampleCard = new SampleCard(new_sample_dialog_sample_name.getText().toString(),
                            new_sample_dialog_sample_start.getText().toString(),
                            new_sample_dialog_sample_days_left.getText().toString(),
                            new_sample_dialog_sample_change_at.getText().toString());
                    mAdapter.addItem(sampleCard);
                    // Bring down keyboard
                    bringKeyboardUp(false);
                    dialog.dismiss();
                } else {
                    // This portion handles EDITING an item
                    SampleCard sampleCard = new SampleCard(new_sample_dialog_sample_name.getText().toString(),
                            new_sample_dialog_sample_start.getText().toString(),
                            new_sample_dialog_sample_days_left.getText().toString(),
                            new_sample_dialog_sample_change_at.getText().toString());
                    myDataSet.set(edit_position, sampleCard);
                    mAdapter.notifyDataSetChanged();
                    bringKeyboardUp(false);
                    dialog.dismiss();
                }
            }
        });
        new_sample_dialog_sample_name = (EditText) viewAddDialog.findViewById(R.id.new_sample_dialog_sample_name);
        new_sample_dialog_sample_start = (EditText) viewAddDialog.findViewById(R.id.new_sample_dialog_sample_start);
        new_sample_dialog_sample_days_left = (EditText) viewAddDialog.findViewById(R.id.new_sample_dialog_sample_days_left);
        new_sample_dialog_sample_change_at = (EditText) viewAddDialog.findViewById(R.id.new_sample_dialog_sample_change_at);
    }

    private void initDialogNote() {
        // Same as the builder above sorta
        alertDialogNote = new AlertDialog.Builder(this);
        viewNoteDialog = getLayoutInflater().inflate(R.layout.dialog_note_layout, null);
        alertDialogNote.setView(viewNoteDialog);
        alertDialogNote.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Set the text in the sampleNote state!
                myDataSet.get(edit_position).setSampleNote(new_sample_note.getText().toString());
                dialog.dismiss();
            }
        });
        new_sample_note = (EditText) viewNoteDialog.findViewById(R.id.note_text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_undo) {
            // Call undo
            undoHandler.undo();
            // The adapter MUST be called after calling undo, or the app will crash.  Technically
            // I should code this functionality into undoHandler, but idk if I can pass a SampleAdapter class
            // in by reference and still call its methods properly.  W.e. I guess.
            mAdapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param bringKeyboardUp if true, brings keyboard up, if false brings keyboard down
     */
    public void bringKeyboardUp(boolean bringKeyboardUp) {
        if (bringKeyboardUp) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewAddDialog.getWindowToken(), 0);
        }
    }
}
