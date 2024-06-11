package com.ipower.service.core.dic;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认系统用户
 * 
 */
public enum SystemDefaultUserType {

	SYSTEM( -1L, "系统");


	// 类型
	private Long code;
	// 类型名称
	private String name;

	private static Map<Long, SystemDefaultUserType> codeObjMap = new HashMap<Long, SystemDefaultUserType>();

	static {
		for (SystemDefaultUserType obj :  SystemDefaultUserType.values()) {
			codeObjMap.put(obj.code, obj);
		}
	}

	public static SystemDefaultUserType valueOfCode(Long code) {
		return codeObjMap.get(code);
	}

	SystemDefaultUserType(Long code, String name) {
		this.code = code;
		this.name = name;
	}

	public Long getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
