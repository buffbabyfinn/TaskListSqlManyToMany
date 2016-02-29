import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    String layout = "views/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      // List<Category> delete = Category.all();
      // delete.delete();

      model.put("template", "views/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      // Category myCategory = request.session().attribute("category");
      String description = request.queryParams("description");
      String duedate = request.queryParams("dueDate");
      int categoryid = Integer.parseInt(request.queryParams("categoryid"));

      Task myTask = new Task(description, duedate, categoryid);
      myTask.save();

      model.put("categories", Category.all());
      model.put("tasks", Task.all());
      model.put("template", "views/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      String name = request.queryParams("name");
      Category myCategory = new Category(name);
      System.out.println(myCategory.getName());
      // int id = myCategory.getId();
      myCategory.save();
      //request.session().attribute("category", myCategory);

      model.put("categories", Category.all());
      model.put("template", "views/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/home", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();


      model.put("categories", Category.all());
      model.put("template", "views/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/categories/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("category", Category.find(Integer.parseInt(request.params(":id"))));
      model.put("tasks", Task.all());
      model.put("template", "views/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
