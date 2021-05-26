import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Server   //PLAYER2
{
    public static void main(String[] args) throws Exception
    {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socketServer = new DatagramSocket(5555);

        byte[] receiveToData = new byte[1024];
        byte[] sendToData = new byte[1024];

        String input,output,lastTwoLettersClient;
        String message ="You Won!";

        ArrayList usedWords = new ArrayList();


        while(true) {


            DatagramPacket receivePacket = new DatagramPacket(receiveToData, receiveToData.length);
            socketServer.receive(receivePacket);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            input = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            lastTwoLettersClient = input.substring(input.length() - 2, input.length());

            if (input.equals(message)) {
                System.out.println("YOU WON!!! CONGRATULATIONS!!!! ");
                break;
            }


            usedWords.add(input);


            System.out.println("WORDS THAT USED: " + usedWords);
            System.out.println("PLAYER1 SAYS: -" + input + "- ENTER THE WORD THAT START WITH THESE LETTERS: " + lastTwoLettersClient);

            GameTimer time1 = new GameTimer(10);

            if (!in.ready()) {

                output = in.readLine();

                while (usedWords.contains(output)) {
                    System.out.println("THIS WORD IS USED. TRY ANOTHER ONE: ");
                    output = in.readLine();

                }

                if (!lastTwoLettersClient.equals(output.substring(0, 2))) {

                    String mes = "You Win!";
                    sendToData = message.getBytes();
                    DatagramPacket sendMess1 = new DatagramPacket(sendToData, sendToData.length, IPAddress, port);
                    socketServer.send(sendMess1);
                    System.out.println("GAME OVER! PLAYER1 WINS :(");
                    break;

                }


                usedWords.add(output);
                sendToData = output.getBytes();
                DatagramPacket sendToClient = new DatagramPacket(sendToData, sendToData.length, IPAddress, port);
                socketServer.send(sendToClient);

                time1.timer.cancel();

            } else {

                sendToData = message.getBytes();
                DatagramPacket sendMessage1 = new DatagramPacket(sendToData, sendToData.length, IPAddress, port);
                socketServer.send(sendMessage1);
                break;

            }

        }
    }
}