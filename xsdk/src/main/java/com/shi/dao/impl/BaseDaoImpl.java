package com.shi.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.shi.common.Page;
import com.shi.dao.BaseDao;



@SuppressWarnings("unchecked")
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;
	
	
	BaseDaoImpl(){	
        // 谁实现该类，这就是谁的类字节码
        Class c = this.getClass();
        // 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
        Type type = c.getGenericSuperclass();
        // 将类型强转为参数化类型
        ParameterizedType pType = (ParameterizedType) type;
        // 获取该类的父类的所有实际类型参数，也就是泛型的实际参数
        // 这里也就是获取BaseDaoImpl的实际类型参数
        Type[] actualTypeArguments = pType.getActualTypeArguments();
        // 将实际类型参数赋值给成员变量
        clazz = (Class<T>) (actualTypeArguments[0]);
	}
	
	public Session getCurrentSession() {

		return this.sessionFactory.getCurrentSession();
	}

    
	/**
	 * 返回存储此对象的主键
	 */
	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	public void saveByCollection(Collection<T> collection) {
		for (T t : collection) {
			this.save(t);
		}
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	/**
	 * 更新一个实体中指定的字段 这里columnNames和columnsValues的名字和值得顺序必须一致;
	 * 
	 * @param t
	 * @param columnNames
	 * @param columnValues
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void updateByColumns(T t, List<String> columnNames,
			List<?> columnValues) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String tableNameString = t.getClass().getSimpleName();
		String hqlString = "update " + tableNameString + " table_ set ";
		for (int i = 0; i < columnNames.size(); i++) {
			hqlString += columnNames.get(i) + "=" + columnValues.get(i);
		}
		hqlString += " where table_." + this.getPkName(t.getClass()) + "="
				+ this.getPkValue(t).toString();
		this.executeHql(hqlString);
	}

	/**
	 * 更新一个实体中除开指定的字段之外的字段 这里columnNames和columnsValues的名字和值得顺序必须一致;
	 * 
	 * @param t
	 * @param columnNames
	 * @param columnValues
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public void updateByExceptColumns(T t, List<String> exceptColumnNames,
			List<?> columnValues) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<String> columnNames = this.getEntityColumnNames(t.getClass(),
				exceptColumnNames);
		String tableNameString = t.getClass().getSimpleName();
		String hqlString = "update " + tableNameString + " table_ set ";
		for (int i = 0; i < columnNames.size(); i++) {
			hqlString += columnNames.get(i) + "=" + columnValues.get(i);
		}
		hqlString += " where table_." + this.getPkName(t.getClass()) + "="
				+ this.getPkValue(t).toString();
		this.executeHql(hqlString);
	}

	public T getById (ID id) {
		return (T) this.getCurrentSession().get(this.clazz, id);
	}

	public T get(String hql) {
		return this.get(hql, null);
	}

	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, params);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	/**
	 * 从数据库中找出此id对象并删除
	 * 
	 * @param entityClass
	 * @param id
	 */
	public void delete(ID id) {
		this.getCurrentSession().delete(getById(id));
	}

	/**
	 * hql语句,"delete from "+tableName+" where "+columnName+" in ("+columnValues+
	 * ")" 用in语句删除指定表中,包含有指定值得指定列的记录;
	 * 
	 * @param tableName
	 * @param columnName
	 * @param columnValues
	 *            如1,3,4这种in语句参数需要的内容
	 * @throws Exception
	 */
	public void deleteByColumns(String tableName, String columnName,
			String columnValues) {
		String hql = "delete from " + tableName + " where " + columnName
				+ " in (" + columnValues + ")";
		this.executeHql(hql);
	}

	/**
	 * hql语句,"delete from "+tableName+" where "+columnName+" in ("+columnValues+
	 * ")" 用in语句删除指定表中,包含有指定值得指定列的记录;
	 * 
	 * @param tableName
	 * @param columnName
	 * @param columnValues
	 *            一个参数值的集合
	 */
	public void deleteByColumns(String tableName, String columnName,
			Collection<?> columnValues) {
		String hql = "delete from " + tableName + " where " + columnName
				+ " in (:columnValues)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("columnValues", columnValues);
		this.executeHql(hql, params);
	}

	/**
	 * 如果有id并存在于数据库中,则更新,否则保存
	 * 
	 * @param model
	 */
	public void merge(T model) {
		getCurrentSession().merge(model);
	}

	public List<T> findList(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	public List<T> findList(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, params);
		return q.list();
	}

	/**
	 * 
	 * @param hql
	 * @param topCount
	 *            返回前topCount条记录
	 * @return
	 */
	public List<T> findTopList(String hql, int topCount) {
		// 获取当前页的结果集
		Query query = this.getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		if (topCount < 0)
			topCount = 0;
		query.setMaxResults(topCount);
		return query.list();
	}

	/**
	 * 用hql语句,得到当前表的所有记录
	 * 
	 * @param tableName
	 * @return
	 */
	@Override
	public List<T> findAll() {
		String hqlString = "from " + this.clazz.getName();
		return this.findList(hqlString);
	}

	/**
	 * 返回分页
	 * hql 查询条件语句
	 * params 查询参数
	 * currentPage 当前页码
	 * pageSize 页面记录条数
	 */
	@Override
	public Page<T> getPage(String hql, Map<String, Object> params, int pageNo,
			int pageSize){
		
		Page<T> page = new Page<T>();
		page.setCunrrentPage(pageNo);
		page.setPageSize(pageSize);
	    // 返回分页查询记录
		Query q1 = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q1, params);
	    List<T> tList =  q1.setFirstResult((page.getCunrrentPage() - 1) * page.getPageSize())
	    		.setMaxResults(page.getPageSize()).list();
	    page.setList(tList);
	    String fromHql = hql.substring(hql.indexOf("from"));
		// 返回查询总数  
	    String countHql="select count(*) "+ fromHql;
	    Query q2 = this.getCurrentSession().createQuery(countHql);
		this.setParameterToQuery(q2, params);
		page.setTotalCount((long) q2.uniqueResult());
	    return page;
		
	}
	
	/**
	 * 返回分页
	 * sql 查询条件语句
	 * params 查询参数
	 * currentPage 当前页码
	 * pageSize 页面记录条数
	 */
	@Override
	public Page<T> getSqlPage(String sql, Map<String, Object> params, int pageNo,
			int pageSize){
		
		Page<T> page = new Page<T>();
		page.setCunrrentPage(pageNo);
		page.setPageSize(pageSize);
	    // 返回分页查询记录
		Query q1 = this.getCurrentSession().createSQLQuery(sql);
		this.setParameterToQuery(q1, params);
	    List<T> tList =  q1.setFirstResult((page.getCunrrentPage() - 1) * page.getPageSize())
	    		.setMaxResults(page.getPageSize()).list();
	    page.setList(tList);
	    String fromSql = sql.substring(sql.indexOf("from"));
		// 返回查询总数  
	    String countHql="select count(*) "+ fromSql;
	    Query q2 = this.getCurrentSession().createSQLQuery(countHql);
		this.setParameterToQuery(q2, params);
		page.setTotalCount(((BigInteger)q2.uniqueResult()).longValue());
	    return page;
		
	}
	
	/**
	 * 
	 * @param hql
	 * @param params
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页显示的记录数量
	 * @return
	 */
	public List<T> findList(String hql, Map<String, Object> params, int page,
			int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, params);
		if (page < 1)
			page = 1;
		if (rows < 0)
			rows = 0;
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> findList(String hql, int page, int rows) {
		return this.findList(hql, null, page, rows);
	}

	public Long getCountByHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	public Long getCountByHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, params);
		return (Long) q.uniqueResult();
	}

	/**
	 * 根据HQL语句返回一个值,如分布获取总页数
	 */
	public Object getCountByHql(String hql, Object... params) {
		Query query = getCurrentSession().createQuery(hql);
		this.setParameterToQuery(query, params);
		return query.uniqueResult();
	}

	/**
	 * 根据HQL语句，获得查找总记录数的HQL语句 如： select ... from Orgnization o where o.parent is
	 * null 经过转换，可以得到： select count(*) from Orgnization o where o.parent is null
	 * 
	 * @param hql
	 * @return
	 */
	public String getCountQuery(String hql) {
		int index = hql.toLowerCase().indexOf("from");
		int last = hql.toLowerCase().indexOf("order by");
		if (index != -1) {
			if (last != -1) {
				return "select count(*) " + hql.substring(index, last);
			}
			return "select count(*) " + hql.substring(index);
		}
		return null;
	}

	public int executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public int executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, params);
		return q.executeUpdate();
	}

	public int executeHql(String hql, Object... objects) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, objects);
		return q.executeUpdate();
	}

	/**
	 * 
	 * @param hql
	 * @param objects
	 *            参数,其顺序应该和?占位符一一对应
	 * @return
	 */
	public int executeHql(String hql, List<?> objects) {
		Query q = this.getCurrentSession().createQuery(hql);
		this.setParameterToQuery(q, objects);
		return q.executeUpdate();
	}

	/**
	 * @param q
	 * @param params
	 *            当前支持普通对象,数组,集合三种类型的参数
	 */
	public void setParameterToQuery(Query q, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				if (params.get(key) instanceof Object[]) {
					Object[] objs = (Object[]) params.get(key);
					q.setParameterList(key, objs);
				} else if (params.get(key) instanceof Collection<?>) {
					Collection<?> collection = (Collection<?>) params.get(key);
					q.setParameterList(key, collection);
				} else {
					q.setParameter(key, params.get(key));
				}
			}
		}
	}

	/**
	 * @param q
	 * @param params
	 *            当前支持普通对象,不支持集合与数组
	 */
	public void setParameterToQuery(Query q, Object... params) {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object object = params[i];
				q.setParameter(i, object);
			}
		}
	}

	/**
	 * @param q
	 * @param params
	 *            当前支持普通对象,不支持集合与数组
	 */
	public void setParameterToQuery(Query q, List<?> params) {
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				Object object = params.get(i);
				q.setParameter(i, object);
			}
		}
	}

	public T getCountBySql(String sql) {
		return this.getCountBySql(sql, new HashMap<String, Object>());
	}

	/**
	 * 根据SQL语句返回一个值,如分布获取总页数
	 */
	public T getCountBySql(String sql, Object... params) {
		Query query = getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		this.setParameterToQuery(query, params);
		return (T) query.uniqueResult();
	}

	/**
	 * 根据SQL语句返回一个值,如分布获取总页数
	 */
	public T getCountBySql(String sql, Map<String, Object> params) {
		Query query = getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		this.setParameterToQuery(query, params);
		return (T) query.uniqueResult();
	}

	public List<Map<String, Object>> findListBySql(String sql) {
		return this.findListBySql(sql, new HashMap<String, Object>());
	}

	public List<Map<String, Object>> findListBySql(String sql,
			Map<String, Object> params) {
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		this.setParameterToQuery(query, params);
		return query.list();
	}

	/**
	 * 根据SQL语句返回一个集合
	 */
	public List<Map<String, Object>> findListBySql(String sql, Object... params) {
		Query query = getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		this.setParameterToQuery(query, params);
		return query.list();
	}

	/**
	 * 调用存储过程
	 */
	public List<T> execProc(String hql) {
		Query q = this.getCurrentSession().getNamedQuery(hql);
		return q.list();
	}

	/**
	 * <b>function:</b> 执行原生态的sql语句，添加、删除、修改语句
	 * 
	 * @createDate 2010-8-2 下午05:33:42
	 * @author hoojo
	 * @param sql
	 *            将要执行的sql语句
	 * @return int
	 * @throws Exception
	 */
	public int executeBySql(String sql) throws Exception {
		try {
			return this.getCurrentSession().createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<T> callProcedure(String procString, List<Object> params)
			throws Exception {

		ResultSet rs = null;
		CallableStatement stmt = null;
		try {
			stmt = (CallableStatement) SessionFactoryUtils
					.getDataSource(this.sessionFactory).getConnection()
					.prepareCall(procString);
			if (params != null) {
				int idx = 1;
				for (Object obj : params) {
					if (obj != null) {
						stmt.setObject(idx, obj);
					} else {
						stmt.setNull(idx, Types.NULL);
					}
					idx++;
				}
			}
			rs = stmt.executeQuery();
			List list = new ArrayList();
			ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数

			Map rowData = new HashMap();
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("调用存储过程的时候发生错误[sql = " + procString + "]", e);
		} finally {
			rs.close();
			stmt.close();
		}
	}

	/**
	 * 返回此类的列的属性名称,不包含静态属性和Transient
	 * 
	 * @param entity
	 * @return
	 */
	public List<String> getEntityColumnNameList(Class<?> cls) {
		List<String> list = new ArrayList<String>();
		Class<?> clazz = cls;
		Field[] fs = clazz.getDeclaredFields();
		String filedName = null;
		for (Field field : fs) {
			boolean isStatic = Modifier.isStatic(field.getModifiers());
			if (isStatic)
				continue;
			field.setAccessible(true);
			filedName = field.getName();
			Annotation[] as = field.getAnnotations();
			boolean isTransaction = false;
			for (int i = 0; i < as.length; i++) {
				Annotation a = as[i];
				if (a instanceof Transient) {
					isTransaction = true;
					break;
				}
			}
			if (!isTransaction) {
				list.add(filedName);
			}
		}
		return list;
	}

	/**
	 * 得到除开指定名称的属性列
	 */
	public List<String> getEntityColumnNames(Class<?> cls,
			String... exceptCoulumns) {
		List<String> nameList = getEntityColumnNameList(cls);
		if (exceptCoulumns != null) {
			for (String s : exceptCoulumns) {
				nameList.remove(s);
			}
		}
		return nameList;
	}

	/**
	 * 得到除开指定名称的属性列
	 */
	public List<String> getEntityColumnNames(Class<?> cls,
			List<String> exceptCoulumns) {
		List<String> nameList = getEntityColumnNameList(cls);
		if (exceptCoulumns != null) {
			for (String s : exceptCoulumns) {
				nameList.remove(s);
			}
		}
		return nameList;
	}

	/**
	 * 获取主键名称
	 * 
	 * @return 没有逐渐则返回null;
	 */
	public String getPkName(Class<?> cls) {
		String pkname = null;
		// 标注在getter方法上
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				if (method.isAnnotationPresent(Id.class)) {
					String temp = method.getName().replaceAll("^get", "");
					// 将第一个字母变成小写
					pkname = this.firstLetterToLower(temp);
					break;
				}
			}
		}
		if (pkname == null) {
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Id.class)) {
					return field.getName();
				}
			}
		}
		return pkname;
	}

	public Object getPkValue(Object t) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Field field = t.getClass().getField(this.getPkName(t.getClass()));
		try {
			Method method = t.getClass().getMethod(
					"get" + this.firstLetterToLower(field.getName()));// 此方法不需要参数，如：getName(),getAge()
			return method.invoke(t);
		} catch (NoSuchMethodException e) {
			return field.get(t);
		}
	}

	public String firstLetterToLower(String srcString) {
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toLowerCase(srcString.charAt(0)));
		sb.append(srcString.substring(1));
		return sb.toString();
	}


}