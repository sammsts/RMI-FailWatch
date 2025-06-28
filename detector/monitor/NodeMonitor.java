package detector.monitor;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NodeMonitor {
    private static final int TIMEOUT_MS = 3000;  // timeout para resposta

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java detector.monitor.NodeMonitor <ip:porta>");
            return;
        }

        String[] parts = args[0].split(":");
        if (parts.length != 2) {
            System.out.println("Formato inválido. Use ip:porta");
            return;
        }

        String ip = parts[0];
        int porta = Integer.parseInt(parts[1]);
        String nodeId = ip + ":" + porta;

        System.out.println("Monitor ouvindo na porta 5000");

        while (true) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, porta), TIMEOUT_MS);
                socket.setSoTimeout(TIMEOUT_MS);

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("heartbeat");

                String response = in.readLine();

                if ("heartbeat response".equalsIgnoreCase(response)) {
                    System.out.println(timestamp() + " Heartbeat recebido do nó: " + nodeId);
                } else {
                    System.out.println(timestamp() + " [SUSPEITA] Falha detectada no nó: " + nodeId);
                }

                socket.close();
            } catch (IOException e) {
                System.out.println(timestamp() + " [SUSPEITA] Falha detectada no nó: " + nodeId);
            }

            try {
                Thread.sleep(2000); // intervalo entre heartbeats
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static String timestamp() {
        return "[" + new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date()) + "]";
    }
}
