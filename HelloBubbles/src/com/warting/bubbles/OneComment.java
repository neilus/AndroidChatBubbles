package com.warting.bubbles;

public class OneComment {
	public boolean left;
	public String uid;
	public static String myuid;
	public String comment;
	/**
	 * Sets the users username, otherwise known as myUID
	 * @param username as String
	 */
	public static void initialize(String username){
		myuid = username;
	}
	/**
	 * Creates one comment, and sets if it is on the left side, or right
	 * @param left as boolean
	 * @param comment as String
	 */
	public OneComment(boolean left, String comment) {
		super();
		this.left = left;
		this.uid = (left)? this.myuid:"";
		this.comment = comment;
	}
	/**
	 * Creates one comment associated with a username, if it is my userid
	 * then it will automatically set place it to the left otherwise to the right
	 * @param uid as String
	 * @param comment as String 
	 */
	public OneComment(String uid,String comment){
		super();
		this.uid = uid;
		this.left = uid.contentEquals(myuid);
		this.comment = comment;
	}
	public String toString(){
		return  this.uid + ": "+ this.comment;
	}
}