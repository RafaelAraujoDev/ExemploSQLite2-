package br.com.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.base.R;
import br.com.base.database.sqlite.DBDemoVersionAdpter;

public class NewContactActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private int id;
    private String tipo;
    private EditText editText_name, editText_valor, editText_qtd;
    private Button button_add;
    private TextView lblTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_contact);

        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_valor = (EditText) findViewById(R.id.editText_valor);
        editText_qtd = (EditText) findViewById(R.id.editText_qtd);

        button_add = (Button) findViewById(R.id.button_add);
        btnBack = (ImageButton) findViewById(R.id.back);
        lblTitle = (TextView) findViewById(R.id.lblTitle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (!(toolbar == null)) setSupportActionBar(toolbar);

        getBundle();

        setBtn();

        if (button_add != null) {
            button_add.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (editText_valor != null && editText_valor.getText().toString().equals("")) {
                        editText_valor.setText("1.0");
                    }

                    if (editText_qtd != null && editText_qtd.getText().toString().equals("")) {
                        editText_qtd.setText("1.0");
                    }

                    //obtem os valores digitados pelo usuario
                    final String edt_name = editText_name != null ? editText_name.getText().toString() : null;
                    final double edt_valor = Double.parseDouble(String.valueOf(editText_valor != null ? editText_valor.getText().toString() : 1.0));
                    final double edt_qtd = Double.parseDouble(String.valueOf(editText_qtd != null ? editText_qtd.getText().toString() : 1.0));

                    //Adiciona o registro no banco de dados
                    insertOrUpdate(edt_name, edt_valor, edt_qtd);
                }
            });
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            tipo = (String) bundle.get("tipo");

            if (tipo != null) {
                if (tipo.equals("edit")) {
                    id = (int) bundle.get("id");
                    if (editText_name != null) editText_name.setText((String) bundle.get("nome"));

                    String valor = String.valueOf((double) bundle.get("price"));
                    if (editText_valor != null) editText_valor.setText(valor);

                    String qtd = String.valueOf((double) bundle.get("qtd"));
                    if (editText_qtd != null) editText_qtd.setText(qtd);

                    button_add.setText("Alterar");
                    lblTitle.setText("Editar");

                } else if (tipo.equals("new")) {
                    button_add.setText("Adicionar");
                    lblTitle.setText("New");
                }
            }
        }
    }

    //Faz um insert ou atualiza os dados
    private void insertOrUpdate(String name, double price, double qtd) {
        DBDemoVersionAdpter mydb = new DBDemoVersionAdpter(this);

        if (tipo.equals("edit")) {
            mydb.updateRecord(id, name, price, qtd);
        } else {
            mydb.insertDemoVersion(name, price, qtd);
        }

        onBackPressed();
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
}
