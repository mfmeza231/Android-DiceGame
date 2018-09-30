package com.example.mfm.dicegameapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.graphics.drawable.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.content.Context;
import android.app.AlertDialog;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity {


    DiceSides diceSides[] = new DiceSides[6];
    Player players[] = new Player[2];
    boolean player1Turn = true;
    String scoreText = "";
    String playerTurnText = "";
    AnimationDrawable dice1_Animation;
    AnimationDrawable dice2_Animation;
    final Context context = this;
    String winner = "";
    DiceSides dice1;
    DiceSides dice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize properties for dice
        setDiceSideProperties();

        // Initialize properties for players
        setPlayerProperties();

        setScoreText();
        setPlayerTurnText(players[0].getName());

        Button playButton = (Button) findViewById(R.id.roll_Button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playGame();
            }
        });
    }
    private void setDiceSideProperties()
    {
        for (int i = 0; i < 6; i++)
        {
            diceSides[i] = new DiceSides();
        }
        // Set Dice Side 1
        diceSides[0].setSideName(DiceSides.DiceSideName.SIDE_1);
        diceSides[0].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_1));
        diceSides[0].setSideValue(1);

        // Set Dice Side 2
        diceSides[1].setSideName(DiceSides.DiceSideName.SIDE_2);
        diceSides[1].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_2));
        diceSides[1].setSideValue(2);

        // Set Dice Side 3
        diceSides[2].setSideName(DiceSides.DiceSideName.SIDE_3);
        diceSides[2].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_3));
        diceSides[2].setSideValue(3);

        // Set Dice Side 4
        diceSides[3].setSideName(DiceSides.DiceSideName.SIDE_4);
        diceSides[3].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_4));
        diceSides[3].setSideValue(4);

        // Set Dice Side 5
        diceSides[4].setSideName(DiceSides.DiceSideName.SIDE_5);
        diceSides[4].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_5));
        diceSides[4].setSideValue(5);

        // Set Dice Side 6
        diceSides[5].setSideName(DiceSides.DiceSideName.SIDE_6);
        diceSides[5].setSideImage((Drawable) getResources().getDrawable(R.mipmap.ic_dice_side_6));
        diceSides[5].setSideValue(6);
    }

    private void setPlayerProperties()
    {
        for (int i = 0; i < 2; i++)
        {
            players[i] = new Player();
        }

        // Set Player 1 Properties
        players[0].setFinalScore(0);
        players[0].setComputer(false);
        players[0].setName(this.getIntent().getStringExtra("PLAYER_1_NAME"));

        // Set Player 2 Properties
        players[1].setFinalScore(0);
        players[1].setComputer(this.getIntent().getExtras().getBoolean("ENABLE_COMPUTER"));
        players[1].setName(this.getIntent().getStringExtra("PLAYER_2_NAME"));
    }

    private void playGame()
    {
        Button playButton = (Button) findViewById(R.id.roll_Button);
        playButton.setClickable(false);
        if(player1Turn) {
            player1Roll();
        } else {
            player2Roll();
        }
    }

    private void player1Roll()
    {
        simulateRoll();
    }

    private void player2Roll()
    {
        simulateRoll();
    }
    private void simulateRoll()
    {
        ImageView dice1_Image = (ImageView) findViewById(R.id.dice_Image1);
        ImageView dice2_Image = (ImageView) findViewById(R.id.dice_Image2);

        dice1_Image.setImageDrawable(null);
        dice2_Image.setImageDrawable(null);

        dice1_Image.setImageDrawable(getResources().getDrawable(R.drawable.ic_roll_dice_animation));
        dice2_Image.setImageDrawable(getResources().getDrawable(R.drawable.ic_roll_dice_animation));

        dice1_Animation = (AnimationDrawable) dice1_Image.getDrawable();
        dice2_Animation = (AnimationDrawable) dice2_Image.getDrawable();

        int rand1 = 0;
        int rand2 = 0;

        for (int i = 0; i < 6; i++) {
            rand1 = getRandomNumber() - 1;
            rand2 = getRandomNumber() - 1;

            dice1_Animation.addFrame(diceSides[rand1].sideImage, 400);
            dice2_Animation.addFrame(diceSides[rand2].sideImage, 400);
        }

        dice1 = diceSides[rand1];
        dice2 = diceSides[rand2];

        CustomAnimationDrawableNew customAnimDice1 = new CustomAnimationDrawableNew(dice1_Animation) {
            @Override
            public void onAnimationFinish() {

            }

            @Override
            public void onAnimationStart() {


            }
        };
        CustomAnimationDrawableNew customAnimDice2 = new CustomAnimationDrawableNew(dice2_Animation) {
            @Override
            public void onAnimationFinish() {

                int score = rollDice();
                if(player1Turn)
                    players[0].setFinalScore(score);
                else
                    players[1].setFinalScore(score);
                setScoreText();
                if(player1Turn) {
                    setPlayerTurnText(players[1].getName());
                    player1Turn = false;
                }
                else {
                    setPlayerTurnText(players[0].getName());
                    player1Turn = true;
                }

                if (player1Turn) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    endGame();
                } else if (players[1].isComputer()){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playGame();
                }
                Button playButton = (Button) findViewById(R.id.roll_Button);
                playButton.setClickable(true);
            }

            @Override
            public void onAnimationStart() {

            }
        };

        dice1_Image.setImageDrawable(customAnimDice1);
        dice2_Image.setImageDrawable(customAnimDice2);

        customAnimDice1.setOneShot(true);
        customAnimDice2.setOneShot(true);

        customAnimDice1.start();
        customAnimDice2.start();

        Log.d("Dice1 Value", dice1_Image.getDrawable().getCurrent().toString());
        Log.d("Dice2 Value", dice2_Image.getDrawable().getCurrent().toString());
    }

    private int getDiceImage1FromRollValue()
    {
        int roll = 0;

        for(int i = 0; i < diceSides.length; i++)
        {
            if(diceSides[i].getSideImage().getConstantState().equals(dice1_Animation.getCurrent().getConstantState()))
            {
                roll = diceSides[i].getSideValue();
                Log.d("Dice 1 value", Integer.toString(roll));
            }
        }

        return roll;
    }

    private int getDiceImage2FromRollValue()
    {
        int roll = 0;

        for(int i = 0; i < diceSides.length; i++)
        {
            if(diceSides[i].getSideImage().getCurrent().equals(dice2_Animation.getCurrent().getConstantState()))
            {
                roll = diceSides[i].getSideValue();
                Log.d("Dice 2 value", Integer.toString(roll));
            }
        }

        return roll;
    }

    private int rollDice()
    {
        int firstRoll = dice1.getSideValue(); //getDiceImage1FromRollValue();
        int secondRoll = dice2.getSideValue(); //getDiceImage2FromRollValue();

        int score = computeScore(firstRoll, secondRoll);

        return score;
    }

    private int getRandomNumber()
    {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    private int computeScore(int firstRoll, int secondRoll)
    {
        int score = (firstRoll + secondRoll) % 6;
        return score;
    }

    private void setScoreText()
    {
        scoreText = players[0].getName() + " vs " + players[1].getName() +
                "\n" + players[0].getFinalScore() + "-" + players[1].getFinalScore();

        TextView scoreTextView = (TextView) findViewById(R.id.score_TextView);
        scoreTextView.setText(scoreText);
    }

    private void setPlayerTurnText(String playerName)
    {        playerTurnText = playerName + "'s Turn";

        TextView playerTurnTextView = (TextView) findViewById(R.id.playerTurn_TextView);
        playerTurnTextView.setText(playerTurnText);

    }

    private void endGame()
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.dialog_end_game, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        TextView finalText = (TextView) promptsView.findViewById(R.id.endGame_TextView);
        if (players[0].getFinalScore() > players[1].getFinalScore())
        {
            finalText.setText(players[0].getName() +" Wins! \n" +
                    players[0].getFinalScore() + "-" + players[1].getFinalScore());
        }
        else if (players[1].getFinalScore() > players[0].getFinalScore())
        {
            finalText.setText(players[1].getName() +" Wins! \n" +
                    players[0].getFinalScore() + "-" + players[1].getFinalScore());
        }
        else
        {
            finalText.setText("It's a Tie! \n" +
                    players[0].getFinalScore() + "-" + players[1].getFinalScore());
        }
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Restart",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Intent intent = new Intent (context, StartGameActivity.class);
                                context.startActivity(intent);
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
