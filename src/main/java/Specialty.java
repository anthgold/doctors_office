import java.util.List;
import org.sql2o.*;

public class Specialty {
  private int id;
  private String type;

  public Specialty(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public int getId() {
    return id;
  }

  public static List<Specialty> all() {
    String sql = "SELECT id, type FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if (!(otherSpecialty instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpecialty = (Specialty) otherSpecialty;
      return this.getType().equals(newSpecialty.getType());
    }
  }



}
