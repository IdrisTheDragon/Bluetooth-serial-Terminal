import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import javax.bluetooth.*;
import javax.microedition.io.*;
import java.io.*;

public class Bluetooth extends Thread {

    private RemoteDevice r;
    private String deviceName;
    private Label status;
    private ObservableList<String> terminalOut;
    private boolean enable;
    private StreamConnection conn;
    private BufferedReader bReader2;
    private PrintWriter pWriter;
    private DataOutputStream dos;
    private InputStream inStream;
    Bluetooth(Label status,String deviceName, ObservableList<String> terminalOut){
        this.status = status;
        this.deviceName = deviceName;
        this.terminalOut = terminalOut;
        enable = true;
    }


    public void run(){
        conn = getService(deviceName, status);
            try {
                dos = conn.openDataOutputStream();
                inStream = conn.openInputStream();
                pWriter = new PrintWriter(new OutputStreamWriter(dos));
                bReader2 = new BufferedReader(new InputStreamReader(inStream));
                while(enable){
                    if(bReader2.ready()){
                        String lineRead=bReader2.readLine();
                        Platform.runLater(() -> terminalOut.add(0,lineRead));
                    }
                }
                conn.close();
                dos.close();
                inStream.close();
                Platform.runLater(() -> status.setText("Disconnected"));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void interrupt(){
        enable = false;
    }

    public void send(String s){
        if(enable){
            pWriter.write(s);
            pWriter.flush();
            Platform.runLater(() -> terminalOut.add(0,">>"+s));
        }
    }

    private StreamConnection getService(String deviceName, Label status){
        try {
            Platform.runLater(() -> status.setText("Getting Local Device.."));
            Platform.runLater(() -> status.setStyle("-fx-color: orange"));
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            Platform.runLater(() -> status.setText("Getting Remote Device.."));
            RemoteDevice[] remoteDevices = localDevice.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
            DeviceDiscovery dscListener = new DeviceDiscovery();
            for(RemoteDevice d : remoteDevices){
                if(d.getFriendlyName(false).equals(deviceName)){
                    Platform.runLater(() -> {
                        try {
                            status.setText("found: " + d.getFriendlyName(false));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    r = d;
                }
            }
            Platform.runLater(() -> status.setText("Getting SSP ServiceURL.."));
            String robotService = dscListener.getConnectionURL(localDevice.getDiscoveryAgent(), r);
            Platform.runLater(() -> status.setText("Opening Stream Connection.."));
            StreamConnection conn=(StreamConnection)Connector.open(robotService);
            Platform.runLater(() -> status.setText("Connected to SPP"));
            Platform.runLater(() -> status.setStyle("-fx-color: green"));
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveStream(File selectedFile) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(selectedFile));
            for (String s :terminalOut) {
                writer.write(s + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
