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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dataliquid.maven.plugin.i18n.domain.KeyEntry;
import com.dataliquid.maven.plugin.i18n.domain.ResourceEntry;

/**
 * Json writer test
 *
 * @author Ben Asmussen
 */
class JsonResourceWriterTest {

    private JsonResourceWriter resourceWriter;

    @TempDir
    Path tempDir;

    private File tempDirectory;

    @BeforeEach
    void setUp() {
        tempDirectory = tempDir.toFile();
        resourceWriter = new JsonResourceWriter();
        resourceWriter.setOutputFolder(tempDirectory);
    }

    @Test
    void testWrite() throws IOException {
        ResourceEntry resourceEntry = new ResourceEntry("customer");
        resourceEntry.getLocales().add("DEFAULT");
        resourceEntry.getLocales().add("de");

        KeyEntry customer = new KeyEntry("CUSTOMER");
        resourceEntry.add(customer);

        customer.addValue("DEFAULT", "Customer");
        customer.addValue("de", "Kunde");

        List<ResourceEntry> entries = new LinkedList<ResourceEntry>();
        entries.add(resourceEntry);

        resourceWriter.setResourceEntries(entries);

        resourceWriter.write();

        // locale default
        File fileDefault = new File(tempDirectory, "customer.json");
        assertTrue(fileDefault.exists());

        // locale de
        File fileDe = new File(tempDirectory, "customer_de.json");
        assertTrue(fileDe.exists());
    }

}
