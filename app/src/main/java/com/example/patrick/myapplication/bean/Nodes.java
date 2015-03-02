
package com.example.patrick.myapplication.bean;

import java.util.ArrayList;

public class Nodes{
   	private Integer count;
   	private String next;
   	private String previous;
   	private ArrayList<NodeBean> results = new ArrayList<NodeBean>();

    /**
     *
     * @return
     * The count
     */
 	public Integer getCount(){
		return this.count;
	}

    /**
     *
     * @param count
     * The count
     */
	public void setCount(Integer count){
		this.count = count;
	}

    /**
     *
     * @return
     * The next
     */
 	public String getNext(){
		return this.next;
	}
	public void setNext(String next){
		this.next = next;
	}

    /**
     *
     * @return
     * The previous
     */
 	public String getPrevious(){
		return this.previous;
	}
	public void setPrevious(String previous){
		this.previous = previous;
	}

    /**
     *
     * @return
     * The results
     */
 	public ArrayList<NodeBean> getNodeBean(){
		return this.results;
	}
	public void setNodeBean(ArrayList<NodeBean> results){
		this.results = results;
	}
}
