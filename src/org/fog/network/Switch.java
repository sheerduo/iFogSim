package org.fog.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.entities.Tuple;
import org.fog.utils.FogEvents;
import org.fog.utils.Logger;

public class Switch extends SimEntity {
	private static String LOG_TAG = "SWITCH";
	
	protected List<Integer> neighbourSwitches;
	
	/**
	 * Map from destination entity ID to ID of link to forward to
	 */
	protected Map<Integer, Integer> switchingTable;
	
	/**
	 * List of adjacent entities
	 */
	protected List<Integer> adjacentEntities;
	
	/**
	 * List of adjacent end devices
	 */
	protected List<Integer> adjacentEndDevices;
	
	public Switch(String name) {
		super(name);
		setSwitchingTable(new HashMap<Integer, Integer>());
		setAdjacentEntities(new ArrayList<Integer>());
		setNeighbourSwitches(new ArrayList<Integer>());
		setAdjacentEndDevices(new ArrayList<Integer>());
	}

	public boolean isCoreSwitch() {
		return true;
	}
	
	public boolean isEdgeSwitch() {
		return !isCoreSwitch();
	}
	
	private void processTupleArrival(SimEvent ev) {
		Tuple tuple = (Tuple) ev.getData();
		Logger.debug(LOG_TAG, getName(), "Received tuple with dst = "
		+CloudSim.getEntityName(tuple.getDestinationDeviceId())+" & tupleType = "+tuple.getTupleType());
		
		int destId = tuple.getDestinationDeviceId();
		if (getSwitchingTable().containsKey(destId)) {
			sendNow(getSwitchingTable().get(destId), FogEvents.TUPLE_ARRIVAL, tuple);
		} else {
			Logger.error(LOG_TAG, getName(), "DESTINATION NOT IN SWITCHING TABLE");
		}
	}
	
	@Override
	public void startEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processEvent(SimEvent ev) {
		int tag = ev.getTag();
		switch (tag) {
		case FogEvents.TUPLE_ARRIVAL:
			processTupleArrival(ev);
			break;
		}
	}

	@Override
	public void shutdownEntity() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Integer> getNeighbourSwitches() {
		return neighbourSwitches;
	}

	public void setNeighbourSwitches(List<Integer> neighbourSwitches) {
		this.neighbourSwitches = neighbourSwitches;
	}

	public Map<Integer, Integer> getSwitchingTable() {
		return switchingTable;
	}

	public void setSwitchingTable(Map<Integer, Integer> switchingTable) {
		this.switchingTable = switchingTable;
	}

	public List<Integer> getAdjacentEntities() {
		return adjacentEntities;
	}

	public void setAdjacentEntities(List<Integer> adjacentEntities) {
		this.adjacentEntities = adjacentEntities;
	}

	public List<Integer> getAdjacentEndDevices() {
		return adjacentEndDevices;
	}

	public void setAdjacentEndDevices(List<Integer> adjacentEndDevices) {
		this.adjacentEndDevices = adjacentEndDevices;
	}

}
