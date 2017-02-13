package com.glorial.connection;

import java.util.HashMap;
import java.util.Map;

public abstract class ConnectionManagerImpl implements IConnectionManager {
	protected Map connections = new HashMap();
}