package joesticoe.mobile.todolistappuas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rv_Adapter extends RecyclerView.Adapter<rv_Adapter.rv_ViewHolder> {

    private List<ItemTodoList> itemTodoLists;
    Context context;

    public rv_Adapter(Context context, List<ItemTodoList> itemTodoLists){
        this.itemTodoLists = itemTodoLists;
        this.context = context;
    }

    @NonNull
    @Override
    public rv_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todolist, parent, false);
        return new rv_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rv_Adapter.rv_ViewHolder holder, int position) {

        ItemTodoList itemTodoList = itemTodoLists.get(position);
        holder.todo.setText(itemTodoList.getWhat());
        holder.time.setText(itemTodoList.getTime());
        if(holder.checkBox.isChecked()){
            holder.status.setText("Done");
        }else {
            holder.status.setText("Waiting List");
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.status.setText("Done");
                    holder.status.setTextColor(context.getResources().getColor(R.color.green));
                }else {
                    holder.status.setText("Waiting List");
                    holder.status.setTextColor(context.getResources().getColor(R.color.red));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTodoLists.size();
    }

    public class rv_ViewHolder extends RecyclerView.ViewHolder {

        TextView todo, time, status;
        CheckBox checkBox;
        public rv_ViewHolder(View view) {
            super(view);
            todo = view.findViewById(R.id.txt_todo);
            time = view.findViewById(R.id.txt_time);
            status = view.findViewById(R.id.txt_status);
            checkBox = view.findViewById(R.id.checkBox);
        }
    }
}
