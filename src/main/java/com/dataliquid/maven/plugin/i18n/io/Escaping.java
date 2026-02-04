package com.dataliquid.maven.plugin.i18n.io;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Represents the escaping method used to encode special (eg. Unicode)
 * characters.
 *
 * @author matemeszaros 2015.10.26
 */
public enum Escaping {
    NONE {
        @Override
        public String apply(String value) {
            return value;
        }
    },
    HTML {
        @Override
        public String apply(String value) {
            return StringEscapeUtils.escapeHtml4(value);
        }
    },
    JAVA {
        @Override
        public String apply(String value) {
            return StringEscapeUtils.escapeJava(value);
        }
    },
    XML {
        @Override
        public String apply(String value) {
            return StringEscapeUtils.escapeXml10(value);
        }
    };

    public abstract String apply(String value);
}
