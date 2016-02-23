package pl.themolka.itsquery.net.input;

import pl.themolka.itsquery.net.DataContainer;
import pl.themolka.itsquery.net.QueryData;
import pl.themolka.itsquery.query.TSQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadQueryThread extends Thread {
    protected final TSQuery tsQuery;

    public ReadQueryThread(TSQuery tsQuery) {
        super(tsQuery.getIdentifier() + " Read Thread");

        this.tsQuery = tsQuery;
    }

    @Override
    public void run() {
        try {
            Socket socket = this.tsQuery.getSocket();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), this.tsQuery.getEncoding()));

            String line = null;
            while (this.tsQuery.isRunning() && !socket.isClosed() && (line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    QueryData[] contexts = QueryDataParser.parse(this.tsQuery.getTextEncoding(), line);
                    if (contexts.length == 0) {
                        continue;
                    }

                    String command = contexts[0].getCommand().getCommand();
                    this.tsQuery.getInputHandler().execute(command, DataContainer.createInput(contexts));
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException io) {

        }
    }
}
