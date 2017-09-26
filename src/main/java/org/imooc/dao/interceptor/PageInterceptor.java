package org.imooc.dao.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.imooc.bean.BaseBean;
import org.imooc.bean.Page;


/**
 * 分页拦截器
 * @author xzb
 *
 */
//type指向要拦截的接口class，method指向要拦截的方法,args指向要拦截的这个参数的方法（防止重载）
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor{

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		StatementHandler statementHandler=(StatementHandler)arg0.getTarget();
		MetaObject metaObject=MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement mappedStatement =(MappedStatement)metaObject.getValue("delegate.mappedStatement");
		
		//配置文件中SQL语句的ID
		String id=mappedStatement.getId();
		if(id.endsWith("ByPage")){
			BoundSql boundSql =statementHandler.getBoundSql();
			//原始的SQL语句
			String sql = boundSql.getSql();
			
			//查询总条数的SQL语句 别名t
			String countSql = "select count(*) from(" + sql + ")t";
			Connection connection = (Connection)arg0.getArgs()[0];			
			//跟原文有差别，它没有强转(PreparedStatement) connection.prepareStatement(countSql);
			PreparedStatement conntStatement = (PreparedStatement) connection.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(conntStatement);
			ResultSet rs = conntStatement.executeQuery();
			
			BaseBean bean=(BaseBean) boundSql.getParameterObject();
			Page page = bean.getPage();
			
			if(rs.next()){
				page.setTotalNumber(rs.getInt(1));
			}
			
			//改造后带分页查询的SQL语句
			//注意:limit前后要加空格！
			String pageSql = sql + " limit " + (page.getCurrentPage()-1)*page.getPageNumber()+","+page.getPageNumber();
			metaObject.setValue("delegate.boundSql.sql", pageSql);
		}
		
		return arg0.proceed();
	}
	
	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
		
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

}
