package org.xdi.oxauth.comp;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.annotations.Test;
import org.xdi.oxauth.BaseComponentTestAdapter;
import org.xdi.oxauth.model.config.ConfigurationFactory;
import org.xdi.oxauth.model.crypto.signature.RSAKeyFactory;
import org.xdi.oxauth.model.crypto.signature.SignatureAlgorithm;
import org.xdi.oxauth.model.exception.InvalidJwtException;
import org.xdi.oxauth.model.jwk.JSONWebKey;
import org.xdi.oxauth.model.jws.RSASigner;
import org.xdi.oxauth.model.jwt.Jwt;
import org.xdi.oxauth.model.jwt.JwtHeader;
import org.xdi.oxauth.model.jwt.JwtHeaderName;
import org.xdi.oxauth.model.jwt.JwtType;
import org.xdi.oxauth.model.jwt.PureJwt;
import org.xdi.oxauth.model.util.JwtUtil;

/**
 * https://localhost:8443/oxauth/seam/resource/restv1/oxauth/jwk
 * http://openid.net/specs/openid-connect-messages-1_0.html#sigs
 *
 * @author Yuriy Zabrovarnyy
 * @version 0.9, 22/11/2012
 */

public class FederationSigningTest extends BaseComponentTestAdapter {

    private static final String TEST_METADATA = "{\"federation_id\"  : \"@!2222!0008!FF8F!7434\",\n" +
            " \"display_name\" : \"Federation example name\",\n" +
            " \"OPs\" : [\n" +
            "          {\n" +
            "            \"display_name\" : \"Example OP\",\n" +
            "            \"op_id\" : \"example.com\",\n" +
            "            \"domain\" : \"example.com\"\n" +
            "          }          \n" +
            "         ],\n" +
            " \"RPs\" : [\n" +
            "          {\n" +
            "            \"display_name\" : \"oxGraph client\",\n" +
            "            \"redirect_uri\" : \"example.com/oxGraph\"\n" +
            "          }\n" +
            "         ]        \n" +
            "}";

    @Test
    public void test() throws InvalidJwtException, JSONException, SignatureException, IOException, IllegalBlockSizeException, NoSuchProviderException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException {
        final String keyId = ConfigurationFactory.getConfiguration().getFederationSigningKid();
        final SignatureAlgorithm algorithm = SignatureAlgorithm.fromName(ConfigurationFactory.getConfiguration().getFederationSigningAlg());
        final String jwkUrl = ConfigurationFactory.getConfiguration().getJwksUri();

        final JSONWebKey JSONWebKey = ConfigurationFactory.getWebKeys().getKey(keyId);
        final RSAKeyFactory factory = RSAKeyFactory.valueOf(JSONWebKey);

        final JSONObject jsonHeader = JwtHeader.instance().
                setType(JwtType.JWS).setAlgorithm(algorithm).setKeyId(keyId).
                toJsonObject();
        final JSONObject jsonPayload = new JSONObject(TEST_METADATA);

        final String signedJwt = JwtUtil.encodeJwt(jsonHeader, jsonPayload, algorithm, factory.getPrivateKey());

        ////////////// VERIFICATION //////////////
        final PureJwt jwt = PureJwt.parse(signedJwt);

        // 1. check signing
        RSASigner rsaSigner = new RSASigner(algorithm, factory.getPublicKey());
        assertTrue(rsaSigner.validateSignature(jwt.getSigningInput(), jwt.getEncodedSignature()));//

        // 2. check keyId and jwtPath
        final JwtHeader header = Jwt.parse(signedJwt).getHeader();
        assertTrue(header.getClaim(JwtHeaderName.KEY_ID).equals(keyId));

    }
}
