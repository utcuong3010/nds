package com.mbv.bp.common.executor;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;
import com.mbv.bp.common.integration.IPool;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectPool implements IPool<IExecutable>{
	private static Log logger = LogFactory.getLog(ObjectPool.class);
	private int poolSize;
	private IObjectFactory<IExecutable> objectFactory;
    private boolean destroyFlag	= false;
    private List<IExecutable> pool;
    private int maxRetry=3;
    private int waitTime=100;
   
	public ObjectPool(){ 
	}
	@Override
	public synchronized IExecutable getObject() throws Exception {
		for (int i=0;i<maxRetry;i++){
           	if( destroyFlag ) return null;
            if ( pool.size() > 0) {
                IExecutable conn = pool.remove(0);
                if (!conn.isUsable()){
                	conn.reset();
                }
                return conn;
            }
            logger.info("Pool is empty, Waiting for Object");
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                throw new Exception("Thread is interrupted.", e);
            }
		}
		return null;
		
	}

	@Override
	public void initialize() {
		pool = new ArrayList<IExecutable>(poolSize);
        for (int i = 0; i < poolSize; i++) {
        	IExecutable obj = objectFactory.createObject();
            pool.add(obj);
        }
        logger.info("Pool is initialized!| poolSize-"+pool.size());
	}

	@Override
	public synchronized void releaseObject(IExecutable t) {
			if (t!=null) pool.add(t);
	}
	@Override
	public void setObjectFactory(IObjectFactory<IExecutable> objectFactory) {
		this.objectFactory=objectFactory;
		
	}
	@Override
	public void setPoolSize(int poolSize) {
		this.poolSize=poolSize;
		
	}
	
	@Override
	public synchronized void destroy() {
		if( !destroyFlag ) {
			destroyFlag = true;
			if( pool != null ){
				while(pool.size()!=poolSize){}
				while (pool.size()>0){
					IExecutable object=pool.remove(0);
					object.destroy();
				}
				pool.clear();
				pool = null;
			}    			
		}
    }
}
