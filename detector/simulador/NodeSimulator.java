package detector.simulador;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class NodeSimulator {
    private static boolean ativo = true;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Uso: java detector.simulador.NodeSimulator <porta>");
            return;
        }

        int porta = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("Nó ativo na porta " + porta);

        // Thread para lidar com comandos do usuário (falhar / voltar)
        Thread comandoThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Digite 'falhar' para simular falha ou 'voltar' para reativar:");
                String cmd = scanner.nextLine().trim().toLowerCase();
                if ("falhar".equals(cmd)) {
                    ativo = false;
                    System.out.println("Nó está simulado como falho (sem resposta)");
                } else if ("voltar".equals(cmd)) {
                    ativo = true;
                    System.out.println("Nó reativado");
                }
            }
        });
        comandoThread.setDaemon(true);
        comandoThread.start();

        // Thread principal aceita conexões do monitor
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg = in.readLine();

                    if ("heartbeat".equalsIgnoreCase(msg)) {
                        System.out.println("Heartbeat recebido de " + socket.getInetAddress());
                        if (ativo) {
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println("heartbeat response");
                            System.out.println("Respondendo heartbeat");
                        } else {
                            System.out.println("Nó está falho, não respondendo heartbeat");
                            // não responde para simular falha
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
