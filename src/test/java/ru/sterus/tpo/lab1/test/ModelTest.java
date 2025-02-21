package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;
import ru.sterus.tpo.lab1.model.FlyingEntity;
import ru.sterus.tpo.lab1.model.MyColor;
import ru.sterus.tpo.lab1.model.Sky;
import ru.sterus.tpo.lab1.model.Thunder;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.sterus.tpo.lab1.model.Thunder.MIN_VOLUME;

@DisplayName("Domain model tests")
public class ModelTest implements TestLifecycleLogger{
    private static final String MIN_VALUE_ERROR_MESSAGE = "Значение Volume у класса Thunder должно быть >= " + MIN_VOLUME;
    private static final String EMPTY_LIST_ERROR_MESSAGE = "После операции moveAll Sky должен быть пустой";
    private static final String AT_LEAST_ONE_YELLOW_ERROR = "В небе должен быть хотя бы одно нечто с желтым цветом";
    private static final String AIR_DESTRUCTION_TEST_ERROR = "Количество разрывов воздуха должно быть равно количеству Entity";

    private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
    private static FlyingEntity generateOne(){
        return new FlyingEntity(randomEnum(MyColor.class), new Thunder());
    }

    private Sky sky;
    @BeforeEach
    void initNewSky(){
        sky = new Sky(
            Stream.generate(ModelTest::generateOne).limit(new Random().nextInt(10) + 1).collect(Collectors.toList())
        );
    }

    @RepeatedTest(10)
    void minValueTest(){
        for(FlyingEntity f : sky.getFlyingEntities()){
            Assertions.assertTrue(f.getThunder().getVolume() >= MIN_VOLUME, MIN_VALUE_ERROR_MESSAGE);
        }
    }
    @Test
    void moveAllEmptyTest(){
        sky.moveAll();
        Assertions.assertEquals(sky.getFlyingEntities().size(), 0, EMPTY_LIST_ERROR_MESSAGE);
    }

    @Test
    void ifYellowExist(){
        Assertions.assertTrue(sky.getFlyingEntities().stream().anyMatch((f) -> f.getColor() == MyColor.YELLOW), AT_LEAST_ONE_YELLOW_ERROR);
    }

    @Test
    void airDestructionTest(){
        int currentNumberOfDestruction = sky.getFlyingEntities().size();
        sky.moveAll();
        Assertions.assertEquals(sky.getAirDestruction(), currentNumberOfDestruction, AIR_DESTRUCTION_TEST_ERROR);
    }
}
