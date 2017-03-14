/*
 * oxAuth is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.xdi.oxauth.service;

import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.RandomStringUtils;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.ui.ClientUidSelector;

/**
 * Fix cookie value in Seam component
 * 
 * @author Yuriy Movchan Date: 07/10/2013
 */
@Named("org.jboss.seam.ui.clientUidSelector")
@Injectstall(precedence = Install.DEPLOYMENT)
public class FixedClientUidSelector extends ClientUidSelector {

	private static final long serialVersionUID = -7004476980453250712L;

	private String clientUid;

	@Inject(value = "#{facesContext.externalContext}", required = false)
	private ExternalContext externalContext;

	@Create
	public void onCreate() {
		setCookiePath(externalContext.getRequestContextPath());
		setCookieMaxAge(-1);
		setCookieEnabled(true);
		clientUid = getCookieValue();
	}

	public void seed() {
		if (!isSet()) {
			clientUid = RandomStringUtils.random(50, true, true); // Fix
			setCookieValueIfEnabled(clientUid);
		}
	}

	public boolean isSet() {
		return clientUid != null;
	}

	public String getClientUid() {
		return clientUid;
	}

	@Override
	protected String getCookieName() {
		return "javax.faces.ClientToken";
	}

}