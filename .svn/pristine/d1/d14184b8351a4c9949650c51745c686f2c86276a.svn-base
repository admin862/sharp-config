package com.dafy.config.demo;

import org.apache.log4j.Logger;

import java.sql.Connection;

public class DataEngine extends com.cdoframework.cdolib.database.DataEngine
{
	private Logger log = Logger.getLogger(this.getClass());

	public void onSQLStatement(Connection conn,String strSQL)
	{
		if(log.isInfoEnabled())
		{
			log.info("Connection"+conn.hashCode()+" SQL:"+strSQL);
		}
	}

	public void onException(String strText,Exception e)
	{				
		log.error("Database Exception: "+strText+"\r\n"+e,e);
	}

	//构造函数,所有构造函数在此定义------------------------------------------------------------------------------

	public DataEngine() {

	}
}
