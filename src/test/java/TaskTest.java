import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn", "Feb 3rd", 1);
    Task secondTask = new Task("Mow the lawn", "Feb 3rd", 1);
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Task myTask = new Task("Mow the lawn", "June 9th", 1);
    myTask.save();
    assertEquals(Task.all().get(0).getDescription(),"Mow the lawn");
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", "June 9th", 1);
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", "June 9th", 1);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", "June 9th", myCategory.getId());
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  // @Test
  // public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task firstTask = new Task("Mow the lawn", "June 9th", myCategory.getId());
  //   firstTask.save();
  //   Task secondTask = new Task("Do the dishes", "June 9th", myCategory.getId());
  //   secondTask.save();
  //   Task[] tasks = new Task[] { firstTask, secondTask };
  //   assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
  // }
}
