package com.work.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Configuration {

    /**
     * The Constant log.
     */
    private static final Logger LOG = Logger.getLogger("Configuration");

    /**
     * The instance.
     */
    private static Configuration instance;

    /**
     * The Constant FILE_NAME.
     */
    private static final String FILE_NAME = "application.properties";

    /**
     * The properties.
     */
    private final Properties properties;

    /**
     * Instantiates a new configuration.
     */
    private Configuration() {
        properties = new Properties();
        load();
    }

    /**
     * Gets the single instance of Configuration.
     *
     * @return single instance of Configuration
     */
    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                Configuration inst = instance;
                if (inst == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
    }

    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    private String getProperty(final String key) {
        String p = properties.getProperty(key);
        if (p != null) {
            return p.trim();
        }
        return p;
    }

    /**
     * Load.
     */
    public void load() {
        load(FILE_NAME);
    }

    /**
     * Gets the path.
     *
     * @param directory the directory
     * @return the path
     */
    public String getPath(String directory) {
        if (directory != null) {
            if (directory.endsWith(File.separator)) {
                return directory;
            } else {
                return directory + File.separator;
            }
        }
        return directory;
    }

    /**
     * Load.
     *
     * @param fileName the file name
     */
    public void load(String fileName) {
        try {

            URI uri = Configuration.class.getClassLoader().getResource(fileName).toURI();
            properties.load(new FileInputStream(new File(uri)));
        } catch (IOException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, "Load Error: {0}", ex);
        }
    }

    /**
     * Gets the string.
     *
     * @param propiedad the propiedad
     * @return the string
     */
    public String getString(final String propiedad) {
        String p = getProperty(propiedad);
        if (p == null) {
            load();
            p = getProperty(propiedad);
        }
        return p;
    }

    /**
     * Gets the float.
     *
     * @param propiedad the propiedad
     * @return the float
     * @throws NumberFormatException the number format exception
     */
    public float getFloat(final String propiedad) throws NumberFormatException {
        return Float.parseFloat(getString(propiedad));
    }

    /**
     * Gets the float.
     *
     * @param propiedad the propiedad
     * @param defaultValor the default valor
     * @return the float
     */
    public float getFloat(final String propiedad, final float defaultValor) {
        try {
            return getFloat(propiedad);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Float Error: {0}", ex);
            return defaultValor;
        }
    }

    /**
     * Gets the long.
     *
     * @param propiedad the propiedad
     * @return the long
     * @throws NumberFormatException the number format exception
     */
    public long getLong(final String propiedad) throws NumberFormatException {
        return Long.parseLong(getString(propiedad));
    }

    /**
     * Gets the long.
     *
     * @param propiedad the propiedad
     * @param defaultValor the default valor
     * @return the long
     */
    public long getLong(final String propiedad, long defaultValor) {
        try {
            return getLong(propiedad);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Long Error: {0}", ex);
            return defaultValor;
        }
    }

    /**
     * Gets the double.
     *
     * @param propiedad the propiedad
     * @return the double
     * @throws NumberFormatException the number format exception
     */
    public double getDouble(final String propiedad) throws NumberFormatException {
        return Double.parseDouble(getString(propiedad));
    }

    /**
     * Gets the double.
     *
     * @param propiedad the propiedad
     * @param defaultValor the default valor
     * @return the double
     */
    public double getDouble(final String propiedad, final double defaultValor) {
        try {
            return getDouble(propiedad);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Double Error: {0}", ex);
            return defaultValor;
        }
    }

    /**
     * Gets the int.
     *
     * @param propiedad the propiedad
     * @return the int
     * @throws NumberFormatException the number format exception
     */
    public int getInt(final String propiedad) throws NumberFormatException {
        return Integer.parseInt(getString(propiedad));
    }

    /**
     * Gets the int.
     *
     * @param propiedad the propiedad
     * @param defaultValor the default valor
     * @return the int
     */
    public int getInt(final String propiedad, final int defaultValor) {
        try {
            return getInt(propiedad);
        } catch (NumberFormatException ex) {
            LOG.log(Level.SEVERE, "Int Error: {0}", ex);
            return defaultValor;
        }
    }
}
