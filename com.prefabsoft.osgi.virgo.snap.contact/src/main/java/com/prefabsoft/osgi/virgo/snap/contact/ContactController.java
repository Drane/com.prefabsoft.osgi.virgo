/*******************************************************************************
 * Copyright (c) 2010, Pouzin Society, http://www.pouzinsociety.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Patsy Phelan, Pouzin Society - initial contribution
 *******************************************************************************/
package com.prefabsoft.osgi.virgo.snap.contact;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    @RequestMapping("/form")
    public ModelAndView cat(HttpServletRequest request) throws IOException {
    	URL host = request.getServletContext().getResource("host:/WEB-INF/sample.properties");
    	Properties host_props = new Properties();
    	if(host != null){
    		host_props.load(host.openStream());
    	}
    	URL snap = request.getServletContext().getResource("/WEB-INF/sample.properties");
    	Properties snap_props = new Properties();
    	if(snap != null){
    		snap_props.load(snap.openStream());
    	}
    	return new ModelAndView("index").addObject("host", host_props.getProperty("some.property"))
										.addObject("snap", snap_props.getProperty("some.property"));
    }
	
}
