package com.example.chopin_tp_2;

public class Card {

    private boolean isChecked;
    private boolean isVisible;
    private int id;

	public Card(int id)
    {
        this.isChecked = false;
        this.isVisible = true;
        this.id = id;
    }
    
    public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
