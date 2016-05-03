import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try (Connection con = DB.sql2o.open()) {
      String deleteSpecialtiesQuery = "DELETE FROM specialties *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deleteSpecialtiesQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    Doctor myDoctor = new Doctor("Dr. Anderson", 3);
    assertEquals(true, myDoctor instanceof Doctor);
  }

  @Test
  public void getName_doctorInstantiatesWithName_String() {
    Doctor myDoctor = new Doctor("Dr.Anderson", 3);
    assertEquals("Dr.Anderson", myDoctor.getName());
  }

}
