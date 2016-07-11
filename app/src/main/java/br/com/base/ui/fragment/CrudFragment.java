package br.com.base.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.base.R;
import br.com.base.beans.Record;
import br.com.base.database.sqlite.DBDemoVersionAdpter;
import br.com.base.recyclerview.RecordsAdapter;
import br.com.base.ui.activity.NewContactActivity;

public class CrudFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView txvTotal;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crud, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_contacts);
        txvTotal = (TextView) rootView.findViewById(R.id.txvTotal);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.menu_item_new_contact).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_new_contact:
                //opção do menu de adicionar novo registro
                Intent intent = new Intent(getActivity(), NewContactActivity.class);
                intent.putExtra("tipo", "new");
                getActivity().startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        //inicializo a variavel do banco
        final DBDemoVersionAdpter db = new DBDemoVersionAdpter(getActivity());

        //cria a lista onde vai ser armazenado os registros
        List<Record> recordsList = new ArrayList<>();

        //inicializa o adapter do recyclerview
        final RecordsAdapter myAdapter = new RecordsAdapter(recordsList, getActivity(), txvTotal);

        //cria o recyclerview com um linearlayout
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(myAdapter);
        recordsList.clear();

        //Faz o select de todos os record do banco de dados sqlite e insere no recyclerview
        ArrayList<Record> records = db.getAllRecords();
        if (records != null && records.size() > 0) {
            for (Record record : records) {
                recordsList.add(record);
            }
        }
    }
}
