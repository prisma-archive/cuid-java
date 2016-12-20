import cool.graph.cuid.Cuid;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CuidTest {
    @Test
    public void createCuid() throws Exception {
        // more sane tests?
        assertEquals(Cuid.createCuid().charAt(0), 'c');
    }

    @Test
    public void testForCollisions() {
        assertEquals(hasNoCollisions(), true);
    }

    @Test
    public void shouldValidateACuid() throws Exception {
        final String cuid = Cuid.createCuid();
        assertTrue(Cuid.validate(cuid));
    }

    @Test
    public void shouldNotAcceptNullCuidWhenValidate() throws Exception {
        assertFalse(Cuid.validate(null));
    }

    @Test
    public void shouldNotAcceptEmptyCuidWhenValidate() throws Exception {
        assertFalse(Cuid.validate(""));
    }

    @Test
    public void shouldNotAcceptBlankCuidWhenValidate() throws Exception {
        assertFalse(Cuid.validate(" "));
    }

    @Test
    public void shouldNotAcceptSpecialCharsWhenValidate() throws Exception {
        Arrays.asList(
                "ch72gsb3200~0udocl363eofy", "ch72gsb3200`0udocl363eofy", "ch72gsb3200!0udocl363eofy",
                "ch72gsb3200@0udocl363eofy", "ch72gsb3200#0udocl363eofy", "ch72gsb3200$0udocl363eofy",
                "ch72gsb3200%0udocl363eofy", "ch72gsb3200^0udocl363eofy", "ch72gsb3200&0udocl363eofy",
                "ch72gsb3200*0udocl363eofy", "ch72gsb3200(0udocl363eofy", "ch72gsb3200)0udocl363eofy",
                "ch72gsb3200-0udocl363eofy", "ch72gsb3200_0udocl363eofy", "ch72gsb3200+0udocl363eofy",
                "ch72gsb3200=0udocl363eofy", "ch72gsb3200,0udocl363eofy", "ch72gsb3200.0udocl363eofy",
                "ch72gsb3200[0udocl363eofy", "ch72gsb3200]0udocl363eofy", "ch72gsb3200{0udocl363eofy",
                "ch72gsb3200}0udocl363eofy", "ch72gsb3200|0udocl363eofy", "ch72gsb3200\0udocl363eofy",
                "ch72gsb3200<0udocl363eofy", "ch72gsb3200>0udocl363eofy", "ch72gsb3200?0udocl363eofy",
                "ch72gsb3200:0udocl363eofy", "ch72gsb3200;0udocl363eofy", "ch72gsb3200\"0udocl363eofy",
                "ch72gsb3200\'0udocl363eofy")
                .stream()
                .forEach(cuid -> assertFalse("error on " + cuid, Cuid.validate(cuid)));
    }

    @Test
    public void cuidLengthShouldNotBeLessThanExpectedWhenValidate() throws Exception {
        Arrays.asList(
                "c", "ch", "ch7", "ch72", "ch72g", "ch72gs",
                "ch72gsb", "ch72gsb3", "ch72gsb32", "ch72gsb320", "ch72gsb3200e", "ch72gsb3200e0",
                "ch72gsb3200e0u", "ch72gsb3200e0ud", "ch72gsb3200e0udo", "ch72gsb3200e0udoc",
                "ch72gsb3200e0udocl", "ch72gsb3200e0udocl3", "ch72gsb3200e0udocl36", "ch72gsb3200e0udocl363",
                "ch72gsb3200e0udocl363e", "ch72gsb3200e0udocl363eo", "ch72gsb3200e0udocl363eof")
                .stream()
                .forEach(cuid -> assertFalse("error on " + cuid, Cuid.validate(cuid)));
    }

    @Test
    public void cuidLengthShouldNotBeGreaterThanExpectedWhenValidate() throws Exception {
         assertFalse(Cuid.validate("ch72gsb3200e0udocl363eofxc"));
    }

    private boolean hasNoCollisions() {
        int iterations = 4200000;
        Map<String, String> cuids = new HashMap<>();

        for (int i = 0; i < iterations; ++i) {
            String cuid = Cuid.createCuid();
            if (cuids.containsKey(cuid)) {
                return false;
            } else {
                cuids.put(cuid, cuid);
            }
        }

        return true;
    }
}