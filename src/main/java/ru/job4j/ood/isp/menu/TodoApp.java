package ru.job4j.ood.isp.menu;

public class TodoApp {

    private Printer printer;
    private Menu menu;

    public TodoApp(Printer printer, Menu menu) {
        this.printer = printer;
        this.menu = menu;
    }

    public void addToRoot(String childName, ActionDelegate actionDelegate) {
        menu.add("root", childName, actionDelegate);
    }

    public void addToParent(String parentName, String childName, ActionDelegate actionDelegate) {
        menu.add(parentName, childName, actionDelegate);
    }

    public void getAction(String itemName) {
        menu.select(itemName).ifPresent(Menu.MenuItemInfo::getActionDelegate);
    }

    public void showMenu() {
        printer.print(menu);
    }
}


