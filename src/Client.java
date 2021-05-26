import java.io.*;
import java.net.*;
import java.util.ArrayList;

class Client   //PLAYER1
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket socketClient = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendToData = new byte[1024];
        byte[] receiveToData = new byte[1024];

        String input,output,lastTwoLettersServer;
        String message ="You won!";

        ArrayList usedWords  = new ArrayList();


        System.out.println("ENTER A WORD:");


        input = in.readLine();
        usedWords.add(input);
        sendToData = input.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendToData, sendToData.length, IPAddress, 5555);
        socketClient.send(sendPacket);

        while(true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveToData, receiveToData.length);
            socketClient.receive(receivePacket);

            output = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            lastTwoLettersServer = output.substring(output.length() - 2, output.length());

            if (output.equals(message)) {
                System.out.println("YOU WON! CONGRATULATIONS! ");
                break;
            }

            usedWords.add(output);


            System.out.println("USED WORDS FROM PLAYERS: " + usedWords.toString());
            System.out.println("PLAYER2 SAYS: -" + output + " -ENTER THE WORD THAT START WITH THESE LETTERS: " + lastTwoLettersServer);

            GameTimer time2 = new GameTimer(10);

            if (!in.ready()) {
                input = in.readLine();


                while (usedWords.contains(input)) {
                    System.out.println("THIS WORD IS USED. TRY ANOTHER ONE: ");
                    input = in.readLine();

                }


                if (!lastTwoLettersServer.equals(input.substring(0, 2))) {

                    sendToData = message.getBytes();
                    DatagramPacket sendMessage1 = new DatagramPacket(sendToData, sendToData.length, IPAddress, 5555);
                    socketClient.send(sendMessage1);
                    System.out.println("GAME OVER. PLAYER2 WINS...");
                    break;

                }


                usedWords.add(input);
                sendToData = input.getBytes();
                DatagramPacket sendToServer = new DatagramPacket(sendToData, sendToData.length, IPAddress, 5555);
                socketClient.send(sendToServer);

                time2.timer.cancel();

            }
            else{
                time2.timer.cancel();
                sendToData = message.getBytes();
                DatagramPacket sendMessage1 = new DatagramPacket(sendToData, sendToData.length, IPAddress, 5555);
                socketClient.send(sendMessage1);
                break;
            }
        }

    }

}

