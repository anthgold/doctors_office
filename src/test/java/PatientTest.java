import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {

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
  public void Patient_instantiatesCorrectly_true() {
    Patient myPatient = new Patient("Bill Murray", "11-12-1969", 2);
    assertEquals(true, myPatient instanceof Patient);
  }

  @Test
  public void getName_patientInstantiatesWithName_String() {
    Patient myPatient = new Patient("Bill Murray", "11-12-1969", 2);
    assertEquals("Bill Murray", myPatient.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Patient firstPatient = new Patient("Bill Murray", "11-12-1969", 2);
    Patient secondPatient = new Patient("Bill Murray", "11-12-1969", 2);
    assertTrue(firstPatient.equals(secondPatient));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Patient myPatient = new Patient("Bill Murray", "11-12-1969", 2);
    myPatient.save();
    assertTrue(Patient.all().get(0).equals(myPatient));
  }

  @Test
  public void save_assignsIdToObject() {
    Patient myPatient = new Patient("Bill Murray", "11-12-1969", 2);
    myPatient.save();
    Patient savedPatient = Patient.all().get(0);
    assertEquals(myPatient.getId(), savedPatient.getId());
  }

  @Test
  public void find_findPatientInDatabase_true() {
    Patient myPatient = new Patient("Bill Murray", "11-12-1969", 2);
    myPatient.save();
    Patient savedPatient = Patient.find(myPatient.getId());
    assertTrue(myPatient.equals(savedPatient));
  }

  @Test
  public void save_savesDoctorIdIntoDB_true() {
    Doctor myDoctor = new Doctor("Dr. Anderson", 3);
    myDoctor.save();
    Patient myPatient = new Patient("Bill Murray", "11-23-1956", myDoctor.getId());
    myPatient.save();
    Patient savedPatient = Patient.find(myPatient.getId());
    assertEquals(savedPatient.getDoctorId(), myDoctor.getId());
  }

}
