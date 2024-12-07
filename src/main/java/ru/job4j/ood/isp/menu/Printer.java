package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {

    private static final String INDENT = "---";

    private StringBuilder printedMenu = new StringBuilder();

    @Override
    public void print(Menu menu) {
        for (Menu.MenuItemInfo itemInfo : menu) {
            String number = itemInfo.getNumber();
            String name = itemInfo.getName();
            int l = number.length() / 2;
            switch (l) {
                case 1:
                    printedMenu
                            .append(number)
                            .append(" ")
                            .append(name)
                            .append("\n");
                    break;
                default:
                    printedMenu.
                            append(INDENT.repeat(l))
                            .append(number)
                            .append(" ")
                            .append(name)
                            .append("\n");
            }
            System.out.println(printedMenu);
        }
    }

    public String getPrintedMenu() {
        return printedMenu.toString();
    }
}



