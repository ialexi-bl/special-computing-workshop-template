package ru.spbu.apcyb.svp.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Тесты для задания 1.
 */
class Task1Test {
    @Test
    void parseInvalidNotes() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Task1.parseNotes("1 b");
        });
    }

    @Test
    void parseValidNotes() {
        Assertions.assertIterableEquals(
                List.of(1L, 2L),
                Task1.parseNotes("1 2")
        );
    }


    @Test
    void emptyNotes() {
        List<Long> emptyList = List.of();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Task1.getCombinations(5, emptyList);
        });
    }

    @Test
    void zeroAmount() {
        List<Long> dummyList = List.of(1L, 100L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Task1.getCombinations(0, dummyList);
        });
    }

    @Test
    void expected1() {
        Assertions.assertIterableEquals(
                Task1.getCombinations(5, List.of(1L)),
                List.of(List.of(1L, 1L, 1L, 1L, 1L))
        );
    }

    @Test
    void expected2() {
        Assertions.assertIterableEquals(
                Task1.getCombinations(5, List.of(1L, 2L)),
                List.of(
                        List.of(2L, 2L, 1L),
                        List.of(2L, 1L, 1L, 1L),
                        List.of(1L, 1L, 1L, 1L, 1L)
                )
        );
    }

    @Test
    void expected3() {
        Assertions.assertIterableEquals(
                Task1.getCombinations(5, List.of(1L, 2L, 3L)),
                List.of(
                        List.of(3L, 2L),
                        List.of(3L, 1L, 1L),
                        List.of(2L, 2L, 1L),
                        List.of(2L, 1L, 1L, 1L),
                        List.of(1L, 1L, 1L, 1L, 1L)
                )
        );
    }
}
