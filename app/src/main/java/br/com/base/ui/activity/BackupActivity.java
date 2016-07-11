package br.com.base.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import br.com.base.R;
import br.com.base.dialog.ErrorDialog;

public class BackupActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private File destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        btnBack = (ImageButton) findViewById(R.id.back);

        setBtn();
    }

    private void setBtn() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    //Realiza o backup
    public void btnBackup(@SuppressWarnings("UnusedParameters") View view) {

        File file = getDatabasePath("demo.db");
        File root = Environment.getExternalStorageDirectory();
        destination = new File(root, "/backup_demo.db");
        try {
            //noinspection ResultOfMethodCallIgnored
            destination.createNewFile();
            FileUtils.copyFile(file, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ErrorDialog errorDialog = new ErrorDialog(this, "Backup concluido com sucesso!");
        errorDialog.setTripleButtonMessage("voltar", "OK", "Compartilhar");

        errorDialog.setOnTripleOptionListener(new ErrorDialog.OnTripleOptionListener() {

            @Override
            public void onPositiveButtonClick(AlertDialog dialog) {

            }

            @Override
            public void onNegativeButtonClick() {

            }

            @Override
            public void onNeutralButtonClick() {
                share(destination);
            }
        });
        errorDialog.createDialog().show();
        errorDialog.disableButton(DialogInterface.BUTTON_POSITIVE);

    }

    //compartilhar backup ou enviar por email
    private void share(File destination){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"emailteste@teste.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Backup database "+getString(R.string.app_name));

        if (!destination.exists() || !destination.canRead()) {
            Toast.makeText(this, "No sd-card", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.parse("file://" + destination.getAbsolutePath());
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        this.startActivity(Intent.createChooser(intent, "Compartilhar com:"));
    }

    //apagar todas as tabelas do banco de dados
    public void btnDropAllTables(@SuppressWarnings("UnusedParameters") View view) {

        ErrorDialog errorDialog = new ErrorDialog(this, "Está ação ira excluir definitivamente toda a base de dados. Deseja continuar? ");
        errorDialog.setTripleButtonMessage("voltar", "APAGAR", "cancelar");
        errorDialog.setOnTripleOptionListener(new ErrorDialog.OnTripleOptionListener() {

            @Override
            public void onPositiveButtonClick(AlertDialog dialog) {
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegativeButtonClick() {
                //apaga o banco de dados demo.db
                deleteDatabase("demo.db");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

            @Override
            public void onNeutralButtonClick() {


            }
        });
        errorDialog.createDialog().show();
        errorDialog.disableButton(DialogInterface.BUTTON_POSITIVE);

    }
}