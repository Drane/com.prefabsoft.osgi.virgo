package com.prefabsoft.osgi.virgo.snap.host;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.gemini.web.core.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrefabSoftController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	private Directory directory;

	private static final String SNAP_SERVICE_CLASS = "org.eclipse.virgo.snaps.core.internal.Snap";
	public static final String SNAPS_ATTRIBUTE_NAME = "snaps";
	private volatile String attributeName = SNAPS_ATTRIBUTE_NAME;
	
//	@Autowired
//	LogService log;
	
	/*
	 * 
	 * org.springframework.faces;version="[2.0.7,3.0.0)"
	 */
	
    @RequestMapping("/*")
    public ModelAndView get(HttpServletRequest request) throws IOException {
    	
    	
    	System.out.println("*** in get of PrefabSoftController /*");
    	logger.info("In get with request '{}'", request);
//    	log.log( LogService.LOG_INFO, "[get] part in controller for /*");
    	
    	
		BundleContext bundleContext = (BundleContext) request
				.getServletContext().getAttribute(
						WebContainer.ATTRIBUTE_BUNDLE_CONTEXT);

		long hostId = bundleContext.getBundle().getBundleId();
		List<Snap> snaps = new ArrayList<Snap>();

		try {
			ServiceReference<?>[] serviceReferences = bundleContext
					.getServiceReferences(SNAP_SERVICE_CLASS, "(snap.host.id="
							+ hostId + ")");

			if (serviceReferences != null) {
				Arrays.sort(serviceReferences);
				for (ServiceReference<?> serviceReference : serviceReferences) {
					snaps.add(createSnap(serviceReference));
					System.out.println(serviceReference);
				}
			}
		} catch (InvalidSyntaxException ise) {
			// throw new
			// Exception("Unexpected InvalidSyntaxException when querying service registry for Snaps",
			// ise);\
			ise.printStackTrace();
		}

    	

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
				.addObject("snap", snap_props.getProperty("some.property"))
		    	.addObject("snaps", snaps);

    }
    
	private static Snap createSnap(ServiceReference<?> serviceReference) {
		String[] propertyKeys = serviceReference.getPropertyKeys();
		Map<String, Object> attributes = new HashMap<String, Object>();
		for (String key : propertyKeys) {
			attributes.put(key, serviceReference.getProperty(key));
		}
		return new Snap(attributes);
	}
    
}
