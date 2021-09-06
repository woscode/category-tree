package com.wos.categorytree;

import java.util.*;

public class CategorySimpleImpl implements Comparable<CategorySimpleImpl> {

    private String name;
    private CategorySimpleImpl parent = null;
    private Set<CategorySimpleImpl> subCategories = new HashSet<>();

    public CategorySimpleImpl(String name) {
        this.name = name;
    }

    public CategorySimpleImpl(String name, CategorySimpleImpl parent) {
        this(name);
        setParent(parent);
    }

    public boolean isRoot() {
        return Objects.isNull(getParent());
    }

    public boolean hasSubCategories() {
        return !subCategories.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void updateName(String newName) {
        name = newName;
    }

    public void setParent(CategorySimpleImpl parent) {
        this.parent = parent;
        if (!Objects.isNull(this.parent))
            this.parent.getSubCategories().add(this);
    }

    public CategorySimpleImpl getParent() {
        return parent;
    }

    public Set<CategorySimpleImpl> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<CategorySimpleImpl> categories) {
        if (!Objects.isNull(categories)) {
            this.subCategories = categories;
            this.subCategories.forEach(category -> category.setParent(this));
        }
    }

    public void addSubCategory(CategorySimpleImpl subCategory) {
        if (getSubCategories().add(subCategory))
            subCategory.setParent(this);
    }

    public void removeSubCategory(CategorySimpleImpl subCategory) {
        if (getSubCategories().remove(subCategory))
            subCategory.setParent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorySimpleImpl that = (CategorySimpleImpl) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category[" + "name='" + name + ", parent=" + parent + ']';
    }

    /**
     * Compare categories by name
     * @throws NullPointerException – if an argument is null
     */
    @Override
    public int compareTo(CategorySimpleImpl o) {
        return Objects.compare(getName(), o.getName(), String::compareTo);
    }

    public static void main(String[] args) {

        CategorySimpleImpl computers = new CategorySimpleImpl("Computers");
        CategorySimpleImpl electronics = new CategorySimpleImpl("Electronics");
        CategorySimpleImpl clothingShoes = new CategorySimpleImpl("Clothing & Shoes");
        CategorySimpleImpl entertainmentArts = new CategorySimpleImpl("Entertainment & Arts");

        CategorySimpleImpl videoGamesConsoles = new CategorySimpleImpl("Video Games & Consoles", electronics);
        CategorySimpleImpl shoes = new CategorySimpleImpl("Shoes", clothingShoes);

        String[] categoryComputers = { "Laptops","Desktop Computers", "Computer Monitors","Laptop Bags", "Hardware & Peripherals","Computer Networking","Printers & Office Electronics","Tablets"};

        String[] categoryElectronics = { "Cameras & Camcorders","Televisions","MP3 & Media Players","Home Theater","Car Electronics","Electronics Accessories","Gadgets","Cell Phone Accessories","Companies with Cell Phones & Plans","Headphones","Smartwatches"};
        String[] categoryVideoGames = { "Nintendo Switch", "PlayStation", "WiiU", "Xbox One" };

        String[] categoryClothingShoes = { "Jewelry","Watches","Clothing Accessories","Kids' Clothing","Women's Clothing","Men's Clothing"};
        String[] categoryShoes = { "Boot","Boat Shoes","Boots" ,"Dress Shoes" ,"Loafers" ,"Oxfords","Sandals","Slippers","Sneakers"};

        String[] categoryEntertainmentArts = { "Companies with Tickets", "Media", "Collectibles", "Toys & Games", "Crafts & Fabrics", "Musical Instruments"};


        List<CategorySimpleImpl> categories = new ArrayList<>();

        categories.addAll(List.of(computers,electronics,clothingShoes,entertainmentArts, videoGamesConsoles, shoes));

        for (String name : categoryComputers)
            categories.add(new CategorySimpleImpl(name, computers));

        for (String name : categoryElectronics)
            categories.add(new CategorySimpleImpl(name, electronics));

        for (String name : categoryClothingShoes)
            categories.add(new CategorySimpleImpl(name, clothingShoes));

        for (String name : categoryEntertainmentArts)
            categories.add(new CategorySimpleImpl(name, entertainmentArts));

        for (String name : categoryShoes)
            categories.add(new CategorySimpleImpl(name, shoes));

        for (String name : categoryVideoGames)
            categories.add(new CategorySimpleImpl(name, videoGamesConsoles));

        categories.forEach(System.out::println);

        System.out.println("\n::::" + computers.getName() + "::::");
        computers.getSubCategories().forEach(System.out::println);

        System.out.println("::::" + electronics.getName() + "::::");
        electronics.getSubCategories().forEach(System.out::println);

        System.out.println("::::" + clothingShoes.getName() + "::::");
        clothingShoes.getSubCategories().forEach(System.out::println);

        System.out.println("\n:::: Before sort (by name) ::::");
        Collections.shuffle(categories);
        categories.forEach(c -> System.out.println(c.getName()));

        System.out.println(":::: After sort (by name) ::::");
        Collections.sort(categories);
        categories.forEach(c -> System.out.println(c.getName()));

        System.out.println("\n:::: Before sort (by root) ::::");
        Collections.shuffle(categories);
        categories.forEach(c -> System.out.println(c.getName()));

        System.out.println(":::: After sort (by root) ::::");
        categories.sort((c1, c2) ->  c1.isRoot() != c2.isRoot() ?  (c1.isRoot() ? -1 : 1) : c1.compareTo(c2));
        categories.forEach(c -> System.out.println(c.getName() +", (is root: "+ c.isRoot() + ")"));

        /*
         * N-ary Tree sort
         */
    }
}
