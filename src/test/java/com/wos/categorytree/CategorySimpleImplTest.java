package com.wos.categorytree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategorySimpleImplTest {

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

    static List<CategorySimpleImpl> categories;


    @BeforeEach
    void clearCategoryList() {
        categories = new ArrayList<>();
    }

    @Test
    void initTest() {

        categories.addAll(List.of(computers,electronics,clothingShoes,entertainmentArts, videoGamesConsoles, shoes));
        for (CategorySimpleImpl c : categories)
            assertNotNull(c.getName());
    }

    @Test
    void nameCategoryTest() {

        for (String name : categoryElectronics)
            categories.add(new CategorySimpleImpl(name, null));

        for (int i = 0; i < categoryElectronics.length; i++)
            assertEquals(categories.get(i).getName(), categoryElectronics[i]);
    }

    @Test
    void rootCategoryTest() {

        categories.addAll(List.of(computers,electronics,clothingShoes,entertainmentArts));

        for (CategorySimpleImpl c : categories) {
            assertNull(c.getParent());
            assertTrue(c.isRoot());
        }
    }

    @Test
    void rootCategorySetterTest() {

        for (String name : categoryClothingShoes)
            categories.add(new CategorySimpleImpl(name, null));

        CategorySimpleImpl rootCategory = new CategorySimpleImpl(clothingShoes.getName(), null);

        for (CategorySimpleImpl c : categories) {
            c.setParent(rootCategory);
            assertEquals(c.getParent(), rootCategory);
        }
        assertTrue(rootCategory.hasSubCategories());
    }

    @Test
    void subCategoryTest() {

        for (String name : categoryComputers)
            categories.add(new CategorySimpleImpl(name, computers));

        for (CategorySimpleImpl c : categories) {
            assertNotNull(c.getParent());
            assertFalse(c.isRoot());
            assertEquals(c.getParent(), computers);
        }
    }

    @Test
    void subCategoryMethodsTest() {

        CategorySimpleImpl rootCategoryElectronics = new CategorySimpleImpl(electronics.getName(), null);
        CategorySimpleImpl subCategoryElectronics = new CategorySimpleImpl(videoGamesConsoles.getName(), rootCategoryElectronics);

        assertTrue(rootCategoryElectronics.isRoot());
        assertTrue(rootCategoryElectronics.hasSubCategories());

        assertFalse(subCategoryElectronics.isRoot());

        CategorySimpleImpl newCategory;

        for (String name : categoryElectronics) {
            newCategory = new CategorySimpleImpl(name, rootCategoryElectronics);
            rootCategoryElectronics.addSubCategory(newCategory);
            assertTrue(rootCategoryElectronics.getSubCategories().contains(newCategory));
            assertEquals(newCategory.getParent(), rootCategoryElectronics);
        }

        for (String name : categoryVideoGames) {
            newCategory = new CategorySimpleImpl(name, subCategoryElectronics);
            subCategoryElectronics.addSubCategory(newCategory);
            assertTrue(subCategoryElectronics.getSubCategories().contains(newCategory));
            assertEquals(newCategory.getParent(), subCategoryElectronics);
        }

        rootCategoryElectronics.removeSubCategory(subCategoryElectronics);
        assertTrue(subCategoryElectronics.isRoot());
    }
}
