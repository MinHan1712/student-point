package student.point.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityJwtConfigurationWSO2 {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityJwtConfigurationWSO2.class);

    @Value("${jhipster.security.authentication.jwt.rsa-public-key:classpath:jwt_public_key.crt}")
    private String rsaPublicKeyCertificateFilePath;

    public static InputStream getFileInputStream(String filePath) throws IOException {
        // Try to load from classpath first
        if (filePath.startsWith("classpath:")) {
            Resource resource = new DefaultResourceLoader().getResource(filePath);
            if (resource.exists()) {
                return resource.getInputStream();
            }
        }

        // If not found in classpath, try to open as a regular file
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File not found: " + filePath);
        }
        return new FileInputStream(file);
    }

    @Bean
    public RSAPublicKey rsaPublicKey() throws IOException, CertificateException {
        // Read the CRT file
        try (InputStream fis = getFileInputStream(rsaPublicKeyCertificateFilePath)) {
            // Parse the certificate
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(fis);

            // Extract the public key
            return (RSAPublicKey) certificate.getPublicKey();
        }
    }

}
