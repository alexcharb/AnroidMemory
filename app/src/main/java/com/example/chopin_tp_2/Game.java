package com.example.chopin_tp_2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private int nbPair;
    private String typeGame;
    private Card[] cards;
    private boolean isGameFinished;
    private int nbCardChecked = 0;
	ArrayList<Integer> listIdChecked = new ArrayList<>();
	ArrayList<Integer> listPositionChecked = new ArrayList<>();

    public Game(int nbPair)
    {
        this.nbPair = nbPair;

        InstanciatePairs();
        ShuffleGame();
    }

    public int getNbPair()
    {
        return nbPair;
    }

    public Card[] getCards()
    {
        return cards;
    }

    private void InstanciatePairs()
    {
        cards = new Card[nbPair*2];

        for (int i=0;i<this.nbPair*2;i+=2)
        {
            this.cards[i] = new Card(i);
            this.cards[i+1] = new Card(i);
        }
    }

    private void ShuffleGame()
    {
        Random rnd = new Random();
        for (int i = this.cards.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);

            // Simple swap
            Card c = this.cards[index];
            this.cards[index] = this.cards[i];
            this.cards[i] = c;
        }
    }
    
    public boolean isPairOkOrNot()
    {
    	if (this.nbCardChecked == 2)
    	{
    		if (this.listIdChecked.get(0) == this.listIdChecked.get(1))
    		{
    			// On fait disparaitre les cartes en question
    			this.cards[this.listPositionChecked.get(0)].setVisible(false);
    			this.cards[this.listPositionChecked.get(1)].setVisible(false);
    			
    			// On les remets � l'�tat d'origine
    			this.cards[this.listPositionChecked.get(0)].setChecked(false);
    			this.cards[this.listPositionChecked.get(1)].setChecked(false);

                // Une fois qu'on a retourn� les deux cartes, on r�initialise les param�tres
                this.nbCardChecked = 0;
                this.listIdChecked.clear();
                this.listPositionChecked.clear();

    			return true;
    		}
    		else
    		{
    			this.cards[this.listPositionChecked.get(0)].setChecked(false);
    			this.cards[this.listPositionChecked.get(1)].setChecked(false);
    		}
    		
    		// Une fois qu'on a retourn� les deux cartes, on r�initialise les param�tres
    		this.nbCardChecked = 0;
    		this.listIdChecked.clear();
    		this.listPositionChecked.clear();
    	}
    	
    	return false;
    }
    
    public void OnCardClick(int position)
    {
    	// On change l'état de la carte courante
    	this.cards[position].setChecked(true);
    	
    	// On met � jour le nombre de carte checked
		this.nbCardChecked++;
		this.listIdChecked.add(this.cards[position].getId());
		this.listPositionChecked.add(position);
    }

    public boolean isGameFinished()
    {
        for (Card c : cards)
        {
            // Si au moins une carte est encore visible, on n'a pas gagné
            if (c.isVisible() == true)
                return false;
        }

        // Si aucune carte n'est visible, on a gagné
        return true;
    }

    public void resetAllCards()
    {
        for (Card c : cards)
        {
            c.setChecked(false);
            c.setVisible(true);
        }
    }

}
