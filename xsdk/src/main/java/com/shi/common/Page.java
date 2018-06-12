package com.shi.common;
import java.util.ArrayList;  
import java.util.List;  
  
public class Page<T>  
{  
    //当前页码  
    private int cunrrentPage;  
    //全部页码  
    private long totalPage;  
    //全部数据  
    private long totalCount;  
    //每页多少数据  
    private int pageSize;  
    //查询返回结果  
    private List<T> list=new ArrayList<T>();  
  
    public int getCunrrentPage()  
    {  
        return cunrrentPage;  
    }  
  
    public void setCunrrentPage(int cunrrentPage)  
    {  
        if(cunrrentPage<0){  
            cunrrentPage=0;  
        }  
        this.cunrrentPage = cunrrentPage;  
    }  
  
    public long getTotalPage()  
    {  

        return totalPage;  
    }  
  
    public void setTotalPage(long totalPage)  
    {  
    	
        if(totalCount%pageSize==0){  
            totalPage=totalCount/pageSize;  
        }else{  
            totalPage=totalCount/pageSize+1;  
        }  
        if(totalPage<0){  
            totalPage=1;  
        }  
        this.totalPage = totalPage;  
    }  
  
    public long getTotalCount()  
    {  
        return totalCount;  
    }  
  
    public void setTotalCount(long l)  
    {  
        if(l<0){  
            l=1;  
        }  
        this.totalCount = l;  
    }  
  
    public int getPageSize()  
    {  
        return pageSize;  
    }  
  
    public void setPageSize(int pageSize)  
    {  
        if(pageSize<0){  
            pageSize=1;  
        }  
        this.pageSize = pageSize;  
    }  
  
    public List<T> getList()  
    {  
        return list;  
    }  
  
    public void setList(List<T> list)  
    {  
        this.list = list;  
    }  
}  