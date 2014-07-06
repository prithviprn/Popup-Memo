package pe.kmh.popupmemo;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PopupScreen extends PopupActivity {

    public String title;
    public String body;
    private EditText mBodyText;
    private Long mRowId;
    private NotesDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isBind(true);
        isOnTitle(false);
        WindowBuild();
        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();


        setContentView(R.layout.popupscreen);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String color = sharedPref.getString("color", "#ffff99");
        RelativeLayout pLayout = (RelativeLayout) findViewById(R.id.popupscreen);
        pLayout.setBackgroundColor(Color.parseColor(color));
        Toast.makeText(getApplicationContext(), R.string.touchoutsidetosave, Toast.LENGTH_LONG).show();
        getLayout();
        mBodyText = (EditText) findViewById(R.id.body);
        mRowId = (savedInstanceState == null) ? null : (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID) : null;
        }
        populateFields();

    }

    @SuppressWarnings("deprecation")
    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            mBodyText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveMemo() {
        if (mRowId == null) {
            long id = mDbHelper.createNote(title, body);
            if (id > 0) {
                mRowId = id;
                Toast toast = Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            mDbHelper.updateNote(mRowId, title, body);
            Toast toast = Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void saveState() {
        body = mBodyText.getText().toString();
        title = body;
        if (body.length() == 0) { // 내용을 입력 안했을때
            Toast toast = Toast.makeText(this, R.string.nobody,
                Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (body.length() > 15) { // 내용이 15자 이상일 경우
                title = body.substring(0, 15) + "...";
                saveMemo();
            } else { // 내용이 15자 이하일 경우(StringIndexOutofBoundsException)
                title = body;
                saveMemo();
            }
        }
    }

}
