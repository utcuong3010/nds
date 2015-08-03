
package com.mbv.bp.common.executor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.integration.IObjectFactory;
import com.mbv.bp.common.integration.IPool;

public class Executor implements IExecutor<ContextBase>{

	private static final Log log = LogFactory.getLog(Executor.class);

	private int MAX_RETRY				= 3;
	private long RETRY_WAIT_INTERVAL  = 100l;
	private IPool<IExecutable> pool;
	private ExecutorCfg executorCfg;
	public Executor() {
		pool=null;
	}
	
	@Override
	public void init(ExecutorCfg executorCfg, IObjectFactory<IExecutable> objectFactory) throws IntegrationException {
		log.info("init executor-"+executorCfg);
		this.executorCfg=executorCfg;
		this.pool = initPool(objectFactory);
	}
	
	@Override
	public void destroy() {
		pool.destroy();
	}
	
	public ContextBase process(ContextBase context) throws IntegrationException {
		IExecutable objExecutable 		= null;
		ContextBase result 		= null;
		
		if( pool == null ) {
			throw new IntegrationException("Fail to get Object from Pool| Pool is null.");
		}
		boolean isExecuted=false;
		try {
			
			for (int i = 0; i < MAX_RETRY; i++) {
				try {
					objExecutable = pool.getObject();
				} catch (Exception e) {
					log.error("Fail to get Object from pool." ,e);
					objExecutable=null;
				}	
				
				if( objExecutable != null ) {
					long startTime=System.currentTimeMillis();
					result=objExecutable.process(context);
					isExecuted=true;
					log.info("Executor processed.|processed time(ms):"+(System.currentTimeMillis()-startTime)+"|Context:"+context);
				} 
				
				if (isExecuted) break; 
				log.info("**** Waiting for Object...");
				try{
					Thread.sleep(RETRY_WAIT_INTERVAL);
				}catch (InterruptedException e) {
					log.warn("Error on sleep",e);
				}
			}
			if (!isExecuted) throw new IntegrationException("Fail to perform execution. Unable to get Object from pool");
			return result;			
		} catch (Exception e) {
			throw new IntegrationException( e.getMessage(), e );
		} finally {
	            pool.releaseObject(objExecutable);
		}
	}

	protected IPool<IExecutable> initPool(IObjectFactory<IExecutable> objectFactory) {
		IPool<IExecutable> pool=new ObjectPool();
		pool.setObjectFactory(objectFactory);
		pool.setPoolSize(executorCfg.getPoolSize())	;
		pool.initialize();
		return pool;
	}
}
