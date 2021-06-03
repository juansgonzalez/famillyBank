package com.juanseb.bank.views.enums;

public enum FORM_ACTION {
	SAVE("Save"),
	CANCEL("Cancel");

    public final String label;

    private FORM_ACTION(String label) {
        this.label = label;
    }
    
    @Override
	public String toString() {
    	return label;
    }
}
