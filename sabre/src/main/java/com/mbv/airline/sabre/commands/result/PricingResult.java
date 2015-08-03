package com.mbv.airline.sabre.commands.result;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class PricingResult extends Result {
	private Long totalAmount = 0L;

	private DateTime timeLimit;

	private List<PaxPricing> paxPricingList;

	private PNR pnr;

	public PricingResult() {
		this.paxPricingList = new ArrayList<PricingResult.PaxPricing>();
	}

	public PricingResult(Code code) {
		this.setStatus(code);
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public DateTime getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(DateTime timeLimit) {
		this.timeLimit = timeLimit;
	}

	public List<PaxPricing> getPrInfos() {
		return paxPricingList;
	}

	public void setPrInfos(List<PaxPricing> prInfos) {
		this.paxPricingList = prInfos;
	}

	public void setPNR(PNR pnr) {
		this.pnr = pnr;
	}

	public PNR getPNR() {
		return pnr;
	}

	public void addPaxPricing(PaxPricing prInfo) {
		this.paxPricingList.add(prInfo);
	}

	public void addSegPricing(String paxType, SegPricing prCal) {
		for (int i = 0; i < paxPricingList.size(); i++) {
			if (paxPricingList.get(i).getType().equalsIgnoreCase(paxType)) {
				paxPricingList.get(i).addSegPricing(prCal);
				break;
			}
		}
	}

	public PaxPricing getPaxPricing(String paxType) {
		for (PaxPricing info : this.paxPricingList) {
			if (paxType.equals(info.getType())) {
				return info;
			}
		}
		return null;
	}

	public List<SegPricing> getSegPricingList(String paxType) {
		List<SegPricing> result = new ArrayList<PricingResult.SegPricing>();

		for (PaxPricing paxPricing : this.paxPricingList) {
			if (paxType.equals(paxPricing.getType())) {
				result.addAll(paxPricing.getSegPricingList());
			}
		}

		return result;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("totalAmount: " + this.totalAmount + "\t timeLimit: "
				+ this.timeLimit + "\n PaxPricing: ");
		for (PaxPricing prInfo : this.paxPricingList) {
			sb.append(prInfo.toString());
		}
		sb.append("\t pnr: " + this.pnr);
		return sb.toString();
	}

	/*
	 * 
	 */
	public static class PaxPricing {
		private String type;
		private int count;
		List<SegPricing> segPricingList;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<SegPricing> getSegPricingList() {
			return segPricingList;
		}

		public void setSegPricingList(List<SegPricing> prCals) {
			this.segPricingList = prCals;
		}

		public PaxPricing() {
			segPricingList = new ArrayList<PricingResult.SegPricing>();
		}

		public void addSegPricing(SegPricing prCal) {
			this.segPricingList.add(prCal);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.type + "\t" + this.count + "\n");
			for (SegPricing cal : this.segPricingList) {
				sb.append("\t" + cal.toString());
			}

			return sb.toString();
		}
	}

	/*
	 * 
	 */
	public static class SegPricing {
		private String from;
		private String to;
		private String fareBasisCode;
		private long fareAmount;

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getFareBasisCode() {
			return fareBasisCode;
		}

		public void setFareBasisCode(String fareBasisCode) {
			this.fareBasisCode = fareBasisCode;
		}

		public long getFareAmount() {
			return fareAmount;
		}

		public void setFareAmount(long fareAmount) {
			this.fareAmount = fareAmount;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.from + "/" + this.to + "\t" + this.fareBasisCode
					+ "\t" + this.fareAmount + "\n");
			return sb.toString();
		}
	}

}
