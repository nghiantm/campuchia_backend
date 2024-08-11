package nghia.campuchia_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nghia.campuchia_backend.utility.SimplifyUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SimplifyUtilTest {
    private SimplifyUtil simplifyUtil;

    @BeforeEach
    void setUp() {
        simplifyUtil = new SimplifyUtil();
    }

    @Test
    void test_basic_2_person_1() {
        HashMap<String, Double> exampleDebts = new HashMap<>();
        exampleDebts.put("Chi", -30.0);
        exampleDebts.put("Dung", 30.0);

        HashMap<String, HashMap<String, Double>> exampleResult = new HashMap<>();
        exampleResult.putIfAbsent("Chi", new HashMap<>());
        exampleResult.get("Chi").put("Dung", 30.0);

        assertEquals(exampleResult, simplifyUtil.simplify(exampleDebts));
    }

    @Test
    void test_basic_4_person_rent_even() {
        HashMap<String, Double> exampleDebts = new HashMap<>();
        exampleDebts.put("Hien", -600.0);
        exampleDebts.put("Duy", -600.0);
        exampleDebts.put("Duong", -600.0);
        exampleDebts.put("Nghia", 1800.0);

        HashMap<String, HashMap<String, Double>> exampleResult = new HashMap<>();
        exampleResult.putIfAbsent("Hien", new HashMap<>());
        exampleResult.get("Hien").put("Nghia", 600.0);
        exampleResult.putIfAbsent("Duong", new HashMap<>());
        exampleResult.get("Duong").put("Nghia", 600.0);
        exampleResult.putIfAbsent("Duy", new HashMap<>());
        exampleResult.get("Duy").put("Nghia", 600.0);

        assertEquals(exampleResult, simplifyUtil.simplify(exampleDebts));
    }
}

