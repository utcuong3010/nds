package com.mbv.airline.sabre.commands.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FareQuoteResult extends Result{
	private HashMap<String, ArrayList<QuoteInfo>> quotes;
	
	public FareQuoteResult(){
		super();
		quotes = new HashMap<String, ArrayList<QuoteInfo>>();
	}
	
	public void addQuote(QuoteInfo quote){
		ArrayList<QuoteInfo> quoteList = quotes.get(quote.getPaxType());
		if(quoteList == null){
			quoteList = new ArrayList<QuoteInfo>();			
			quotes.put(quote.getPaxType(), quoteList);
		}
		quoteList.add(quote);
	}
	
	public List<QuoteInfo> getQuotesByPaxType(String paxType){
		List<QuoteInfo> retval = quotes.get(paxType);
		if(retval == null) return new ArrayList<QuoteInfo>();
		return retval;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();		
		for(String paxType : quotes.keySet()){
			Iterator<QuoteInfo> iterator =  quotes.get(paxType).iterator();
			while(iterator.hasNext()){
				sb.append(iterator.next().toString() + "\n");
			}
		}
		return sb.toString();
	}
}
