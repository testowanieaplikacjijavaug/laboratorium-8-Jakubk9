import org.easymock.*;
import static org.easymock.EasyMock.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EasyMockExtension.class)
public class VerifyVsAssertTest {

    @Test
    public void withoutVerifyTest() {
        Car car = mock(MockType.NICE, Car.class);

        expect(car.getModel()).andReturn("F40");
        expect(car.getVMax()).andReturn(Double.MAX_VALUE);
        replay(car);

        assertThat(car.getModel()).isEqualTo("F40");
        //brak verify powoduje ze test przechodzi
        //ponieważ assert nie sprawdza czy wszystkie funkcje zostaly wywołane
    }

    @Test
    public void withVerifyTest() {
        Car car = mock(MockType.NICE, Car.class);

        expect(car.getModel()).andReturn("F40");
        expect(car.getVMax()).andReturn(Double.MAX_VALUE);
        replay(car);

        assertThat(car.getModel()).isEqualTo("F40");

//        verify(car);
//        wywolanie verify skutokowałoby wyrzuceniem wyjatku
//        poniewaz nie wywolywalismy wszystkich metod

        assertThatThrownBy(() -> verify(car)).isInstanceOf(AssertionError.class);
    }
}
