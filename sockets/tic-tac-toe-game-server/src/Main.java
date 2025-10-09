import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

    static class Game {

        // Board cells numbered 0-8, top to bottom, left to right; null if empty
        private Player[] board = new Player[9];

        // Whose turn it is now
        Player currentPlayer;

        public boolean hasWinner() {
            return (board[0] != null && board[0] == board[1] && board[0] == board[2])
                    || (board[3] != null && board[3] == board[4] && board[3] == board[5])
                    || (board[6] != null && board[6] == board[7] && board[6] == board[8])
                    || (board[0] != null && board[0] == board[3] && board[0] == board[6])
                    || (board[1] != null && board[1] == board[4] && board[1] == board[7])
                    || (board[2] != null && board[2] == board[5] && board[2] == board[8])
                    || (board[0] != null && board[0] == board[4] && board[0] == board[8])
                    || (board[2] != null && board[2] == board[4] && board[2] == board[6]);
        }

        public boolean boardFilledUp() {
            return Arrays.stream(board).allMatch(Objects::nonNull);
        }

        public synchronized void move(int location, Player player) {
            if (player != currentPlayer) {
                throw new IllegalStateException("Not your turn");
            } else if (player.opponent == null) {
                throw new IllegalStateException("You don't have an opponent yet");
            } else if (board[location] != null) {
                throw new IllegalStateException("Cell already occupied");
            }
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
        }

        class Player implements Runnable {
            final char mark;
            Player opponent;
            final Socket socket;
            final Scanner input;
            final PrintWriter output;

            public Player(Socket socket, char mark) throws IOException {
                this.socket = socket;
                this.mark = mark;
                this.input = new Scanner(socket.getInputStream());
                this.output = new PrintWriter(socket.getOutputStream(), true);
            }


            @Override
            public void run() {
                try (socket) {
                    setup();
                    processCommands();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                  if (opponent != null && opponent.output != null) {
                      opponent.output.println("OTHER_PLAYER_LEFT");
                  }
                  System.out.println("Player " + this + " disconnected");
                }
            }

            @Override
            public String toString() {
                return mark + "@" + socket.getRemoteSocketAddress();
            }

            private void setup() throws IOException {
                System.out.println("Player " + this + " connected");
                System.out.println("Sending welcome message to " + this);

                output.println("WELCOME " + mark);

                if (mark == 'X') {
                    currentPlayer = this;
                    output.println("MESSAGE Waiting for opponent to connect");
                } else {
                    opponent = currentPlayer;
                    opponent.opponent = this;
                    opponent.output.println("MESSAGE Your move");
                }
            }

            private void processCommands() {
                while (input.hasNextLine()) {
                    var command = input.nextLine();
                    System.out.println("Received command from " + this + ": " + command);
                    if (command.startsWith("QUIT")) {
                        // No more to read from this player
                        return;
                    } else if (command.startsWith("MOVE")) {
                        processMoveCommand(Integer.parseInt(command.substring(5)));
                    }
                }
            }

            private void processMoveCommand(int location) {
                try {
                    move(location, this);
                    output.println("VALID_MOVE");
                    opponent.output.println("OPPONENT_MOVED " + location);

                    if (hasWinner()) {
                        output.println("VICTORY");
                        opponent.output.println("DEFEAT");
                    } else if (boardFilledUp()) {
                        output.println("TIE");
                        opponent.output.println("TIE");
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Rejected move from " + this + ": " + e.getMessage());
                    output.println("MESSAGE " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        try (var listener = new ServerSocket(58901)) {
            System.out.println("Tic tac toe server is running on port 58901...");
            var pool = Executors.newFixedThreadPool(100);

            while (true) {
                Game game = new Game();
                pool.execute(game.new Player(listener.accept(), 'X'));
                pool.execute(game.new Player(listener.accept(), 'O'));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}