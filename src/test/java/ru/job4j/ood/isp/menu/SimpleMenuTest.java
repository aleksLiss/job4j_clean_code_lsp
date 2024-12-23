package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
    }

    @Test
    public void whenSelectRootElementThenReturnMenuItemInfo() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        Menu.MenuItemInfo result = menu.select("Сходить в магазин").get();
        Menu.MenuItemInfo expected = new Menu.MenuItemInfo("Сходить в магазин",
                List.of(), STUB_ACTION, "1.");
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSelectFirstChildFirstRootElementThenReturnMenuItemInfo() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        Menu.MenuItemInfo result = menu.select("Купить продукты").get();
        Menu.MenuItemInfo expected = new Menu.MenuItemInfo("Купить продукты",
                List.of(), STUB_ACTION, "1.1.");
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSelectLastElementThenReturnMenuItemInfo() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить воду", STUB_ACTION);
        menu.add("Купить воду", "Купить газированную воду", STUB_ACTION);
        Menu.MenuItemInfo result = menu.select("Купить газированную воду").get();
        Menu.MenuItemInfo expected = new Menu.MenuItemInfo("Купить газированную воду",
                List.of(), STUB_ACTION, "1.1.1.1.");
        assertThat(result).isEqualTo(expected);
    }
}