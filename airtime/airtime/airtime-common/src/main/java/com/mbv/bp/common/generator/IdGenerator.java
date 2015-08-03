package com.mbv.bp.common.generator;

import com.mbv.bp.common.dao.DaoException;

public interface IdGenerator
{

    public abstract String generateId() throws Exception;
    public void shutdownSeq() throws DaoException;
}
