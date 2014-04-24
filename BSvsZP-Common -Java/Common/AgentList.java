package Common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.concurrent.locks.Lock;

import java.util.logging.Logger;

import org.omg.CORBA.portable.ApplicationException;

public class AgentList extends DistributableObject implements Iterable<AgentInfo> 
{
	private static final Logger log = Logger.getLogger(AgentList.class.getName());
	private ArrayList<AgentInfo> agents = new ArrayList<>();
    private Object myLock = new Object();
    private static int MinimumEncodingLength;
    private Lock lock = new Lock();

    public int FindIndex(short id)
    {
    	int result = -1;
    	int i = 0;
    	while (i < agents.size())
    	{
    		if (agents.get(i).getId() == id)
    			result =i;
    	}
    	return result; 
    }
    public short getClassId() {
        return (short) DISTRIBUTABLE_CLASS_IDS.AgentList.getValue();
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4 // Object header
        						+ 2;           // Components
        return MinimumEncodingLength;
    }

    public AgentList() {
    }

    public static AgentList Create(ByteList bytes) throws ApplicationException, Exception {
        AgentList result = new AgentList();
        result.Decode(bytes);
        return result;
    }

    public void Dispose() {
        Clear();
    }

    public int Count() {
        int result = 0;
        synchronized (myLock) {
            result = agents.size();
        }
        return result;

    }

    public AgentInfo getAgentInfo(int index) {
        AgentInfo result = null;
        if (index >= 0 && index < agents.size()) {
            result = agents.get(index);
        }
        return result;
    }

    public void Add(AgentInfo agentInfo) {
        synchronized (myLock) {
            agents.add(agentInfo);
        }
    }

    public void Remove(AgentInfo agentInfo) {
        if (agentInfo != null) {
            Remove(agentInfo.getId());
        }
    }

    public void Remove(short agentId) {
        synchronized (myLock) {
            for (AgentInfo agent : agents) {
                if (agent.getId() == agentId) {
                    agents.remove(agent);
                    break;
                }
            }
        }
    }

    public void Clear() {
        synchronized (myLock) {
            agents.clear();
        }

    }

    @Override
    public Iterator<AgentInfo> iterator() {
        return agents.iterator();
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, Exception {
        bytes.Add((short) DISTRIBUTABLE_CLASS_IDS.AgentList.getValue());                             // Write out the class type
        bytes.update();
        short lengthPos = bytes.getCurrentWritePosition();   // Get the current write position, so we
        bytes.update();                                  // can write the length here later

        bytes.Add((short) 0);                           // Write out a place holder for the length
        bytes.update();
        lock.lock();
        {
            bytes.Add((short) (agents.size()));
            bytes.update();
            for (AgentInfo component : agents) {
                bytes.Add(component);
                bytes.update();
            }
        }
        lock.unlock();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.WriteInt16To(lengthPos, length);          // Write out the length of this object    

    }

    @Override
    protected void Decode(ByteList bytes) throws ApplicationException, Exception {
        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid byte array", null);
        } else if (bytes.PeekInt16() != (short) DISTRIBUTABLE_CLASS_IDS.AgentList.getValue()) {
            throw new ApplicationException("Invalid class id", null);
        } else {
            short objType = bytes.GetInt16();

            short objLength = bytes.GetInt16();

            bytes.SetNewReadLimit(objLength);
            bytes.update();
            synchronized (myLock) {
                Clear();

                short count = bytes.GetInt16();

                for (int i = 0; i < count; i++) {
                    agents.add((AgentInfo) bytes.GetDistributableObject());
                }
            }

            bytes.RestorePreviosReadLimit();
        }

    }
    
    public void Update(AgentList agentList)
    {
        if (agentList!=null && agentList.Count()>0)
        	synchronized (myLock)
        	{
        		for (AgentInfo updatedAgent: agentList)
        		{
	                int index;
	                if ((index = FindIndex(updatedAgent.getId())) != -1)
	                	agents.set(index, updatedAgent);
	                else
	                    agents.add(updatedAgent);
	                
            }
        }
    }
    
    public AgentInfo FindClosestToLocation(FieldLocation location, ArrayList<AgentInfo.PossibleAgentType[]> types)
    {
        double closestDistance = 0;
        AgentInfo closestAgent = null;

        synchronized (myLock)
        {
            for(AgentInfo agent : agents)
            {
                double distance = FieldLocation.Distance(location, agent.getLocation());
                if ((types==null || types.size() ==0 || types.contains(agent.getAgentType()))
                        && (closestAgent == null || distance < closestDistance))
                {
                    closestAgent = agent;
                    closestDistance = distance;
                }
            }
        }

        return closestAgent;
    }

}
