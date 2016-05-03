import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class SpecialtyTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:portgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM specialties *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Specialty_instantiatesCorrectly_true() {
    Specialty mySpecialty = new Specialty("General Care");
    assertEquals(true, mySpecialty instanceof Specialty);
  }

  @Test
  public void getType_specialtyInstantiatesWithType_String() {
    Specialty mySpecialty = new Specialty("General Care");
    assertEquals("General Care", mySpecialty.getType());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Specialty.all().size(), 0);
  }

}
