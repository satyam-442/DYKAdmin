package com.example.dyk_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddBlogsActivity extends AppCompatActivity {

    Spinner selectCategory;
    TextView selectedCategory;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference factsRef;
    TextInputLayout heading, blog, writtenBy;
    Button addBlogsBtn;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blogs);

        loadingBar = new ProgressDialog(this);

        selectCategory = findViewById(R.id.selectCategory);
        selectedCategory = findViewById(R.id.selectedCategory);

        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(this, R.array.category_array_blog, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter4Category);
        selectCategory.setOnItemSelectedListener(new CategorySpinner());

        heading = findViewById(R.id.heading);
        blog = findViewById(R.id.blog);
        writtenBy = findViewById(R.id.writtenBy);
        addBlogsBtn = findViewById(R.id.addBlogsBtn);
        addBlogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String head = heading.getEditText().getText().toString();
                String post = blog.getEditText().getText().toString();
                String writer = writtenBy.getEditText().getText().toString();
                String cat = selectedCategory.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat time = new SimpleDateFormat("HH-MM-ss");//HOUR-MINUTE-SECOND
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");//HOUR-MINUTE-SECOND-MILLISECOND
                String currentTime = time.format(calendar.getTime());
                String currentDate = date.format(calendar.getTime());
                String blogID = cat.toUpperCase() + currentTime;
                if (head.isEmpty() && post.isEmpty() && writer.isEmpty()) {
                    Toast.makeText(AddBlogsActivity.this, "Field's are empty", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    HashMap factMap = new HashMap();
                    factMap.put("Blog", post);
                    factMap.put("writer", writer);
                    factMap.put("Heading", head);
                    factMap.put("Category", cat.trim());
                    factMap.put("Time", currentTime);
                    factMap.put("Date", currentDate);
                    factMap.put("BlogID", blogID);
                    db.collection("Blog").document(blogID).set(factMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(AddBlogsActivity.this, "Blog uploaded", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(AddBlogsActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    //Toast.makeText(AddFactsActivity.this, factID, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class CategorySpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            selectedCategory.setText(itemSpinner);
            /*if (itemSpinner.equals("Most Popular")) {
                selectedCategory.setText("MostPopular");
            } else if (itemSpinner.equals("World Culture")) {
                selectedCategory.setText("WorldCulture");
            } else {
                selectedCategory.setText(itemSpinner);
            }*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}