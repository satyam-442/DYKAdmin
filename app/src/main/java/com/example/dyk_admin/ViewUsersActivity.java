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

import com.example.dyk_admin.modal.Users;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewUsersActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RecyclerView allUsersList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        dialog = new ProgressDialog(this);

        usersRef = db.collection("Users");

        allUsersList = findViewById(R.id.allUsersList);
        allUsersList.setHasFixedSize(true);
        allUsersList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        Query query = usersRef.orderBy("Name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>().setQuery(query, Users.class).build();
        FirestoreRecyclerAdapter<Users, ViewUserHolder> fireAdapter = new FirestoreRecyclerAdapter<Users, ViewUserHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewUserHolder holder, int position, @NonNull Users model) {
                holder.userName.setText("Name: "+model.getName());
                //holder.factCategory.setText(model.getCategory());
                holder.userPhone.setText("Phone: "+model.getPhone());
                holder.userEmail.setText("Email: "+model.getEmail());
                dialog.dismiss();
            }

            @NonNull
            @Override
            public ViewUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_card,parent,false);
                return new ViewUserHolder(view);
            }
        };
        allUsersList.setAdapter(fireAdapter);
        fireAdapter.startListening();
    }

    class ViewUserHolder extends RecyclerView.ViewHolder{

        TextView userName, userPhone, userEmail;

        public ViewUserHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userPhone = itemView.findViewById(R.id.userPhone);
            userEmail = itemView.findViewById(R.id.userEmail);
        }
    }
}