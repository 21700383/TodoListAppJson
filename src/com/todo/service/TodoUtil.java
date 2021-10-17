package com.todo.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.google.gson.Gson;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, category, desc, who, due_date;
		int how_many, is_completed;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Create Item\n"
				+ "Title: ");
		
		title = sc.nextLine().trim();
		if (l.isDuplicate(title)) {
			System.out.printf("Title can't be duplicate");
			return;
		}
		
		System.out.print("Category: ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("Description: ");
		desc = sc.nextLine().trim();
		
		System.out.print("Number of people involved: ");
		how_many = sc.nextInt();
		sc.nextLine();
		if(how_many == 1) {
			who = "나";
		}
		else {
			System.out.print("People involved(예: 나,철수,영희): ");
			who = sc.nextLine().trim();
		}
		
		System.out.print("Due_date(yyyy/mm/dd): ");
		due_date = sc.next();
		
		is_completed = 0;
		TodoItem t = new TodoItem(title, category, desc, how_many, who, due_date, is_completed);
		if(l.addItem(t) > 0) System.out.println("Item added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Delete Item\n"
				+ "Enter item number to remove: ");
		int index = sc.nextInt();
		if (l.deleteItem(index)>0) 
			System.out.println("Item deleted");
	}
	
	private static void deleteItem(TodoList l, int id) {
		if (l.deleteItem(id)>0) 
			System.out.println("Item deleted");
	}
	
	public static void deleteMany(TodoList l, String del_input) {
		String[] del_str = del_input.split(" "); // if inputs are 5 6 7 split at " "
		int id;
		boolean is_num = true;
		for(int i = 0; i < del_str.length; i++) {
			is_num = del_str[i].matches("-?\\d+(\\.\\d+)?");
			if(is_num == false) { // if the string parsed is not a number
				System.out.println("Please match the input format");
				break;
			}
			id = Integer.valueOf(del_str[i]);
			deleteItem(l, id);
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String new_title, new_category, new_desc, new_who, new_due_date;
		int new_how_many, is_completed;
		System.out.print("Edit Item\n"
				+ "Enter item number to edit: ");
		int index = sc.nextInt();
		sc.nextLine();
		
		System.out.print("New title: ");
		new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.printf("Title can't be duplicate");
			return;
		}
		
		System.out.print("New category: ");
		new_category = sc.next();
		sc.nextLine();
		
		System.out.print("New description: ");
		new_desc = sc.nextLine().trim();
		
		System.out.print("Number of people involved: ");
		new_how_many = sc.nextInt();
		sc.nextLine();
		
		if(new_how_many == 1) {
			new_who = "나";
		}
		else {
			System.out.print("People involved(예: 나,둘리,또치): ");
			new_who = sc.nextLine().trim();
		}
		
		System.out.print("New due date(yyyy/mm/dd): ");
		new_due_date = sc.nextLine().trim();
		is_completed = 0; // assume editing task sets completed status to 0, as it's different now
		
		TodoItem t = new TodoItem(new_title, new_category, new_desc, new_how_many, new_who, new_due_date, is_completed);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("Item updated");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item: l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item: l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("Found total of " + count + " items matching");
	}
	
	public static void listAll(TodoList l) {
		System.out.println("<Total List, total " + l.getCount() + ">");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("<Total List, toal %d>\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)){
			System.out.println(item.toString());
		}
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		
		System.out.println("\nFound total of " + count + " categories");
	}
	
	public static void listCompAll(TodoList l) {
		System.out.println("List of completed Tasks");
		for (TodoItem item : l.getListComp()){
			System.out.println(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, int id) {
		if(l.completeItem(id) > 0) {
			System.out.println("Task finish checked");
		}
		
	}
	
	public static void completeMany(TodoList l, String comp_input) {
		String[] id_str = comp_input.split(" "); // if inputs are 5 6 7 split at " "
		int id;
		boolean is_num = true;
		for(int i = 0; i < id_str.length; i++) {
			is_num = id_str[i].matches("-?\\d+(\\.\\d+)?");
			if(is_num == false) {
				System.out.println("Please match the input format");
				break;
			}
			id = Integer.valueOf(id_str[i]);
			completeItem(l, id);
		}
	}

	
	public static void saveList(TodoList l, String string) {
		Gson gson = new Gson();
		String jsonstr = "";
		List<TodoItem> list = new ArrayList<TodoItem>();
		try {
			Writer w = new FileWriter(string);
			for (TodoItem item : l.getList()) {
				list.add(item);
//				w.write(item.toSaveString()); 
			}
			jsonstr = gson.toJson(list);
			System.out.println(jsonstr);
			w.write(jsonstr);
			w.close();
			
			System.out.println("All data has been saved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
