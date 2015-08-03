package com.mbv.anypay.queue.utils;

import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.SequencedIdDao;
import com.mbv.bp.common.generator.IdGenerator;
import com.mbv.bp.common.vo.airtime.SequencedId;

public class SimIdGenerator implements IdGenerator {
	protected static final Log log = LogFactory.getLog(SimIdGenerator.class);
	AtomicBoolean shutdownFlag = new AtomicBoolean(false);
	SequencedId sequencedId;
	long initialValue = 0;
	long maxValue = 9999999999L;
	long updateIncrement = 1000;
	long currentCount = 0;
	String type;
	Object lock = new Object();
	
	public SimIdGenerator(long initialValue, long maxValue,
			long updateIncrement, String type) {
		super();
		this.initialValue = initialValue;
		this.maxValue = maxValue;
		this.updateIncrement = updateIncrement;
		this.type = type;
	}

	private void initSequencedId() throws Exception {
		if (sequencedId != null) {
			return;
		}
		SequencedId id = null;
		SequencedIdDao sequencedIdDao=new SequencedIdDao();
		id = sequencedIdDao.selectSequencedIdByType(type);
		
		if (id == null) {
			id = new SequencedId();
			id.setCounter(initialValue);
			id.setRunning(true);
			id.setType(type);
			sequencedIdDao.insert(id);
		} else if (id.getRunning()) {
			id.setCounter(id.getCounter() + updateIncrement);
			log.info("Id of type " + type
					+ " was not shutdown properly, increasing "
					+ updateIncrement + " to " + id.getCounter());
			sequencedIdDao.updateSequencedId(id);
		} else {
			id.setRunning(true);
			sequencedIdDao.updateSequencedId(id);
		}
		sequencedId = id;
		currentCount = 0;

	}

	@Override
	public String generateId() throws Exception {
		if (shutdownFlag.get())
			throw new Exception("Application is shutting down");
		synchronized (lock) {
			if (sequencedId == null)
				initSequencedId();
			long result = sequencedId.increase();
			if (result > maxValue) {
				currentCount = 0;
				sequencedId.setCounter(initialValue);
				result = initialValue;
				SequencedIdDao sequencedIdDao=new SequencedIdDao();
				sequencedIdDao.updateSequencedId(sequencedId);
			}
			currentCount++;
			if (currentCount >= updateIncrement) {
				SequencedIdDao sequencedIdDao=new SequencedIdDao();
				sequencedIdDao.updateSequencedId(sequencedId);
				currentCount = 0;
			}
			String strResult=String.valueOf(result);
			return strResult;
		}
	}
	public void shutdownSeq() throws DaoException{
		if (shutdownFlag.compareAndSet(false, true)){
            if (sequencedId != null)
            {
                sequencedId.setRunning(false);
                SequencedIdDao sequencedIdDao=new SequencedIdDao();
                sequencedIdDao.updateSequencedId(sequencedId);
                shutdownFlag.compareAndSet(true, false);
                log.info("Viettel Sim Seq is shutdown.");
            }
        }
    }
}
