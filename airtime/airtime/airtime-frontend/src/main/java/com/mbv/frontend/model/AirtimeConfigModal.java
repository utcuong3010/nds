package com.mbv.frontend.model;

import java.util.Date;

import com.mbv.bp.common.settings.AlertSettings;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;

public class AirtimeConfigModal extends AirtimeConfig{

	private String currentValue;

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	
	
    
}
