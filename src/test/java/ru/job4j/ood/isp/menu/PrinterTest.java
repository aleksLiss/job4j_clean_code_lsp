package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenMenuHasOnlyRootElements() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add(Menu.ROOT, "Сходить в кино", STUB_ACTION);
        menu.add(Menu.ROOT, "Купить телефон", STUB_ACTION);
        printer.print(menu);
        String result = printer.getPrintedMenu();
        String expected = "1. Сходить в магазин\n"
                + "2. Покормить собаку\n"
                + "3. Сходить в кино\n"
                + "4. Купить телефон\n";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenMenuHasSomeRootAndChildrenElements() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        printer.print(menu);
        String result = printer.getPrintedMenu();
        String expected = "1. Сходить в магазин\n"
                + "------1.1. Купить продукты\n"
                + "---------1.1.1. Купить хлеб\n"
                + "---------1.1.2. Купить молоко\n"
                + "2. Покормить собаку\n";
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenFirstRootElementHasMoreChildrenElementsThanSecond() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить воду", STUB_ACTION);
        menu.add("Купить воду", "Купить минералку", STUB_ACTION);
        printer.print(menu);
        String expected = "1. Сходить в магазин\n"
                + "------1.1. Купить продукты\n"
                + "---------1.1.1. Купить воду\n"
                + "------------1.1.1.1. Купить минералку\n"
                + "2. Покормить собаку\n";
        String result = printer.getPrintedMenu();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSecondRootElementHasMoreChildrenElementsThanFirst() {
        Menu menu = new SimpleMenu();
        Printer printer = new Printer();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Поехать в банк", STUB_ACTION);
        menu.add("Поехать в банк", "Обратиться к работнику банка", STUB_ACTION);
        menu.add("Обратиться к работнику банка", "Приобрести банковскую карту", STUB_ACTION);
        printer.print(menu);
        String expected = "1. Сходить в магазин\n"
                + "2. Поехать в банк\n"
                + "------2.1. Обратиться к работнику банка\n"
                + "---------2.1.1. Приобрести банковскую карту\n";
        String result = printer.getPrintedMenu();
        assertThat(result).isEqualTo(expected);
    }
}
