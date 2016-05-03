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

  public int getSpecialtyId() {
    return specialtyid;
  }


  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
             this.getId() == newDoctor.getId() &&
             this.getSpecialtyId() == newDoctor.getSpecialtyId();
    }
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, name, specialtyid FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, specialtyid) VALUES (:name, :specialtyid)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("specialtyid", this.specialtyid)
        .executeUpdate()
        .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors WHERE id=:id";
      Doctor doctor = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where doctorid=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        // .addParameter("name", this.name)
        // .addParameter("birthdate", this.birthdate)
        // .addParameter("doctorid", this.doctorid)
        .executeAndFetch(Patient.class);
    }
  }

}
