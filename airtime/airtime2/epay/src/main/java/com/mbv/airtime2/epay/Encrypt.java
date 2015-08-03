package com.mbv.airtime2.epay;

import java.io.FileReader;
import java.security.KeyPair;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.RSADigestSigner;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.encoders.Base64;

public class Encrypt {

	private RSAKeyParameters keyParams;
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public Encrypt(String privateKeyPath) throws Exception {
		FileReader fileReader = new FileReader(privateKeyPath);
		PEMReader pemReader = new PEMReader(fileReader);
		KeyPair keyPair = (KeyPair) pemReader.readObject();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		keyParams = new RSAKeyParameters(true, privateKey.getModulus(),
				privateKey.getPrivateExponent());
		pemReader.close();
		fileReader.close();
	}

	public String sign(String data) {
		try {
			RSADigestSigner signer = new RSADigestSigner(new SHA1Digest());
			signer.init(true, keyParams);
			byte[] bytes = data.getBytes();
			signer.update(bytes, 0, bytes.length);
			byte[] signature = signer.generateSignature();
			return new String(Base64.encode(signature));
		} catch (Exception e) {
			return "";
		}
	}
}
