package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        return ROOT.equals(parentName)
                ? rootElements.add(new SimpleMenuItem(childName, actionDelegate))
                : findItem(parentName).isPresent()
                ? findItem(parentName)
                .get()
                .menuItem
                .getChildren()
                .add(new SimpleMenuItem(childName, actionDelegate))
                : false;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        return findItem(itemName).isPresent()
                ? Optional.of(
                new MenuItemInfo(findItem(itemName).get().menuItem,
                        findItem(itemName).get().number))
                : Optional.empty();
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        List<MenuItemInfo> list = new ArrayList<>();
        DFSIterator iterator = new DFSIterator();
        while (iterator.hasNext()) {
            ItemInfo itemInfo = iterator.next();
            list.add(new MenuItemInfo(
                    itemInfo.menuItem.getName(),
                    itemInfo.menuItem.getChildren()
                            .stream()
                            .map(MenuItem::getName)
                            .toList(),
                    itemInfo.menuItem.getActionDelegate(),
                    itemInfo.number));
        }
        return list.iterator();
    }

    private Optional<ItemInfo> findItem(String name) {
        ItemInfo foundItem = null;
        DFSIterator iterator = new DFSIterator();
        while (iterator.hasNext()) {
            String number = iterator.numbers.getFirst();
            MenuItem itemFromIterator = iterator.next().menuItem;
            if (itemFromIterator.getName().equals(name)) {
                foundItem = new ItemInfo(itemFromIterator, number);
                break;
            }
        }
        return Optional.ofNullable(foundItem);
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        Deque<MenuItem> stack = new LinkedList<>();

        Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious(); ) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        MenuItem menuItem;
        String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }
}

