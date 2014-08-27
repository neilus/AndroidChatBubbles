package com.warting.bubbles;

public class OneComment {
	public boolean left;
	public String comment;

	public OneComment(boolean left, String comment) {
		super();
		this.left = left;
		this.comment = comment;
	}
	public String toString(){
		return ((this.left)?"left":"right") + ": "+ this.comment;
	}
}