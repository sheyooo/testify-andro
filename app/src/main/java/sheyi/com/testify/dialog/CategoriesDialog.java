package sheyi.com.testify.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import sheyi.com.testify.R;
import sheyi.com.testify.callback.CategoryCallback;

public class CategoriesDialog extends DialogFragment {
    private CategoryCallback callback;

    public void setCallback(CategoryCallback callback) {
        this.callback = callback;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("My dialog")
                .setItems(R.array.nav_drawer_labels, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!

                        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
