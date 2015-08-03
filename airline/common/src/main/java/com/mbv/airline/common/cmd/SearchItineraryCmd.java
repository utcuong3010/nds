package com.mbv.airline.common.cmd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypeQuantity;

public class SearchItineraryCmd {
	
	
    private List<AirFareInfo> originDestinationInfos;
    private List<AirPassengerTypeQuantity> passengerInfos;

    public List<AirPassengerTypeQuantity> getPassengerInfos() {
        return passengerInfos;
    }

    public void setPassengerInfos(List<AirPassengerTypeQuantity> passengerInfos) {
        this.passengerInfos = passengerInfos;
    }

    public List<AirFareInfo> getOriginDestinationInfos() {
        return originDestinationInfos;
    }

    public void setOriginDestinationInfos(List<AirFareInfo> originDestinationInfos) {
        this.originDestinationInfos = originDestinationInfos;
    }

    public int getPassengerQuantity(AirPassengerType type) {
        for (AirPassengerTypeQuantity item : this.passengerInfos) {
            if (type == item.getPassengerType())
                return item.getQuantity();
        }
        return 0;
    }

    private static DateFormat simpleDateformat = new SimpleDateFormat("ddMM");

    public String toSearchString() {
        // sample BL-RT[{ADT1}{CHD1}][{HANSGN2503}{SGNHAN2703}]
        StringBuilder tmp = new StringBuilder();
        for (AirFareInfo entry : originDestinationInfos) {
            tmp.append(entry.getOriginCode())
                    .append(entry.getDestinationCode())
                    .append(simpleDateformat.format(entry.getDepartureDate()))
                    .append(entry.getVendor())
                    .append(",");
        }

        tmp.append(getPassengerQuantity(AirPassengerType.ADT))
                .append(",")
                .append(getPassengerQuantity(AirPassengerType.CHD))
                .append(",")
                .append(getPassengerQuantity(AirPassengerType.INF));
        return tmp.toString();
    }

    private static HashFunction hashFunc = Hashing.md5();

    public String toHashString() {
        return hashFunc.newHasher().putBytes(this.toSearchString().getBytes()).hash().toString();
        //return hashFunc.newHasher().putString(this.toSearchString()).hash().toString();
    }

    public static SearchItineraryCmd create(AirItinerary itinerary) {
        SearchItineraryCmd result = new SearchItineraryCmd();
        result.setOriginDestinationInfos(itinerary.getFareInfos());

        int numADT = 0, numCHD = 0, numINF = 0;
        for (AirPassengerInfo pass : itinerary.getPassengerInfos()) {
            switch (pass.getPassengerType()) {
                case ADT:
                    numADT++;
                    break;
                case CHD:
                    numCHD++;
                    break;
                case INF:
                    numINF++;
                    break;
            }
        }

        List<AirPassengerTypeQuantity> quantities = new ArrayList<AirPassengerTypeQuantity>();
        quantities.add(new AirPassengerTypeQuantity(AirPassengerType.ADT, numADT));
        if (numCHD > 0)
            quantities.add(new AirPassengerTypeQuantity(AirPassengerType.CHD, numCHD));
        if (numINF > 0)
            quantities.add(new AirPassengerTypeQuantity(AirPassengerType.INF, numINF));

        result.setPassengerInfos(quantities);
        return result;
    }
    
    
    /**
	 * get list passenger
	 * @param listAirPassengerInfo
	 * @return
	 */
	public ArrayList<AirPassengerTypeQuantity> getAirPassengerTypeQuantity(List<AirPassengerInfo> listAirPassengerInfo){
		ArrayList<AirPassengerTypeQuantity> list = new ArrayList<AirPassengerTypeQuantity>();
		int curADT = 0;
		int curCHD = 0;
		int curINF = 0;
		for (AirPassengerInfo paxInfo : listAirPassengerInfo) {
			switch (paxInfo.getPassengerType()) {
			case ADT:
				curADT++;
				break;
			case CHD:
				curCHD++;
				break;
			case INF:
				curINF++;
				break;
			}
		}
		AirPassengerTypeQuantity aptqadt = new AirPassengerTypeQuantity(AirPassengerType.ADT,curADT);
		list.add(aptqadt);
		AirPassengerTypeQuantity aptqchd = new AirPassengerTypeQuantity(AirPassengerType.CHD,curCHD);
		list.add(aptqchd);
		AirPassengerTypeQuantity aptqinf = new AirPassengerTypeQuantity(AirPassengerType.INF,curINF);
		list.add(aptqinf);

		return list;
	}
	
	/**
	 * 
	 * @param itinerary
	 * @return
	 */
	public static SearchItineraryCmd getSearchItinaryCmd(AirItinerary itinerary){
		ArrayList<AirFareInfo> listAirFareInfo = (ArrayList<AirFareInfo>)itinerary.getFareInfos();
		List<AirPassengerInfo> listAirPassengerInfo = (List<AirPassengerInfo>)itinerary.getPassengerInfos();
		SearchItineraryCmd request = new SearchItineraryCmd();
		request.setOriginDestinationInfos(listAirFareInfo);
		request.setPassengerInfos(request.getAirPassengerTypeQuantity(listAirPassengerInfo));
		return request;
	}
	
	
    
    @Override
	public String toString() {
		return "SearchItineraryCommand [originDestinationInfos="
				+ originDestinationInfos + ", passengerInfos=" + passengerInfos
				+ "]";
	}
}
