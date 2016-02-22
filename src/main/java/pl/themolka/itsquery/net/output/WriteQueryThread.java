package pl.themolka.itsquery.net.output;

import pl.themolka.itsquery.query.TSQuery;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WriteQueryThread extends Thread {
    protected final TSQuery tsQuery;

    private final BlockingQueue<String> queryQueue = new ArrayBlockingQueue<>(1024);

    public WriteQueryThread(TSQuery tsQuery) {
        super("Write Query Thread");

        this.tsQuery = tsQuery;
    }

    @Override
    public void run() {
        try {
            Socket socket = this.tsQuery.getSocket();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), this.tsQuery.getEncoding()));

            while (this.tsQuery.isRunning() && !socket.isClosed()) {
                try {
                    this.submit(writer, this.queryQueue.take());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException io) {

        }
    }

    public boolean addQuery(String query) {
        try {
            return this.queryQueue.add(query.trim());
        } catch (IllegalStateException ex) {
            return false;
        }
    }

    public void submit(BufferedWriter writer, String query) throws IOException {
        try {
            writer.write(query);
            writer.newLine();
            writer.flush();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
