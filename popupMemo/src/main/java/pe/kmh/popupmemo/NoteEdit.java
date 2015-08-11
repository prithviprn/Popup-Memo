package pe.kmh.popupmemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteEdit extends Activity {

    String title;
    String body;
    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private NotesDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();

        setContentView(R.layout.notes_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        setTitle(R.string.edit_note);
        mTitleText = (EditText) findViewById(R.id.title);
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
            mTitleText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
            mBodyText.setText(note.getString(note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(NoteEdit.this, NoteMain.class));
            finish();
        } else if (id == R.id.action_save) {
            saveState();
        } else if (id == R.id.menu_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mBodyText.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMemo() {
        if (mRowId == null) {
            long id = mDbHelper.createNote(title, body);
            if (id > 0) {
                mRowId = id;
                Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            mDbHelper.updateNote(mRowId, title, body);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveState() {
        body = mBodyText.getText().toString();
        title = mTitleText.getText().toString();

        if (title.length() == 0 && body.length() == 0)  // 내용과 제목을 모두 입력 안했을때
            Toast.makeText(this, R.string.nobody, Toast.LENGTH_SHORT).show();

        else if (title.length() == 0) {
            if (body.length() > 15) { // 내용이 15자 이상일 경우
                title = body.substring(0, 15) + "...";
                title = title.replaceAll("[\r\n]", ""); // 엔터값 제거
                saveMemo();
            } else { // 내용이 15자 이하일 경우(StringIndexOutofBoundsException)
                title = body;
                title = title.replaceAll("[\r\n]", ""); // 엔터값 제거
                saveMemo();
            }
        }

        else if (body.length() == 0) {
            body = title;
            saveMemo();
        }

        else saveMemo();
    }
}
