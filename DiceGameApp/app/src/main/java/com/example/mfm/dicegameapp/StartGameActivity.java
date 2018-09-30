package com.example.mfm.dicegameapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.view.LayoutInflater;
import android.app.AlertDialog;
import android.util.Log;


public class StartGameActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Button onePlayerButton = (Button) findViewById(R.id.onePlayer_Button);
        onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnePlayerSelect();
            }
        });

        Button twoPlayerButton = (Button) findViewById(R.id.twoPlayer_Button);
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTwoPlayerSelect();
            }
        });

    }

    private void handleOnePlayerSelect()
    {
        Button button = (Button) findViewById(R.id.onePlayer_Button);
        final Intent intent = new Intent (context, PlayGameActivity.class);
        intent.putExtra("ENABLE_COMPUTER", true);

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_input_1player_name, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.one_player1_InputText);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                intent.putExtra("PLAYER_1_NAME", userInput.getText().toString());
                                intent.putExtra("PLAYER_2_NAME", "Computer");
                                Log.d("PLAYER 1 name", userInput.getText().toString());
                                context.startActivity(intent);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void handleTwoPlayerSelect()
    {
        Button button = (Button) findViewById(R.id.twoPlayer_Button);
        final Intent intent = new Intent (context, PlayGameActivity.class);
        intent.putExtra("ENABLE_COMPUTER", false);

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_input_2player_name, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput1 = (EditText) promptsView
                .findViewById(R.id.two_player1_InputText);

        final EditText userInput2 = (EditText) promptsView
                .findViewById(R.id.two_player2_InputText);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                intent.putExtra("PLAYER_1_NAME", userInput1.getText().toString());
                                Log.d("PLAYER 1 name", userInput1.getText().toString());
                                intent.putExtra("PLAYER_2_NAME", userInput2.getText().toString());
                                Log.d("PLAYER 2 name", userInput2.getText().toString());
                                context.startActivity(intent);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
