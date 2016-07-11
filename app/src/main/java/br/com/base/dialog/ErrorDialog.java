package br.com.base.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import br.com.base.R;

public class ErrorDialog {

    private final AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private OnTripleOptionListener onTripleOptionListener;
    private String positiveBtn, negativeBtn, neutralBtn;

    public ErrorDialog(Context context, String msg) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setIcon(R.drawable.ic_warning_48dp);
        dialogBuilder.setTitle("Atenção!");
        dialogBuilder.setMessage(msg);
    }

    public void setView(View view) {
        dialogBuilder.setView(view);
    }

    public void setTripleButtonMessage(String positiveBtn, String negativeBtn, String neutralBtn) {
        this.positiveBtn = positiveBtn;
        this.negativeBtn = negativeBtn;
        this.neutralBtn = neutralBtn;
    }

    public void setOnTripleOptionListener(OnTripleOptionListener onTripleOptionListener) {
        this.onTripleOptionListener = onTripleOptionListener;
    }

    public ErrorDialog createDialog() {
        dialogBuilder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                if (onTripleOptionListener != null) {
                    onTripleOptionListener.onPositiveButtonClick(dialog);
                }
            }
        }).setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                if (onTripleOptionListener != null) {
                    onTripleOptionListener.onNegativeButtonClick();
                }
            }
        }).setNeutralButton(neutralBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                if (onTripleOptionListener != null) {
                    onTripleOptionListener.onNeutralButtonClick();
                }
            }
        });
        return this;
    }

    public void disableButton(int dialogButton) {
        dialog.getButton(dialogButton).setVisibility(View.GONE);
    }

    public void show() {
        dialog = dialogBuilder.create();
        dialog.show();
    }

    @SuppressWarnings("EmptyMethod")
    public interface OnTripleOptionListener {

        void onPositiveButtonClick(AlertDialog dialog);

        void onNegativeButtonClick();

        void onNeutralButtonClick();
    }
}
