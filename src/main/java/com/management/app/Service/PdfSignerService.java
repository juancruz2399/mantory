package com.management.app.Service;

import com.itextpdf.kernel.pdf.PdfReader;

import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.geom.Rectangle;

import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;

@Service
public class PdfSignerService {

    public void signPdf(String src, String dest, String p12Path, String password) throws Exception {
        // Cargar keystore .p12
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(p12Path), password.toCharArray());

        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        // Añadir BouncyCastle como proveedor criptográfico (si no está ya)
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        // Crear firma
        PdfReader reader = new PdfReader(src);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), new StampingProperties());

        // Apariencia de la firma
        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
                .setReason("Documento firmado digitalmente")
                .setLocation("Colombia")
                .setReuseAppearance(false);

        signer.setFieldName("Signature1");

        IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, BouncyCastleProvider.PROVIDER_NAME);
        IExternalDigest digest = new BouncyCastleDigest();

        signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CADES);
    }
    public void signPdfVisible(String src, String dest, String p12Path, String password,
            String reason, String location, int pageNumber, float x, float y) throws Exception {

		// Cargar el keystore .p12
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(new FileInputStream(p12Path), password.toCharArray());
		
		String alias = ks.aliases().nextElement();
		PrivateKey pk = (PrivateKey) ks.getKey(alias, password.toCharArray());
		Certificate[] chain = ks.getCertificateChain(alias);
		
		// Añadir BouncyCastle si aún no está
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		Security.addProvider(new BouncyCastleProvider());
		}
		
		// Abrir documento en modo APPEND para permitir más de una firma
		PdfReader reader = new PdfReader(src);
		PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), new StampingProperties().useAppendMode());
		
		// Apariencia de la firma visible
		Rectangle rect = new Rectangle(x, y, 200, 100); // Posición y tamaño del rectángulo
		PdfSignatureAppearance appearance = signer.getSignatureAppearance()
		.setReason(reason)
		.setLocation(location)
		.setPageNumber(pageNumber)
		.setReuseAppearance(false)
		.setPageRect(rect); // Aparece visualmente en el PDF
		
		signer.setFieldName("sig" + System.currentTimeMillis());
		
		IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, BouncyCastleProvider.PROVIDER_NAME);
		IExternalDigest digest = new BouncyCastleDigest();
		
		signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CADES);
		}
    
    
    public void signTwice(String originalPdfPath, String tempSignedPath1, String finalSignedPath,
            String p12EjecutorPath, String passEjecutor,
            String p12ReceptorPath, String passReceptor) throws Exception {

		// Firma del EJECUTOR DEL SERVICIO (técnico o proveedor)
		signPdfVisible(
		  originalPdfPath,             // PDF original
		  tempSignedPath1,             // PDF firmado por 1ra vez (temporal)
		  p12EjecutorPath,             // Certificado del ejecutor
		  passEjecutor,                // Contraseña
		  "Ejecución del servicio",    // Motivo
		  "Firmado por quien ejecuta el servicio", // Lugar o nota
		  1, 100, 500                  // Página, x, y
		);
		
		// Firma del RECEPTOR DEL SERVICIO (cliente, responsable, supervisor)
		signPdfVisible(
		  tempSignedPath1,             // PDF ya firmado una vez
		  finalSignedPath,             // PDF final con ambas firmas
		  p12ReceptorPath,             // Certificado del receptor
		  passReceptor,
		  "Recepción del servicio",
		  "Firmado por quien recibe el servicio",
		  1, 100, 380
		);
		}


}

