package com.example.midexam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<StudentModel> students;
    Context context;
    DBHelper dbHelper;

    public StudentAdapter(List<StudentModel> students, Context context) {
        this.students = students;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final StudentModel studentModel = students.get(position);
        holder.textViewID.setText(Integer.toString(studentModel.getId()));
        holder.editTextName.setText(studentModel.getName());
        holder.editTextStudentId.setText(studentModel.getStudentId());
        holder.checkEnrolled.setChecked(studentModel.isEnrolled());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = holder.editTextName.getText().toString();
                String strStudentId = holder.editTextStudentId.getText().toString();
                boolean enrolled = holder.checkEnrolled.isChecked();

                if (strName.isEmpty() || strStudentId.isEmpty()) {
                    Toast.makeText(context, "Enter data", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.updateStudent(new StudentModel(studentModel.getId(), strName, strStudentId, enrolled));
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteStudent(studentModel.getId());
                students.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID;
        EditText editTextName, editTextStudentId;
        CheckBox checkEnrolled;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextStudentId = itemView.findViewById(R.id.editTextStudentId);
            checkEnrolled = itemView.findViewById(R.id.checkEnrolled);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}