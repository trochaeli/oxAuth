package org.xdi.oxauth.interop;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;
import org.xdi.oxauth.BaseTest;

/**
 * OC5:FeatureTest-Publish openid-configuration Discovery Information
 *
 * @author Javier Rojas Blum
 * @version 0.9, 06/09/2014
 */
public class PublishOpenIdConfigurationDiscoveryInformation extends BaseTest {

    @Test
    public void publishOpenIdConfigurationDiscoveryInformation() {
        showTitle("OC5:FeatureTest-Publish openid-configuration Discovery Information");

        assertTrue(StringUtils.isNotBlank(authorizationEndpoint));
        assertTrue(StringUtils.isNotBlank(tokenEndpoint));
        assertTrue(StringUtils.isNotBlank(userInfoEndpoint));
        assertTrue(StringUtils.isNotBlank(checkSessionIFrame));
        assertTrue(StringUtils.isNotBlank(endSessionEndpoint));
        assertTrue(StringUtils.isNotBlank(jwksUri));
        assertTrue(StringUtils.isNotBlank(registrationEndpoint));
    }
}