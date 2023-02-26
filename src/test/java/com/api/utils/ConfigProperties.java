package com.api.utils;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads properties file, decrypting secrets
 */
public class ConfigProperties {

    private static final String ENCRYPTION_PASSWORD = "encryption.password";
    private final Properties allProperties = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(ConfigProperties.class);
    //private final StandardPBEStringEncryptor encryptor;

    public ConfigProperties() {
      //  this.encryptor = new StandardPBEStringEncryptor();
        //this.encryptor.setPassword(readEncryptionPassword());
       // Log.info("enter properties file");
        Properties encryptedProps = readProps();
        for (Object key : encryptedProps.keySet()) {

            this.allProperties.put(key, encryptedProps.getProperty(key.toString()));
           // Log.info("enter loop"+allProperties.getProperty(key.toString()));
        }

        String environment = (System.getProperty("environment") != null) ? System.getProperty("environment") : this.allProperties.getProperty("environment");
        encryptedProps = readProps(environment);
        for (Object key : encryptedProps.keySet()) {
            this.allProperties.put(key, encryptedProps.getProperty(key.toString()));
        }
    }

    public Properties getAllProperties() {
        return this.allProperties;
    }

    private Properties readProps(String env) {
        String path;
        if (env == null) {
            path = "src/test/resources/config.properties";
        } else {

            path = "src/test/resources/config-" + env + ".properties";

        }
        try (final FileInputStream fis = new FileInputStream(path)) {
            LOG.debug("Reading properties file from {}", path);
           // final Properties prop = new EncryptableProperties(encryptor);
            final Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (IOException e) {
            LOG.error("IO Exception while Reading properties file {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Properties readProps() {
        return readProps(null);
    }

    /**
     * Read the encryption password from a system environment variable DecryptionPassword
     * or system variable encryptionPassword or encryption.properties
     *
     * @return the password if found, otherwise a default one to continue
     */
    private String readEncryptionPassword() {
        String encryptionPassword = System.getenv("DencryptionPassword");
        if (encryptionPassword != null) {
            return encryptionPassword;
        }
        encryptionPassword = System.getProperty("encryptionPassword");
        if (encryptionPassword != null) {
            return encryptionPassword;
        }
        final Properties props = new Properties();
        try (final FileInputStream fis = new FileInputStream("src/test/resources/encryption.properties")) {
            props.load(fis);
        } catch (FileNotFoundException e) {
            LOG.error("Unable to find encryption password. {}", e.getMessage());
            return ENCRYPTION_PASSWORD;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty(ENCRYPTION_PASSWORD);
    }

}
