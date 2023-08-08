package my.edu.utar.moneysplitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private SQLiteAdapter mySQLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn_save = findViewById(R.id.savebutton);
        Intent intent = getIntent();
        String amount = intent.getStringExtra("amount");
        String numPeople = intent.getStringExtra("numPeople");
        String[] name = new String[Integer.parseInt(numPeople)];
        String[] input = new String[Integer.parseInt(numPeople)];
        String[] output = new String[Integer.parseInt(numPeople)];

        //storing data into array by getting from intent
        int people = Integer.parseInt(numPeople);
        for (int i = 0; i < people; i++) {
            name[i] = intent.getStringExtra("name"+(1+i));
            input[i] = intent.getStringExtra("input" +(1+i));
            output[i] = intent.getStringExtra("output"+(1+i));
        }

        TableLayout tbl = findViewById(R.id.tableLayout2);
        tbl.removeAllViews(); // Clear the existing rows before adding new ones.

        //storing the id of each widget
        int[] nameIds = new int[people];
        int[] inputIds = new int[people];
        int[] outputIds = new int[people];

        for (int i = 1; i <= people; i++) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParameter = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

            tableRowParameter.setMargins(20, 20, 20, 0);
            tr.setLayoutParams(tableRowParameter);

            // Set the text view
            TextView input_name = new TextView(this);
            input_name.setWidth(500);
            input_name.setTextSize(25f);
            input_name.setId(View.generateViewId());
            nameIds[i - 1] = input_name.getId();            //stores the id into the array
            tr.addView(input_name);

            TextView input_value = new TextView(this);
            input_value.setWidth(250);
            input_value.setTextSize(25f);
            input_value.setId(View.generateViewId());
            inputIds[i - 1] = input_value.getId();      //stores the id into the array
            tr.addView(input_value);

            TextView output_value = new TextView(this);
            output_value.setWidth(250);
            output_value.setTextSize(25f);
            output_value.setId(View.generateViewId());
            outputIds[i - 1] = output_value.getId();        //stores the id into the array
            tr.addView(output_value);

            tbl.addView(tr, tableRowParameter);
        }

        for (int i = 0; i < people; i++) {

            //put each data to the respective textview widget
            TextView nameprint = findViewById(nameIds[i]);
            TextView inputprint = findViewById(inputIds[i]);
            TextView outputprint = findViewById(outputIds[i]);

            //setting the text for the textview in table layout
            if (nameprint != null) {
                nameprint.setText(name[i]);
            }

            if (inputprint != null) {
                inputprint.setText(input[i]);
            }

            if (outputprint != null) {
                outputprint.setText(String.valueOf(output[i]));
            }
        }


        //when save button click
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);

                mySQLiteAdapter = new SQLiteAdapter(MainActivity2.this);
                mySQLiteAdapter.openToWrite();
                //mySQLiteAdapter.deleteAll();

                //insert the data to db
                for (int i = 0; i < Integer.parseInt(numPeople); i++) {
                    mySQLiteAdapter.insert_2(name[i], input[i], output[i], amount, numPeople);
                }
                mySQLiteAdapter.close();

                //navigate to history page
                startActivity(intent);
            }
        });
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
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.historyPage){
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        }
        return true;
    }

}
