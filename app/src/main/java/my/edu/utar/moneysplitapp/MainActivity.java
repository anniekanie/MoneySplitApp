package my.edu.utar.moneysplitapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    View.OnClickListener radioListener;
    View.OnClickListener btnListener;

    private TextView my_option_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioButton btn_equal = findViewById(R.id.btn_equal);
        RadioButton btn_separate = findViewById(R.id.btn_separate);
        RadioGroup rg_option = findViewById(R.id.rg_options);
        EditText input_nopeople = findViewById(R.id.input_nopeople);
        Button btn_nopeople = findViewById(R.id.btn_nopeople);
        TextView tv_additionalOption = findViewById(R.id.tv_additionalOption);
        TextView input_additionalOption = findViewById(R.id.input_additionalOption);
        EditText input_amount = findViewById(R.id.input_amount);
        Button submit = findViewById(R.id.btn_submit);
        Button calculate = findViewById(R.id.btn_calculate);

        DecimalFormat df = new DecimalFormat("0.00");

        //shows the rows in the table
        btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputPeople = input_nopeople.getText().toString().trim();

                if (!inputPeople.isEmpty()) {
                    try {
                        int numPeople = Integer.parseInt(inputPeople);

                        int generatedId = 1;        //id for dynamic generated text box
                        TableLayout tbl = findViewById(R.id.tableLayout);
                        tbl.removeAllViews(); // Clear the existing rows before adding new ones.

                        //generate dynamic table rows for user input
                        for (int i = 1; i <= numPeople; i++) {
                            TableRow tr = new TableRow(MainActivity.this);
                            TableLayout.LayoutParams tableRowParameter = new TableLayout.LayoutParams(
                                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

                            tableRowParameter.setMargins(10, 10, 0, 0);
                            tr.setLayoutParams(tableRowParameter);

                            // Set the text view
                            EditText input_name = new EditText(MainActivity.this);
                            input_name.setHint("Name");
                            input_name.setWidth(550);
                            input_name.setTextSize(15f);
                            input_name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            input_name.setId(generatedId);
                            tr.addView(input_name);

                            EditText input_value = new EditText(MainActivity.this);
                            input_value.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            input_value.setWidth(250);
                            input_value.setId(generatedId+1);
                            tr.addView(input_value);

                            TextView output_value = new TextView(MainActivity.this);
                            output_value.setWidth(250);
                            output_value.setText("0.0");
                            output_value.setId(generatedId+2);
                            tr.addView(output_value);

                            tbl.addView(tr, tableRowParameter);
                            generatedId+=3;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Please input number of people first! ", Toast.LENGTH_SHORT).show();
                        return;
                        }
                    }


                }
        };
        btn_nopeople.setOnClickListener(btnListener);

        //define a radio button listener
        radioListener = new View.OnClickListener(){
            @Override
            public void onClick(View view){

                RadioButton rb_selection = (RadioButton) view;

                //duplicated code
                String inputPeople = input_nopeople.getText().toString().trim();
                int numPeople = Integer.parseInt(inputPeople);

                // equal selection
                if (rb_selection.getId() == R.id.btn_equal){

                    tv_additionalOption.setVisibility(View.INVISIBLE);
                    input_additionalOption.setVisibility(View.INVISIBLE);

                    //disable user input for input_value column
                    for (int i =2; i <= numPeople * 3; i++) {
                        EditText input = (EditText) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));

                        if (input != null) {
                            input.setEnabled(false);
                        }
                        i=i+2;
                    }

                    // calculate the individual amount
                    if(input_amount.getText() != null && input_nopeople.getText().toString() != null){
                        double individualAmount = (double) Double.parseDouble(input_amount.getText().toString()) /
                                Double.parseDouble(input_nopeople.getText().toString());

                        //setting amount to text view
                        for (int i =3; i <= numPeople * 3; i++) {
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));
                            TextView input = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i-1), "id", getPackageName()));
                            if (output != null) {
                                output.setText( String.valueOf(df.format(individualAmount)));
                                input.setText( String.valueOf(df.format(individualAmount)));
                            }
                            i=i+2;
                        }
                    }
                }

                // separate calculation selected
                if(rb_selection.getId() == R.id.btn_separate){
                    //additional button only visible on separate button
                    tv_additionalOption.setVisibility(View.VISIBLE);
                    input_additionalOption.setVisibility(View.VISIBLE);
                    registerForContextMenu(input_additionalOption);
                }

                //when user has changed calculation option
                rg_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(RadioGroup group, int checkedId){
                        RadioButton rb = (RadioButton)findViewById(rg_option.getCheckedRadioButtonId());
                        //set the input_value and output_amount to empty
                        for (int i =1; i <= numPeople * 3; i+=3){
                            TextView name = findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));
                            TextView input = findViewById(getResources().getIdentifier(String.valueOf(i + 1), "id", getPackageName()));
                            TextView output = findViewById(getResources().getIdentifier(String.valueOf(i + 2), "id", getPackageName()));

                            name.setText("");
                            input.setText("");
                            input.setEnabled(true);
                            output.setText("0.0");
                        }
                    }
                });
            }
        };

        // activation
        btn_equal.setOnClickListener(radioListener);
        btn_separate.setOnClickListener(radioListener);

        // calculate button
        calculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String inputPeople = input_nopeople.getText().toString().trim();
                int numPeople = Integer.parseInt(inputPeople);

                //using percentage to calculate
                if(input_additionalOption.getText().toString().equals("Percentage")){
                    double total_percentage = 0.0;
                    //double max_percentage = 100.0;
                    for (int i =2; i <= numPeople * 3; i+=3) {
                        TextView input_percentage = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));

                        double input = (double)Integer.parseInt(input_percentage.getText().toString());

                        if(input_percentage.getText() != null){
                            total_percentage += input;

                            double individualPercentage = (double) Double.parseDouble(input_amount.getText().toString()) * Integer.parseInt(input_percentage.getText().toString()) / 100;
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i + 1), "id", getPackageName()));

                            if (output != null) {
                                output.setText(String.valueOf(individualPercentage));
                            }
                        }else{
                            double individualAmount =  0.0;
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i+1), "id", getPackageName()));


                            if (output != null) {
                                output.setText( String.valueOf(df.format(individualAmount)));
                            }
                        }
                    }
                    if (total_percentage != 100){
                        Toast.makeText(getApplicationContext(), "The total percentage should be equal to 100! ", Toast.LENGTH_SHORT).show();
                        for (int i =2; i <= numPeople * 3; i+=3){
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i+1), "id", getPackageName()));
                            TextView input_percentage = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));

                            if (output != null) {
                                output.setText("");
                                input_percentage.setText("");
                            }
                        }
                    }
                }

                //using amount to calculate
                else if(input_additionalOption.getText().toString().equals("Amount")){

                    double total_input = 0.0;
                    for (int i =2; i <= numPeople * 3; i++) {

                        TextView input_individualAmount = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));

                        if(input_individualAmount.getText() != null && !input_individualAmount.getText().toString().isEmpty()){
                            double individualAmount =  Double.parseDouble(input_individualAmount.getText().toString());
                            total_input += individualAmount;
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i+1), "id", getPackageName()));

                            if (output != null) {
                                output.setText( String.valueOf(df.format(individualAmount)));
                            }
                        }else{
                            double individualAmount =  0.0;
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i+1), "id", getPackageName()));

                            if (output != null) {
                                output.setText( String.valueOf(df.format(individualAmount)));
                            }
                        }

                        i=i+2;
                    }
                    // prompt error if the total input amount user entered on list not equal to the amount
                    if (total_input != (double) Double.parseDouble(input_amount.getText().toString())){
                        Toast.makeText(getApplicationContext(), "The total amount should be equal to the input amount! ", Toast.LENGTH_SHORT).show();
                        for (int i =2; i <= numPeople * 3; i+=3){
                            TextView output = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i+1), "id", getPackageName()));
                            TextView input_amount = (TextView) findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));

                            if (output != null) {
                                output.setText("");
                                input_amount.setText("");
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please choose a separation method ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //submit button
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                String amount = input_amount.getText().toString().trim();
                intent.putExtra("amount",amount);

                String inputPeople = input_nopeople.getText().toString().trim();
                int numPeople = Integer.parseInt(inputPeople);

                intent.putExtra("numPeople", inputPeople);
                int count = 1;
                for (int i =1; i <= numPeople * 3; i+=3){

                    TextView name = findViewById(getResources().getIdentifier(String.valueOf(i), "id", getPackageName()));
                    TextView input = findViewById(getResources().getIdentifier(String.valueOf(i + 1), "id", getPackageName()));
                    TextView output = findViewById(getResources().getIdentifier(String.valueOf(i + 2), "id", getPackageName()));

                    intent.putExtra("name" + count, name.getText().toString());
                    intent.putExtra("input" + count,input.getText().toString());
                    intent.putExtra("output" + count, output.getText().toString());
                    count ++;
                }
                startActivity(intent);
            }
        });
     }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if(v.getId() == R.id.input_additionalOption)
        {
            //class, id, order, title
            menu.add(0,1,1,"Percentage");
            menu.add(0,2,2,"Amount");
            menu.setHeaderIcon(android.R.drawable.btn_star).setHeaderTitle("Choose a method ...");

        }
    }

    //to define the listener for the input_additionalOption
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        my_option_selection = findViewById(R.id.input_additionalOption);

        switch(item.getItemId())
        {
            case 1:
                //set to user only can enter percentage
                //my_option_selection.setBackgroundColor(Color.RED);
                Toast.makeText(this, "Using Percentage to Calculate", Toast.LENGTH_SHORT).show();
                my_option_selection.setText("Percentage");

                return true;

            case 2:
                //set to user can only enter amount
                //my_option_selection.setBackgroundColor(Color.GREEN);
                Toast.makeText(this, "Using Amount to Calculate", Toast.LENGTH_SHORT).show();
                my_option_selection.setText("Amount");

                return true;
        }
        return super.onContextItemSelected(item);
    }

    //navigation menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.firstPage){
            return true;
        }
        else if(item.getItemId() == R.id.historyPage){
            Intent intent = new Intent(MainActivity.this, MainActivity3.class);
            startActivity(intent);
        }
        return true;
    }

}