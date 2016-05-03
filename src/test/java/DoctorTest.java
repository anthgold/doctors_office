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
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deleteSpecialtiesQuery).executeUpdate();
      con.createQuery(deletePatientsQuery).executeUpdate();
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

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Doctor.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Doctor firstDoctor = new Doctor("Dr. Bob Smith", 5);
    Doctor secondDoctor = new Doctor("Dr. Bob Smith", 5);
    assertTrue(firstDoctor.equals(secondDoctor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Doctor myDoctor = new Doctor("Dr. Bob Smith", 3);
    myDoctor.save();
    assertTrue(Doctor.all().get(0).equals(myDoctor));
  }

  @Test
  public void save_assignsIdToObject() {
    Doctor myDoctor = new Doctor("Dr. Bob Smith", 3);
    myDoctor.save();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(myDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void find_findDoctorInDatabase_true() {
    Doctor myDoctor = new Doctor("Dr. Bob Smith", 3);
    myDoctor.save();
    Doctor savedDoctor = Doctor.find(myDoctor.getId());
    assertTrue(myDoctor.equals(savedDoctor));
  }

}
