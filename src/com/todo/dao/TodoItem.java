package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
	private Integer id;
    private String title;
    private String category;
	private String desc;
	private Integer how_many;
	private String who;
    private String due_date;
    private String current_date;
    private Integer is_completed;
    
    public TodoItem() {};

    public TodoItem(String title, String category, String desc, int how_many, String who, String due_date, int is_completed){
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.how_many = how_many;
        this.who = who;
        this.due_date = due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
        this.is_completed = is_completed;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getHow_many() {
		return how_many;
	}

	public void setHow_many(int how_many) {
		this.how_many = how_many;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}
    
	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
    
    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
    
    public String toString() {
    	String record = id + ". [" + category + "] " + title;
    	if (is_completed == 1)
    		record += "[V]";
    	record +=	" - " + desc + " -ÀÎ¿ø[" + how_many + "]¸í: " + who +" -" + due_date + " -Time written: " + current_date;
		return record;
    }
    
    public String toSaveString() {
    	return title + "##" + category + "##" + desc + "##" + how_many + "##" + who + "##" 
    			+ due_date + "##" + current_date + "##" + is_completed + "\n";
    }
    
}
