package com.shi.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;

import com.shi.common.Page;

public interface BaseDao<T, ID extends Serializable> {

	public Serializable save(T o);

	public void saveByCollection(Collection<T> collection);

	public void update(T o);

	public void saveOrUpdate(T o);

	public void updateByColumns(T t, List<String> columnNames,
			List<?> columnValues) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	public void updateByExceptColumns(T t, List<String> exceptColumnNames,
			List<?> columnValues) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	public T get(Serializable id);

	public T get(String hql);

	public T get(String hql, Map<String, Object> params);

	public void delete(T o);

	public void delete(ID id);

	public void deleteByColumns(String tableName, String columnName,
			String columnValues);

	public void deleteByColumns(String tableName, String columnName,
			Collection<?> columnValues);

	public void merge(T model);

	public List<T> findList(String hql);

	public List<T> findList(String hql, Map<String, Object> params);

	public List<T> findTopList(String hql, int topCount);

	public List<T> findAll();
	
	public Page<T> getPage(String hql, Map<String, Object> params, int cunrrentPage,
			int pageSize);

	public List<T> findList(String hql, Map<String, Object> params, int page,
			int rows);

	public List<T> findList(String hql, int page, int rows);

	public Long getCountByHql(String hql);

	public Long getCountByHql(String hql, Map<String, Object> params);

	public Object getCountByHql(String hql, Object... params);

	public String getCountQuery(String hql);

	public int executeHql(String hql);

	public int executeHql(String hql, Map<String, Object> params);

	public int executeHql(String hql, Object... objects);

	public int executeHql(String hql, List<?> objects);

	public void setParameterToQuery(Query q, Map<String, Object> params);

	public void setParameterToQuery(Query q, Object... params);

	public void setParameterToQuery(Query q, List<?> params);

	public T getCountBySql(String sql);

	public T getCountBySql(String sql, Object... params);

	public T getCountBySql(String sql, Map<String, Object> params);

	public List<Map<String, Object>> findListBySql(String sql);

	public List<Map<String, Object>> findListBySql(String sql,
			Map<String, Object> params);

	public List<Map<String, Object>> findListBySql(String sql, Object... params);

	public List<T> execProc(String hql);

	public int executeBySql(String sql) throws Exception;

	public List<T> callProcedure(String procString, List<Object> params)
			throws Exception;

	public List<String> getEntityColumnNameList(Class<?> cls);

	public List<String> getEntityColumnNames(Class<?> cls,
			String... exceptCoulumns);

	public List<String> getEntityColumnNames(Class<?> cls,
			List<String> exceptCoulumns);

	public String getPkName(Class<?> cls);

	public Object getPkValue(Object t) throws NoSuchFieldException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	public String firstLetterToLower(String srcString);
}