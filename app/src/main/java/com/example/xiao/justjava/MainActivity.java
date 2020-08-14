package com.example.xiao.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * This is called when the order button is clicked.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    /**
     * This method is called when the plus button is pressed.
     */
    public void  increment(View view)
    {
        if(quantity == 100)
        {
            //shows error message
            Toast.makeText(this, "You cannot have more than 100 cups of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is pressed.
     */
    public void decrement(View view)
    {
        if(quantity ==1)
        {
            //shows error message
            Toast.makeText(this, "You cannot less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 5;

        if (addWhippedCream)
            basePrice = basePrice + 2;

        if (addChocolate)
            basePrice = basePrice + 4;

        return quantity * basePrice;
    }

    /**
     * This method is called when the order button is pressed.
     */
    public void submitOrder(View view)
    {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_editText);
        String isName = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:tester@hack.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + isName);
        intent.putExtra(Intent.EXTRA_TEXT, displayMessage(price,hasWhippedCream, hasChocolate, isName));
        if(intent.resolveActivity(getPackageManager()) !=null) {
            startActivity(intent);
        }

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Creates the Summary of the Order.
     *
     * @param isName  is the name of the customer.
     * @param price  Total of the order.
     * @param addWhippedCream is weather or not the user wants whipped cream.
     * @param addChocolate is weather or not the user wants Chocolate.
     * @return  priceMessage the text summary.
     */
    private String displayMessage(int price, boolean addWhippedCream, boolean addChocolate, String isName)
    {
        String priceMessage = ("Name:" + isName);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank You";

        return priceMessage;
    }
}