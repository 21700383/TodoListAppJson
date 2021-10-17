module TodoListAppJson {
	opens com.todo.dao;
	requires java.sql;
	requires gson;
}