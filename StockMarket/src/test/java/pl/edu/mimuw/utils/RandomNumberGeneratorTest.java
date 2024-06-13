import org.junit.jupiter.api.Test;
import pl.edu.mimuw.utils.RandomNumberGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class RandomNumberGeneratorTest {

    @Test
    public void testGetRandom() {
        int down = 5;
        int up = 10;
        int random = RandomNumberGenerator.getRandom(down, up);

        assertTrue(random >= down && random <= up);

        assertThrows(IllegalArgumentException.class, () -> RandomNumberGenerator.getRandom(up, down));
    }

    @Test
    public void testFlipCoin() {
        boolean firstFlip = RandomNumberGenerator.flipCoin();
        boolean differentResultFound = false;

        for (int i = 0; i < 100; i++) {
            if (RandomNumberGenerator.flipCoin() != firstFlip) {
                differentResultFound = true;
                break;
            }
        }

        assertTrue(differentResultFound, "flipCoin method returned the same result too many times in a row");
    }
}
