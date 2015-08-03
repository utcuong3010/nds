package com.mbv.bp.common.vo.airtime;

public class AirtimeConfig {
	
    public static final String SELECT = "AirtimeConfig_Select";

	public static final String SELECT_BY_MODULE_AND_TYPE = "AirtimeConfig_SelectByModuleAndType";

	public static final String UPDATE = "AirtimeConfig_Update";

	public static final String INSERT = "AirtimeConfig_Insert";
    
    private String module;

    private String paramKey;

    private String type;

    private String paramValue;

    private String valueType;
    
    private String remoteKey;
    
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }
	
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey == null ? null : paramKey.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getRemoteKey() {
		return remoteKey;
	}

	public void setRemoteKey(String remoteKey) {
		this.remoteKey = remoteKey;
	}

	@Override
	public String toString() {
		return "AirtimeConfig [module=" + module + ", paramKey=" + paramKey
				+ ", paramValue=" + paramValue + ", remoteKey=" + remoteKey
				+ ", type=" + type + ", valueType=" + valueType + "]";
	}

	
    
}