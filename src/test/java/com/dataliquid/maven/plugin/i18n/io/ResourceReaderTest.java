package com.dataliquid.maven.plugin.i18n.io;

/*
 * #%L Maven Plugin i18n %% Copyright (C) 2014 Ben Asmussen %% Licensed under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License. #L%
 */

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dataliquid.maven.plugin.i18n.domain.KeyEntry;
import com.dataliquid.maven.plugin.i18n.domain.ResourceEntry;

/**
 * Resource reader test
 *
 * @author Ben Asmussen
 */
class ResourceReaderTest {

    private ResourceReader resourceReader;

    @BeforeEach
    void setUp() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("test.xls");

        resourceReader = new ResourceReader(is);
    }

    @Test
    void testProcess() throws Exception {
        resourceReader.setKeyCell("B4");
        resourceReader.setLocaleCell("C1");

        resourceReader.process();

        List<ResourceEntry> entries = resourceReader.getEntries();

        assertNotNull(entries);
        assertEquals(2, entries.size());

        // sheet customer
        ResourceEntry resourceCustomer = entries.get(0);
        assertNotNull(resourceCustomer);
        assertEquals("customer", resourceCustomer.getName());

        assertTrue(resourceCustomer.getLocales().contains("DEFAULT"));
        assertTrue(resourceCustomer.getLocales().contains("en"));

        // customer keys
        LinkedList<KeyEntry> keyEntries = resourceCustomer.getEntries();
        assertNotNull(keyEntries);
        assertEquals(4, keyEntries.size());

        assertEquals("CUSTOMER", keyEntries.get(0).getKey());
        assertEquals("FIRSTNAME", keyEntries.get(1).getKey());
        assertEquals("LASTNAME", keyEntries.get(2).getKey());
        assertEquals("DAY_OF_BIRTH", keyEntries.get(3).getKey());

        assertNotNull(keyEntries.get(0).getLocales());
        assertTrue(keyEntries.get(0).getLocales().contains("DEFAULT"));
        assertTrue(keyEntries.get(0).getLocales().contains("en"));

        assertEquals("Kunde", keyEntries.get(0).getLocaleValues().get("DEFAULT"));
        assertEquals("Customer", keyEntries.get(0).getLocaleValues().get("en"));

        // sheet invoice
        ResourceEntry entryInvoice = entries.get(1);
        assertNotNull(entryInvoice);
        assertEquals("invoice", entryInvoice.getName());
    }

    @Test
    void testGetLocaleDefinitions() {
        resourceReader.setLocaleCell("C1");
        Map<String, Integer> resourceDefinitions = resourceReader.getLocaleDefinitions("invoice");
        assertNotNull(resourceDefinitions);
        assertEquals(2, resourceDefinitions.size());
    }

    @Test
    void testGetKeyRow() {
        Integer keyRow = resourceReader.getKeyRow();
        assertNotNull(keyRow);
        assertEquals(Integer.valueOf(1), keyRow);
    }

    @Test
    void testGetKeyCol() {
        Integer keyCol = resourceReader.getKeyCol();
        assertNotNull(keyCol);
        assertEquals(Integer.valueOf(0), keyCol);
    }

    @Test
    void testGetCellValue() {
        String cellValue = resourceReader.getCellValue("customer", 0, 0);
        assertEquals("Section", cellValue);
    }

    @Test
    void testGetSheetByName() {
        Sheet sheet = resourceReader.getSheetByName("customer");
        assertNotNull(sheet);
    }

    @Test
    void testGetSheetByNameNotExists() {
        Sheet sheet = resourceReader.getSheetByName("contract");
        assertNull(sheet);
    }

    @Test
    void testGetSheetNames() {
        List<String> sheetNames = resourceReader.getSheetNames();

        assertNotNull(sheetNames);
        assertEquals(2, sheetNames.size());
    }

}
