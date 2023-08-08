package my.edu.utar.moneysplitapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private SQLiteAdapter mySQLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TableLayout tblayout = findViewById(R.id.tableLayout3);


        //open to read
        mySQLiteAdapter = new SQLiteAdapter(this.getApplicationContext());
        mySQLiteAdapter.openToRead();

        String[] name = mySQLiteAdapter.findName();
        String[] input = mySQLiteAdapter.findinput();
        String[] output = mySQLiteAdapter.findoutput();
        String[] numPeople = mySQLiteAdapter.findNumPeople();

        int people = mySQLiteAdapter.findRows();
        int num = 1;
        int x = 0, y=1;
        String colorCode = new String("");

        //printing data from database
        for (int i = 0; i < people; i++) {
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tableRowParameter = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

            tableRowParameter.setMargins(0, 0, 0, 0);
            tr.setLayoutParams(tableRowParameter);


            // Set the text view
            TextView input_name = new TextView(this);
            input_name.setWidth(500);
            input_name.setTextSize(25f);
            input_name.setText(name[i]);
            tr.addView(input_name);


            TextView input_value = new TextView(this);
            input_value.setWidth(250);
            input_value.setTextSize(25f);
            input_value.setText(input[i]);           //stores the id into the array
            tr.addView(input_value);

            TextView output_value = new TextView(this);
            output_value.setWidth(250);
            output_value.setTextSize(25f);
            output_value.setText(output[i]);           //stores the id into the array
            tr.addView(output_value);

            if(x==0){

                if( i >0 && num ==Integer.parseInt(numPeople[i-1])) {
                    x = 1;
                    y = 0;
                    num=0;
                }
                colorCode = "#40E0D0";
                num++;
            }
            else if(y==0){

                if( num ==Integer.parseInt(numPeople[i-1])) {
                    x = 0;
                    y = 1;
                    num=0;
                }
                colorCode = "#9FE2BF";
                num++;
            }


            tr.setBackgroundColor(Color.parseColor(colorCode));
            output_value.setBackgroundColor(Color.parseColor(colorCode));
            input_value.setBackgroundColor(Color.parseColor(colorCode));
            input_name.setBackgroundColor(Color.parseColor(colorCode));

            tblayout.addView(tr, tableRowParameter);
        }
        mySQLiteAdapter.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.firstPage){
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.historyPage){
            return true;
        }
        return true;
    }
}
