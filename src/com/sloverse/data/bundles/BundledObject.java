package com.sloverse.data.bundles;

import com.sloverse.data.EnumAuthorityLevel;
import com.sloverse.data.EnumConnectorType;
import com.sloverse.data.EnumPriorityLevel;

public abstract class BundledObject {

	private EnumAuthorityLevel authorityLevel;
	private EnumPriorityLevel priorityLevel;
	private EnumConnectorType connectorType;
	
	public BundledObject(EnumAuthorityLevel authority, EnumPriorityLevel priority, EnumConnectorType connector) {
		authorityLevel = authority;
		priorityLevel = priority;
		connectorType = connector;
	}
	
	public EnumAuthorityLevel getAuthorityLevel() {
		return authorityLevel;
	}
	
	public EnumPriorityLevel getPriorityLevel() {
		return priorityLevel;
	}
	
	public EnumConnectorType getConnectorType() {
		return connectorType;
	}
}
