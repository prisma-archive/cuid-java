import cool.graph.cuid.Cuid;
import org.junit.Test;

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

    private boolean hasNoCollisions() {
        int iterations = 4200000;
        Map<String, String> cuids = new HashMap<>();

        for (int i = 0; i < iterations; ++i) {
            String cuid = Cuid.createCuid();
            if (cuids.containsKey(cuid) && Cuid.validate(cuid)) {
                return false;
            } else {
                cuids.put(cuid, cuid);
            }
        }

        return true;
    }
}