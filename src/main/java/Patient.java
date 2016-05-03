import java.util.List;
import org.sql2o.*;

public class Patient {
  private int id;
  private String name;
  private String birthdate;
  private int doctorid;

  public Patient(String name, String birthdate, int specialtyid) {
    this.name = name;
    this.birthdate = birthdate;
    this.doctorid = doctorid;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public int getDoctorId() {
    return doctorid;
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName()) &&
             this.getId() == newPatient.getId();
    }
  }

  public static List<Patient> all() {
    String sql = "SELECT id, name, birthdate, doctorid FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthdate, doctorid) VALUES (:name, :birthdate, :doctorid)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("birthdate", this.birthdate)
        .addParameter("doctorid", this.doctorid)
        .executeUpdate()
        .getKey();
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients WHERE id=:id";
      Patient patient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patient.class);
      return patient;
    }
  }

}
