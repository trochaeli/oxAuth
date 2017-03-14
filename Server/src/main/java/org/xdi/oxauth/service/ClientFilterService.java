/*
 * oxAuth is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.xdi.oxauth.service;

import java.util.Map;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.jboss.seam.annotations.Create;
import org.xdi.oxauth.model.configuration.AppConfiguration;

/**
 * @author Yuriy Zabrovarnyy
 * @author Javier Rojas Blum
 * @version March 4, 2016
 */
@ApplicationScoped
@Named
@Startup
public class ClientFilterService extends BaseAuthFilterService {

    @Inject
    private LdapEntryManager ldapEntryManager;

    @Inject
    private AppConfiguration appConfiguration;

    @Create
    public void init() {
        super.init(appConfiguration.getClientAuthenticationFilters(), Boolean.TRUE.equals(appConfiguration.getClientAuthenticationFiltersEnabled()), false);
    }

    public String processAuthenticationFilter(AuthenticationFilterWithParameters authenticationFilterWithParameters, Map<?, ?> attributeValues) {
        if (attributeValues == null) {
            return null;
        }
        final Map<String, String> normalizedAttributeValues = normalizeAttributeMap(attributeValues);
        final String resultDn = loadEntryDN(ldapEntryManager, authenticationFilterWithParameters, normalizedAttributeValues);
        if (StringUtils.isBlank(resultDn)) {
            return null;
        }

        return resultDn;
    }

    public static ClientFilterService instance() {
        return (ClientFilterService) Component.getInstance(ClientFilterService.class);
    }
}
