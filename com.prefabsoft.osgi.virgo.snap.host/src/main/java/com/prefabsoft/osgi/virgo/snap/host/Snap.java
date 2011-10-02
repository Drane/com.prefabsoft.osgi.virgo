/*******************************************************************************
 * Copyright (c) 2008, 2010 VMware Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   VMware Inc. - initial contribution
 *******************************************************************************/

package com.prefabsoft.osgi.virgo.snap.host;

import java.util.Map;

/**
 * TODO Document Snap
 * <p />
 *
 * <strong>Concurrent Semantics</strong><br />
 *
 * TODO Document concurrent semantics of Snap
 *
 */
public final class Snap {
    
    private final Map<String, Object> properties;
    
    public static final String CONTEXT_PATH_PROPERTY_KEY = "snap.context.path";
    
    public static final String NAME_PROPERTY_KEY = "snap.name";
    
    Snap(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public Map<String, Object> getProperties() {
        return this.properties;
    }
    
    public String getContextPath() {
        return (String)properties.get(CONTEXT_PATH_PROPERTY_KEY);
    }
    
    public String getName() {
        return (String)properties.get(NAME_PROPERTY_KEY);
    }
}
