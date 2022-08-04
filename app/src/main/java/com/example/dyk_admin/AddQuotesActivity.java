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

public class AddQuotesActivity extends AppCompatActivity {

    Spinner selectCategory;
    TextView selectedCategory;
    Button addQuoteBtn;
    TextInputLayout quoteTextField, quoteWrittenBy;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference factsRef;
    ProgressDialog loadingBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quotes);

        loadingBar = new ProgressDialog(this);
        
        selectCategory = findViewById(R.id.selectCategory);
        selectedCategory = findViewById(R.id.selectedCategory);

        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(this, R.array.category_array_quote, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCategory.setAdapter(adapter4Category);
        selectCategory.setOnItemSelectedListener(new CategoryQuoteSpinner());

        quoteWrittenBy = findViewById(R.id.quoteWrittenBy);
        quoteTextField = findViewById(R.id.quoteTextField);
        addQuoteBtn = findViewById(R.id.addQuoteBtn);
        addQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = quoteTextField.getEditText().getText().toString();
                String writer = quoteWrittenBy.getEditText().getText().toString();
                String cat = selectedCategory.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat time = new SimpleDateFormat("HH-MM-ss");//HOUR-MINUTE-SECOND
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");//HOUR-MINUTE-SECOND-MILLISECOND
                String currentTime = time.format(calendar.getTime());
                String currentDate = date.format(calendar.getTime());
                String quoteID = cat.toUpperCase() + currentTime;
                if (quote.isEmpty() && writer.isEmpty()){
                    Toast.makeText(AddQuotesActivity.this, "Field's are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    HashMap quoteMap = new HashMap();
                    quoteMap.put("Quote", quote);
                    quoteMap.put("writer", writer);
                    quoteMap.put("Category", cat.trim());
                    quoteMap.put("Time", currentTime);
                    quoteMap.put("Date", currentDate);
                    quoteMap.put("QuoteID", quoteID);
                    db.collection("Quote").document(quoteID).set(quoteMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        loadingBar.dismiss();
                                        Toast.makeText(AddQuotesActivity.this, "Quote uploaded", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(AddQuotesActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        
    }

    private class CategoryQuoteSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            //selectedCategory.setText(itemSpinner);
            if (itemSpinner.equals("Heart Broken")) {
                selectedCategory.setText("HeartBroken");
            } else {
                selectedCategory.setText(itemSpinner);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}