package detector.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class HeartbeatSender {
    private final List<String> nodes;
    private final int interval;

    public HeartbeatSender(List<String> nodes, int interval) {
        this.nodes = nodes;
        this.interval = interval;
    }

    public void start() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            for (String node : nodes) {
                try (Socket socket = new Socket()) {
                    String[] parts = node.split(":");
                    socket.connect(new InetSocketAddress(parts[0], Integer.parseInt(parts[1])), 1000);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("HEARTBEAT");
                } catch (IOException ignored) {}
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
    }
}
