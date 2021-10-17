package com.todo.main;

import java.util.Scanner;

import com.todo.menu.Menu;
import com.todo.service.TodoUtil;
import com.todo.dao.TodoList;

public class TodoMain {

	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		if (l.getCount() == 0)
			l.importData("todolist.txt"); // if db already has data, don't load data to db which will just be the same
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;

			case "del":
				TodoUtil.deleteItem(l);
				break;
			case "del_many":
				String del_input = sc.nextLine().trim();
				TodoUtil.deleteMany(l, del_input);
				break;

			case "edit":
				TodoUtil.updateItem(l);
				break;

			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("List ordered by title");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("List in decending order of title");
				TodoUtil.listAll(l, "title", 0);
				break;

			case "ls_date":
				System.out.println("List ordered by date");
				TodoUtil.listAll(l, "due_date", 1);
				break;

			case "ls_date_desc":
				System.out.println("List in decending order of date");
				TodoUtil.listAll(l, "due_date", 0);
				break;

			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "ls_comp":
				TodoUtil.listCompAll(l);
				break;

			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;

			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
			case "comp":
				int id = sc.nextInt();
				TodoUtil.completeItem(l, id);
				break;
				
			
			case "comp_many":
					String comp_input = sc.nextLine().trim();
					TodoUtil.completeMany(l, comp_input);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("please enter one of the above mentioned command");
				break;
			}
		} while (!quit);
		sc.close();
		TodoUtil.saveList(l, "todolist.txt");
	}
}
