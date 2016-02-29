import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private int categoryid;
  private String description;
  private String duedate;

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) && this.getDueDate() == newTask.getDueDate() && this.getId() == newTask.getId();
    }
  }

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return categoryid;
  }

  public Task(String description, String duedate, int categoryid) {
    this.description = description;
    this.categoryid = categoryid;
    this.duedate = duedate;
  }

  public String getDueDate() {
    return duedate;
  }

  public String getDescription() {
    return description;
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM Tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Tasks (description, duedate, categoryid) VALUES (:description, :duedate, :categoryid)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description",  this.description)
        .addParameter("duedate", this.duedate)
        .addParameter("categoryid", this.categoryid)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Tasks where id = (:id)";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
    return task;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = (:description) WHERE id = (:id)";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void updateDueDate(String duedate) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET duedate = (:duedate) WHERE id = (:id)";
      con.createQuery(sql)
        .addParameter("duedate", duedate)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //   String sql = "DELETE FROM tasks WHERE id = (:id)";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }



  public List<Task> sortTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where categoryid = (:id) ORDER BY duedate=(:duedate) [ASC] [NULLS {FIRST}]";
      return con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("duedate", duedate)
        .executeAndFetch(Task.class);
    }
  }
}
