package br.com.base.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.base.R;
import br.com.base.beans.Record;
import br.com.base.database.sqlite.DBDemoVersionAdpter;
import br.com.base.ui.activity.NewContactActivity;

import static br.com.base.extras.Functions.decimalFormat;
import static br.com.base.extras.Functions.decimalFormatMoney;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder> {

    private final List<Record> recordList;
    private final Context context;
    private final DBDemoVersionAdpter db;
    private final Vibrator vb;
    private final TextView txvTotal;

    public RecordsAdapter(List<Record> recordList, Context context, TextView txv) {
        this.recordList = recordList;
        this.context = context;
        db = new DBDemoVersionAdpter(this.context);
        vb = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        txvTotal = txv;
    }

    @Override
    public RecordsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.record_list_row, parent, false);
        return new MyViewHolder(itemView);
    }


    //altera um registro do recyclerview
    private void changeCard(int position, Double qtd) {
        recordList.get(position).setQuantidade(qtd);
        notifyItemChanged(position);
    }

    //Remove um registro do recyclerview
    private void removeCard(int postition) {
        recordList.remove(postition);
        notifyItemRemoved(postition);
    }

    private String getValorTotal() {

        double t = 0;

        //calcula o valor total
        for (Record record : recordList) {
            t += record.getQuantidade() * record.getPrice();
        }
        return decimalFormatMoney(t);
    }

    @Override
    public void onBindViewHolder(RecordsAdapter.MyViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.txvId.setText(context.getString(R.string.lbl_cod) + String.valueOf(record.getId()));
        holder.txvName.setText(context.getString(R.string.lbl_item) + record.getName());
        holder.txvPrice.setText(context.getString(R.string.lbl_valor_unitario) + decimalFormatMoney(record.getPrice()));
        holder.txvQtd.setText(context.getString(R.string.lbl_x) + decimalFormat(record.getQuantidade()));
        holder.txvTotalCard.setText(context.getString(R.string.lbl_sub_total) + decimalFormatMoney(record.getQuantidade() * record.getPrice()));
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView txvId, txvName, txvPrice, txvQtd, txvObs, txvReferencia, txvTotalCard;
        public final ImageButton btnEdit, btnDelete, btnAddQtd, btnSubQtd, btnObs;
        public final LinearLayout linearObs;

        public MyViewHolder(View view) {
            super(view);

            txvId = (TextView) view.findViewById(R.id.txvId);
            txvName = (TextView) view.findViewById(R.id.txvnome);
            txvPrice = (TextView) view.findViewById(R.id.txvPrice);
            txvQtd = (TextView) view.findViewById(R.id.txvQtd);
            txvObs = (TextView) view.findViewById(R.id.txvObs);
            txvReferencia = (TextView) view.findViewById(R.id.txvReferencia);
            txvTotalCard = (TextView) view.findViewById(R.id.txvTotalCard);

            btnEdit = (ImageButton)view.findViewById(R.id.btn_edtit_card);
            btnDelete = (ImageButton)view.findViewById(R.id.btn_delete_card);
            btnAddQtd = (ImageButton)view.findViewById(R.id.btnAddQtd);
            btnSubQtd = (ImageButton)view.findViewById(R.id.btnSubQtd);
            btnObs = (ImageButton)view.findViewById(R.id.btnObs);

            linearObs = (LinearLayout) view.findViewById(R.id.linearObs);

            txvTotal.setText(context.getString(R.string.lbl_total) + getValorTotal());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vb.vibrate(50);

                    //Passa os dados para a proxima tela para fazer a edicao do registro
                    Record record = recordList.get(getAdapterPosition());
                    Intent intent = new Intent(context, NewContactActivity.class);
                    intent.putExtra("tipo", "edit");
                    intent.putExtra("id", record.getId());
                    intent.putExtra("nome", record.getName());
                    intent.putExtra("price", record.getPrice());
                    intent.putExtra("qtd", record.getQuantidade());
                    intent.putExtra("obs", record.getObs());
                    intent.putExtra("ref", record.getRef());
                    context.startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vb.vibrate(50);
                    Record record = recordList.get(getAdapterPosition());

                    //deleta um registro do banco de dados passando seu id
                    db.deleteRecord(record.getId());
                    removeCard(getAdapterPosition());
                    txvTotal.setText(context.getString(R.string.lbl_total) + getValorTotal());
                }
            });

            btnAddQtd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Record record = recordList.get(getAdapterPosition());
                    double quantidade = record.getQuantidade();

                    //adiciona uma quantidade no registro do card clicado
                    db.updateRecord(record.getId(), record.getName(), record.getPrice(), quantidade++);
                    changeCard(getAdapterPosition(), quantidade);
                    txvTotal.setText(context.getString(R.string.lbl_total) + getValorTotal());
                }
            });

            btnSubQtd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Record record = recordList.get(getAdapterPosition());
                    double quantidade = record.getQuantidade();

                    if (((quantidade - 1) >= 1)) {
                        quantidade--;
                    } else {
                        quantidade = 1.0;
                    }

                    //Subtrai uma quantidade do registro clicado
                    db.updateRecord(record.getId(), record.getName(), record.getPrice(), quantidade);
                    changeCard(getAdapterPosition(), quantidade);
                    txvTotal.setText(context.getString(R.string.lbl_total) + getValorTotal());
                }
            });
        }
    }
}
