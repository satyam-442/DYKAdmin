package com.example.dyk_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dyk_admin.modal.Facts;
import com.example.dyk_admin.modal.Users;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewFactsActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RecyclerView allFactList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference factsRef;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facts);

        category = getIntent().getStringExtra("category");
        dialog = new ProgressDialog(this);

        factsRef = db.collection(category);

        allFactList = findViewById(R.id.allFactList);
        allFactList.setHasFixedSize(true);
        allFactList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        startListen();
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        startListen();
    }

    private void startListen() {
        //Query query = factsRef.whereEqualTo("Category","TECHNOLOGY");
        Query query = factsRef.orderBy("Time", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Facts> options = new FirestoreRecyclerOptions.Builder<Facts>().setQuery(query, Facts.class).build();
        FirestoreRecyclerAdapter<Facts, ViewFactsHolder> fireAdapter = new FirestoreRecyclerAdapter<Facts, ViewFactsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewFactsHolder holder, int position, @NonNull Facts model) {
                holder.factContent.setText(model.getContent());
                //holder.factCategory.setText(model.getCategory());
                holder.factDate.setText("Date: "+model.getDate());
                holder.factTime.setText("Time: "+model.getTime());
                dialog.dismiss();
            }

            @NonNull
            @Override
            public ViewFactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_card,parent,false);
                return new ViewFactsHolder(view);
            }
        };
        allFactList.setAdapter(fireAdapter);
        fireAdapter.startListening();
    }

    class ViewFactsHolder extends RecyclerView.ViewHolder{

        TextView factContent, factCategory, factDate, factTime;

        public ViewFactsHolder(@NonNull View itemView) {
            super(itemView);
            factContent = itemView.findViewById(R.id.factContent);
            //factCategory = itemView.findViewById(R.id.factCategory);
            factDate = itemView.findViewById(R.id.factDate);
            factTime = itemView.findViewById(R.id.factTime);
        }
    }
}