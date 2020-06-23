/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.vendingmachine.daos;

import com.sg.vendingmachine.vendingmachine.dtos.Item;
import com.sg.vendingmachine.vendingmachine.services.ItemNotFoundException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samg.zun
 */
public class VendingMachineFileDAOTest {

    public VendingMachineFileDAOTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        Path testPath = Paths.get("testData", "test.txt");
        Path seedPath = Paths.get("testData", "seed.txt");

        File testFile = testPath.toFile();

        testFile.delete();
        Files.copy(seedPath, testPath);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllItems method, of class VendingMachineFileDAO.
     */
    @Test
    public void testGetAllItemsGP() {
        VendingMachineFileDAO toTest = new VendingMachineFileDAO(Paths.get("testData", "test.txt").toString());

        List<Item> res = toTest.getAllItems();

        assertEquals("Lays Original", res.get(0).getName());
        assertEquals(new BigDecimal("1.00"), res.get(0).getCost());
        assertEquals(25, res.get(0).getTotal());
        
        assertEquals("Banan", res.get(2).getName());
        assertEquals(new BigDecimal("0.50"), res.get(2).getCost());
        assertEquals(25, res.get(2).getTotal());
    }

    /**
     * Test of getItemByName method, of class VendingMachineFileDAO.
     */
    @Test
    public void testGetItemByNameGP() throws Exception {
        VendingMachineFileDAO toTest = new VendingMachineFileDAO(Paths.get("testData", "test.txt").toString());

        Item res = toTest.getItemByName("Lays Original");

        assertEquals("Lays Original", res.getName());
        assertEquals(new BigDecimal("1.00"), res.getCost());
        assertEquals(25, res.getTotal());
    }

    @Test
    public void testGetItemByNameNotFound() throws Exception {
        VendingMachineFileDAO toTest = new VendingMachineFileDAO(Paths.get("testData", "test.txt").toString());

        try {
            Item res = toTest.getItemByName(" ");
            fail("should hit ItemNotFoundException exception");
        } catch (ItemNotFoundException ex) {

        }

    }

    /**
     * Test of purchaseItem method, of class VendingMachineFileDAO.
     */
    @Test
    public void testPurchaseItemGP() throws Exception {
        VendingMachineFileDAO toTest = new VendingMachineFileDAO(Paths.get("testData", "test.txt").toString());

        Item toPurchase = toTest.getItemByName("Lays Original");
        toPurchase.setTotal(24);

        toTest.purchaseItem(toPurchase);

        Item res = toTest.getItemByName("Lays Original");

        assertEquals("Lays Original", res.getName());
        assertEquals(new BigDecimal("1.00"), res.getCost());
        assertEquals(24, res.getTotal());
    }

}
