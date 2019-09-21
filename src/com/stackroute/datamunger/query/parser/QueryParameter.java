package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

/*
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {

	String BaseQuery;
	String FileName;
	List<Restriction> Restrictions;

	List<String> LogicalOperators;

	List<String> Fields;

	List<AggregateFunction> AggregateFunctions;

	List<String> OrderByFields;

	List<String> GroupByFields;







	public String getFileName() { return FileName;}

	public String getBaseQuery() { return BaseQuery;}

	public List<Restriction> getRestrictions() {
		return Restrictions;
	}

	public List<String> getLogicalOperators() { return LogicalOperators; }

	public List<String> getFields() { return Fields; }

	public List<AggregateFunction> getAggregateFunctions() {
		return AggregateFunctions;
	}

	public List<String> getOrderByFields() { return OrderByFields; }

	public List<String> getGroupByFields() { return GroupByFields; }




//.........................Setters..................


	public void setFileName(String queryString){
		String fileName;
		String[] splitString = queryString.split(" ");

		int index= queryString.indexOf("from");

		String temp = queryString.substring(index+5);

		index=queryString.indexOf(" ");


		fileName=temp.substring(0,index+1);


		this.FileName=fileName;
	}

	public void setBaseQuery(String queryString){

		int i= queryString.indexOf("where");
		int j= queryString.indexOf("group");
		int k= queryString.indexOf("order");

		int a=10000,b=10000,c=10000;

		if(i!=-1)
			a=i;
		if(j!=-1)
			b=j;
		if(k!=-1)
			c=k;

		int fin=a;
		if(a>b)
			fin=b;
		if(fin>c)
			fin=c;


		String ans="";

		if(i==-1 && j==-1 && k==-1)
			ans= queryString;
		else
			ans= queryString.substring(0,fin-1);

		this.BaseQuery = ans;

	}

	public void setRestriction(String queryString) {



		List<Restriction> ans = new ArrayList<>();

		int start= queryString.indexOf("where ");

		if(start==-1){
			this.Restrictions=null;
		}
		else {

			String subStr = queryString.substring(start + 6);
//

			String[] str = (subStr.trim()).split(" and | or ");


			for (int i = 0; i < str.length; i++) {

				String[] lol = str[i].split(" |'");

				Restriction obj = new Restriction(lol[0], lol[2], lol[1]);
				ans.add(obj);

			}

			this.Restrictions = ans;
		}


	}

	public void setLogicalOperator(String queryString) {

		List<String > ans = new ArrayList<String>();

		int index = queryString.indexOf("where");

		if(index==-1)
			this.LogicalOperators = null;
 else {
			String[] splitStr = queryString.split(" ");
			String str = "";

			for (int i = 0; i < splitStr.length; i++) {
				if ("and".equals(splitStr[i]))
					str = str + "and ";

				if ("or".equals(splitStr[i]))
					str = str + "or ";
			}

			String[] result = (str.trim()).split(" ");

			for (int i = 0; i < result.length; i++)
				ans.add(result[i]);


			this.LogicalOperators = ans;
		}

	}

	public void setFields(String queryString) {

		int index = queryString.indexOf(" ");

		int index2 = queryString.indexOf(" ",index+1);

		String subStr = queryString.substring(index+1,index2);


		String[] result = subStr.split(",");


		List<String> ans = Arrays.asList(result);

		this.Fields = ans;

	}

	public void setAggregateFunctions(String queryString) {


		List<AggregateFunction> ans = new ArrayList<AggregateFunction>();

		if(queryString.indexOf("*")!=-1)
			ans= null;
		else {
			int index1 = queryString.indexOf(" ");
			int endIndex = queryString.indexOf(" from");

			String str = queryString.substring(index1 + 1, endIndex);

			String[] splitStr = str.split(",");

			List<String> strNew = new ArrayList<String>();


			for(int i=0;i<splitStr.length;i++){
				if(splitStr[i].indexOf("(")!=-1){
					strNew.add(splitStr[i]);
				}
			}


			String[] pair = new String[2];



			for(int i=0;i<strNew.size();i++) {
				pair = strNew.get(i).split("\\(|\\)");

				AggregateFunction obj = new AggregateFunction(pair[1],pair[0]);
				ans.add(obj);
			}


			this.AggregateFunctions = ans;



		}





	}

	public void setOrderByFields(String queryString) {

		String str= queryString;

		int i = str.indexOf("order by");
		List<String > ans = new ArrayList<String >();

		String subStr="";
		subStr= str.substring(i+9);

		String[] res = subStr.split(" ");

		for(int j=0;j<res.length;j++){
			ans.add(res[j]);
		}

		this.OrderByFields=ans;


	}

	public void setGroupByField(String queryString) {

		List<String> ans;
		int index = queryString.indexOf("group by ");
		int index2 = queryString.indexOf("order by ");

//		if(index==-1){
//			this.GroupByFields=null;
//		}
//		else {

		String str = queryString.substring(index + 9,index2==-1?queryString.length():index2-1);



		String[] result = str.split(" ");

		ans = Arrays.asList(result);

		this.GroupByFields = ans;
		//	}

	}




}