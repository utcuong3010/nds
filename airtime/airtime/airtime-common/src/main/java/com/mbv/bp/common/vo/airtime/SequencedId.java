package com.mbv.bp.common.vo.airtime;

public class SequencedId {
	 
	public static final String SELECT_BY_TYPE="SequencedId_SelectByType";
	public static final String INSERT="SequencedId_Insert";
	public static final String UPDATE = "SequencedId_Update";
	private String type;

    private Long counter;

    private Boolean running;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

	@Override
	public String toString() {
		return "SequencedId [counter=" + counter + ", running=" + running
				+ ", type=" + type + "]";
	}

	public long increase() {
		return ++counter;
	}
	
	
}