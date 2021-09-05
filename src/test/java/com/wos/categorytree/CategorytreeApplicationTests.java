package com.wos.categorytree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategorytreeApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    private static List<Category> rootCategories = new ArrayList<>();
    private static List<Category> childCategories = new ArrayList<>();
    private static List<Category> allCategories = new ArrayList<>();

    @BeforeAll
    static void init() {

        Category cIt = new Category();
        cIt.setName("IT");

        Category cArchitecture = new Category();
        cArchitecture.setName("Architecture");

        Category cSociety = new Category();
        cSociety.setName("Society");

        Category cPhilosophy = new Category();
        cPhilosophy.setName("Philosophy");

        rootCategories.addAll(List.of( cIt ,cArchitecture, cSociety,cPhilosophy));

        allCategories.addAll(rootCategories);
    }

    @BeforeEach
    void clearRepository() {
        categoryRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertNotNull(categoryRepository);
    }

    @Test
    void saveToRepositoryTest() {

        assertEquals(0, categoryRepository.count());

        for (Category category : rootCategories)
            categoryRepository.save(category);
        assertNotEquals(0, categoryRepository.count());
    }

    @Test
    void testIsRootCategory() {

        categoryRepository.deleteAll();

        categoryRepository.saveAll(rootCategories);

        for (Category category : categoryRepository.findAll())
            assertTrue(category.isRoot());
    }


    void testIsChildCategory() {

    }
}
