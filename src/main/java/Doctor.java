import java.util.List;
import org.sql2o.*;

public class Doctor {
  private int id;
  private String name;
  private int specialtyid;

  public Doctor(String name, int specialtyid) {
    this.name = name;
    this.specialtyid = specialtyid;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }
}
