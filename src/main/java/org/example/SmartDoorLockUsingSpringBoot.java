package org.example;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SmartDoorLockUsingSpringBoot {

    private static SerialPort chosenPort;
    private static String password;
    private static boolean simulationMode = false; // default

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask if simulation mode
        System.out.print("Run in Simulation Mode? (yes/no): ");
        simulationMode = sc.nextLine().trim().equalsIgnoreCase("yes");

        if (!simulationMode) {
            // Read password from user
            System.out.print("Enter Password: ");
            password = sc.nextLine().trim();

            // List available ports
            SerialPort[] ports = SerialPort.getCommPorts();
            for (int i = 0; i < ports.length; i++) {
                System.out.println(i + ": " + ports[i].getSystemPortName());
            }

            System.out.print("Select port index: ");
            int portIndex = sc.nextInt();
            sc.nextLine(); // consume newline
            chosenPort = ports[portIndex];

            if (chosenPort.openPort()) {
                System.out.println("Port Opened Successfully");
            } else {
                System.out.println("Failed to open port");
                sc.close();
                return;
            }

            // Authenticate
            if (authenticate(password)) {
                System.out.println("Access Granted");
                sendUnlockSignal();
                readResponseFromArduino();
            } else {
                System.out.println("Access Denied: Wrong Password");
            }

            chosenPort.closePort();

        } else {
            // Simulation mode flow (no password, no authentication)
            System.out.println("Simulation Mode: Working without Arduino");
            sendMockSignal();
            readMockResponseFromArduino();
        }

        sc.close();
    }

    static boolean authenticate(String inputPassword) {
        String masterPassword = fetchUrl("http://localhost:8080/api/password");
        String otpPassword = fetchUrl("http://localhost:8080/api/otp");
        return inputPassword.equals(masterPassword) || inputPassword.equals(otpPassword);
    }

    static String fetchUrl(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = br.readLine();
            br.close();
            return response != null ? response.trim() : "";
        } catch (Exception e) {
            System.out.println("Error fetching from URL: " + e.getMessage());
            return "";
        }
    }

    public static void sendUnlockSignal() {
        try {
            OutputStream os = chosenPort.getOutputStream();
            System.out.println("Signal sent to Arduino");
            os.write("Unlock the Door\n".getBytes());
            os.flush();

        } catch (Exception e) {
            System.out.println("Error sending signal: " + e.getMessage());
        }
    }

    public static void readResponseFromArduino() {
        try {
            InputStream is = chosenPort.getInputStream();
            Scanner scanner = new Scanner(is);
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < 5000 && scanner.hasNextLine()) {
                String response = scanner.nextLine();
                System.out.println("Arduino Response: " + response);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading from Arduino: " + e.getMessage());
        }
    }

    public static void sendMockSignal() {
        System.out.println("SIMULATION MODE: Unlock the Door");
        System.out.println("SIMULATION MODE: Signal Sent to Arduino");
    }

    public static void readMockResponseFromArduino() {
        System.out.println("SIMULATION MODE: Received Response from Arduino");
        System.out.println("SIMULATION MODE: Door Unlocked");
    }
}
