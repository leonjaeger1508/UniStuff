
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class PersonalParserTest {

    @Test
    public void testParsePersonal() {
        PersonalParser parser = new PersonalParser();
        parser.parsePersonal();
        
        List<Person> personalList = parser.getPersonen();
        
        assertNotNull("Die Personalliste sollte nicht null sein.",personalList);
        assertFalse("Die Personalliste sollte nicht leer sein.",personalList.isEmpty());
        
        assertEquals("Die Personalliste sollte 4 Einträge enthalten.", 4, personalList.size());
        
        Person p1 = personalList.get(0);
        assertEquals( "ID des ersten Eintrags sollte '002' sein.", "002", p1.getId());
        assertEquals("Name des ersten Eintrags sollte 'Bernhard Turban' sein.","Bernhard Turban", p1.getName());
        assertEquals("Email des ersten Eintrags sollte 'bt@hs-rm.de' sein.", "bt@hs-rm.de", p1.getEmail());
        assertEquals("Type des ersten Eintrags sollte 'Professor' sein.", HSPersonellType.Professor, p1.getPersonellType());
        
        
    }
}
