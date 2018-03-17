import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.bluetooth.*;

/**
 * Class that discovers all bluetooth devices in the neighbourhood
 * and displays their name and bluetooth address.
 */
public class DeviceDiscovery implements DiscoveryListener{

    //object used for waiting
    private static Object lock=new Object();

    public String getConnectionURL(DiscoveryAgent agent,RemoteDevice deviceClass) throws BluetoothStateException {
        UUID uuid[];
        uuid = new UUID[1];
        uuid[0] = new UUID("1101",true);
        agent.searchServices(null, uuid,deviceClass,this);
        try { synchronized(lock){ lock.wait(); } } catch (InterruptedException e) { e.printStackTrace(); }
        if(connectionURL==null){
            System.err.println("Device does not support SPP.");
        } else{
            System.out.println("Device supports SPP.");
        }
        return connectionURL;
    }

    //vector containing the devices discovered
    private Vector vecDevices=new Vector();
    private String connectionURL=null;

/**
 *
 * This call back method will be called for each discovered bluetooth devices.
 */
public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
    try {
        System.out.println("Device discovered: "+btDevice.getFriendlyName(true)); //add the device to the vector
    } catch (IOException e) {
        e.printStackTrace();
    }
    if(!vecDevices.contains(btDevice)){
        vecDevices.addElement(btDevice);
    }
}

    /** * Called when a bluetooth service is discovered. * Used for service search. */
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if (servRecord != null && servRecord.length > 0) {
            connectionURL = servRecord[0].getConnectionURL(0, false);
        }
        synchronized(lock){
            lock.notify();
        }
    }

    /**
     * Called when the service search is over.
     */
    public void serviceSearchCompleted(int transID, int respCode) {
        synchronized(lock){
            lock.notify();
        }
    }

/** * This callback method will be called when the device discovery is * completed. */
public void inquiryCompleted(int discType) {
    synchronized(lock){ lock.notify(); }
    switch (discType) {
        case DiscoveryListener.INQUIRY_COMPLETED : System.out.println("INQUIRY_COMPLETED");
        break;
        case DiscoveryListener.INQUIRY_TERMINATED : System.out.println("INQUIRY_TERMINATED");
        break;
        case DiscoveryListener.INQUIRY_ERROR : System.out.println("INQUIRY_ERROR");
        break;
        default : System.out.println("Unknown Response Code");
        break; }
    }//end method

}//end class