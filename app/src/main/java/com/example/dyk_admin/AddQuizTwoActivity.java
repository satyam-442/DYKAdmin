package com.example.dyk_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.dyk_admin.adapter.QuizAdapter;
import com.example.dyk_admin.modal.Quiz;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddQuizTwoActivity extends AppCompatActivity {

    ImageView addCategoryImg;

    String category;
    RecyclerView rv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference categories;
    CollectionReference setsRef;
    public static List<Quiz> list;
    Dialog addCategoryDialog;
    ProgressDialog loadingBar;

    //DIALOG FOR ADD CATEGORY
    EditText addCatName;
    CircleImageView addCategoryImage;
    Button addCategory;

    //UPLOAD IMAGE VARIABLES DECLARATION
    Uri image;
    String downloadUrl;

    QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_two);

        loadingBar = new ProgressDialog(this);

        addCategoryImg = findViewById(R.id.addCategoryImg);
        addCategoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategoryDialog.show();
            }
        });

        category = getIntent().getStringExtra("category");

        categories = db.collection(category);
        setsRef = db.collection(category);

        setCategoryDaialog();

        rv = findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);

        list = new ArrayList<>();

        adapter = new QuizAdapter(list, new QuizAdapter.DeleteListener() {
            @Override
            public void onDelete(final String key, final int position) {
                new AlertDialog.Builder(AddQuizTwoActivity.this, com.google.android.material.R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("Delete Category")
                        .setMessage("Press confirm to delete or cancel to be safe")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadingBar.show();
                                /*categories.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            for (String setIds : list.get(position).getSetss()){
                                                setsRef.child(setIds).removeValue();
                                            }
                                            list.remove(position);
                                            adapter.notifyDataSetChanged();
                                            loadingBar.dismiss();
                                        } else {
                                            Toast.makeText(CategoryActivity.this, "Error Occurred! :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });*/
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setIcon(R.drawable.warning)
                        .show();
            }
        });
        rv.setAdapter(adapter);

        /*categories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    List<String> sets = new ArrayList<>();
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Sets").getChildren()
                    ){
                        sets.add(dataSnapshot2.getKey());
                    }
                    list.add(new CategoryModel(dataSnapshot1.child("name").getValue().toString(),
                            dataSnapshot1.child("url").getValue().toString(),
                            dataSnapshot1.getKey(),
                            sets
                    ));
                }
                adapter.notifyDataSetChanged();
                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CategoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                finish();
            }
        });*/
    }

    private void setCategoryDaialog() {
        addCategoryDialog = new Dialog(this);
        addCategoryDialog.setContentView(R.layout.add_category_layout);
        addCategoryDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.loading_design));
        addCategoryDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addCategoryDialog.setCancelable(true);

        addCategoryImage = addCategoryDialog.findViewById(R.id.image);
        addCatName = addCategoryDialog.findViewById(R.id.categoryname);
        addCategory = addCategoryDialog.findViewById(R.id.add);

        addCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 101);
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCatName.getText().toString().isEmpty() || addCatName.getText() == null) {
                    addCatName.setError("Required");
                    return;
                }
                for (Quiz model : list) {
                    if (addCatName.getText().toString().equals(model.getNamee())) {
                        addCatName.setError("Category Already Present!");
                        return;
                    }
                }
                if (image == null) {
                    Toast.makeText(AddQuizTwoActivity.this, "Please select Image", Toast.LENGTH_LONG).show();
                    return;
                }
                addCategoryDialog.dismiss();
                uploadData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                image = data.getData();
                addCategoryImage.setImageURI(image);
            }
        }
    }

    private void uploadData() {
        loadingBar.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference imageRef = storageReference.child(category).child(image.getLastPathSegment());

        UploadTask uploadTask = imageRef.putFile(image);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUrl = task.getResult().toString();
                            uploadCategoryName();
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(AddQuizTwoActivity.this, "Error occurred!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    Toast.makeText(AddQuizTwoActivity.this, "Error occurred!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void uploadCategoryName() {
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("name", addCatName.getText().toString());
        categoryMap.put("Sets", 0);
        categoryMap.put("url", downloadUrl);

        final String id = UUID.randomUUID().toString();

        db.collection(category+"Quiz").document(id).set(categoryMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    list.add(new Quiz(addCatName.getText().toString(), downloadUrl, id, new ArrayList<String>()));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddQuizTwoActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                loadingBar.dismiss();
            }
        });
    }

}