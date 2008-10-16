/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Riot.
 *
 * The Initial Developer of the Original Code is
 * Neteye GmbH.
 * Portions created by the Initial Developer are Copyright (C) 2007
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Felix Gnass [fgnass at neteye dot de]
 *
 * ***** END LICENSE BLOCK ***** */
package org.riotfamily.common.log;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;

/**
 * Thin wrapper around Log4J that supports varargs. All framework
 * code should use this class so that we can easily switch to another logging
 * framework in future.
 * 
 * @author Felix Gnass [fgnass at neteye dot de]
 * @since 8.0
 */
public class RiotLog {

	private Logger logger;
	
	private RiotLog(Logger logger) {
		this.logger = logger;
	}
	
	public static RiotLog get(Class<?> clazz) {
		return new RiotLog(Logger.getLogger(clazz));
	}
	
	public static RiotLog get(Object obj) {
		return new RiotLog(Logger.getLogger(obj.getClass()));
	}
	
	public static RiotLog get(String name) {
		return new RiotLog(Logger.getLogger(name));
	}
	
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
	
	public void trace(Object msg) {
		logger.trace(msg);
	}
	
	public void trace(String msg, Object... args) {
		if (logger.isTraceEnabled()) {
			logger.trace(String.format(msg, args));
		}
	}
	
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	
	public void debug(Object msg) {
		logger.debug(msg);
	}
	
	public void debug(String msg, Object... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format(msg, args));
		}
	}
	
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	
	public void info(Object msg) {
		logger.info(msg);
	}
	
	public void info(String msg, Object... args) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format(msg, args));
		}
	}
	
	public void warn(Object msg) {
		logger.warn(msg);
	}
	
	public void warn(Throwable t) {
		logger.warn(t.getMessage(), t);
	}
	
	public void warn(Object msg, Throwable t) {
		logger.warn(msg, t);
	}
	
	public void warn(String msg, Throwable t, Object... args) {
		logger.warn(String.format(msg, args), t);
	}
	
	public void warn(String msg, Object... args) {
		logger.warn(String.format(msg, args));
	}
	
	public void error(Object msg) {
		logger.error(msg);
	}
	
	public void error(Throwable t) {
		logger.error(t.getMessage(), t);
	}
	
	public void error(Object msg, Throwable t) {
		logger.error(msg, t);
	}
	
	public void error(String msg, Throwable t, Object... args) {
		logger.error(String.format(msg, args), t);
	}
	
	public void error(String msg, Object... args) {
		logger.error(String.format(msg, args));
	}
	
	public void fatal(Object msg) {
		logger.fatal(msg);
	}
	
	public void fatal(Throwable t) {
		logger.fatal(t.getMessage(), t);
	}
	
	public void fatal(Object msg, Throwable t) {
		logger.fatal(msg, t);
	}
	
	public void fatal(String msg, Throwable t, Object... args) {
		logger.fatal(String.format(msg, args), t);
	}
	
	public static void put(String key, Object value) {
		MDC.put(key, value);
	}
	
	public static void remove(String key) {
		MDC.remove(key);
	}
	
	public static void push(String msg) {
		NDC.push(msg);
	}
	
	public static String pop() {
		return NDC.pop();
	}
}
