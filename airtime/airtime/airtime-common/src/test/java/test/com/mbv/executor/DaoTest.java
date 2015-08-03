package test.com.mbv.executor;

import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.SequencedIdDao;

public class DaoTest {
public static void main(String[] args) throws DaoException {
//	System.out.println((new SequencedIdDao()).selectSequencedIdByType("seq:at-txn-id"));
	int x=4 %3;
	System.out.println(x);
} 
} 
