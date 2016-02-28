package com.sloverse.data.bundles;

import com.sloverse.data.EnumAuthorityLevel;
import com.sloverse.data.EnumConnectorType;
import com.sloverse.data.EnumPriorityLevel;

public class BundledAction extends BundledObject {
	
	//TODO: Create this class for actions like requesting information (like available game servers) from the hub server.

	public BundledAction(EnumAuthorityLevel authority, EnumPriorityLevel priority, EnumConnectorType connector) {
		super(authority, priority, connector);
	}

}
