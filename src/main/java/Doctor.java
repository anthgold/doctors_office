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

  public static List<Doctor> all() {
    String sql = "SELECT id, name, specialtyid FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
             this.getId() == newDoctor.getId();
    }
  }

}
